import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    List<InvoiceTotal> findInvoiceTotals() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                SELECT i.id, i.customer_name, i.status, SUM(il.unit_price * quantity) AS total
                FROM invoice i
                INNER JOIN invoice_line il ON il.invoice_id = i.id
                GROUP BY i.id, i.customer_name, i.status
                ORDER BY i.id;
                """;
        List<InvoiceTotal> findedInvoiceTotals = new ArrayList<>();

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                findedInvoiceTotals.add(new InvoiceTotal(
                        resultSet.getInt("id"),
                        StatusEnum.valueOf(resultSet.getString("status")),
                        resultSet.getString("customer_name"),
                        resultSet.getDouble("total")
                ));
            }

        } catch (SQLException e){
            throw new RuntimeException("Error executing query", e);
        }
        return findedInvoiceTotals;
    }

    List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                SELECT i.id, i.customer_name, i.status, SUM(il.unit_price * quantity) AS total
                FROM invoice i
                INNER JOIN invoice_line il ON il.invoice_id = i.id
                WHERE i.status = 'CONFIRMED'
                OR i.status = 'PAID'
                GROUP BY i.id, i.customer_name, i.status
                ORDER BY i.id;
                """;
        List<InvoiceTotal> findedInvoiceTotals = new ArrayList<>();

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                findedInvoiceTotals.add(new InvoiceTotal(
                        resultSet.getInt("id"),
                        StatusEnum.valueOf(resultSet.getString("status")),
                        resultSet.getString("customer_name"),
                        resultSet.getDouble("total")
                ));
            }

        } catch (SQLException e){
            throw new RuntimeException("Error executing query", e);
        }
        return findedInvoiceTotals;
    }

    InvoiceStatusTotals computeStatusTotals() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select i.status, SUM(il.unit_price * il.quantity) as total
                from invoice_line il
                join invoice i on i.id = il.invoice_id
                group by i.status
                """;
        Double total_paid = 0.0;
        Double total_confirmed = 0.0;
        Double total_draft = 0.0;

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                 String status = resultSet.getString("status");
                 Double total = resultSet.getDouble("total");

                 switch (status) {
                     case "PAID" -> total_paid = total;
                     case "CONFIRMED" -> total_confirmed = total;
                     case "DRAFT" -> total_draft = total;
                 }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return new InvoiceStatusTotals(
                total_paid,
                total_confirmed,
                total_draft
        );
    }

    Double computeWeightedTurnover() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select
                    SUM(
                        CASE
                            WHEN i.status = 'PAID' THEN il.unit_price * il.quantity
                            WHEN i.status = 'CONFIRMED' THEN ((il.unit_price * il.quantity) * 50) / 100
                            WHEN i.status = 'DRAFT' THEN 0
                        END
                    ) as total
                from invoice_line il
                join invoice i on i.id = il.invoice_id;
                """;
        Double result = 0.0;

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                result = resultSet.getDouble("total");
            }
        } catch (SQLException e){
            throw new RuntimeException("Error executing query", e);
        }
        return result;
    }

    List<InvoiceTaxSummary> findInvoiceTaxSummaries(){
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select  i.id,
                        SUM(il.unit_price * il.quantity) as "HT",
                        ((SUM(il.unit_price * il.quantity) * 20) / 100) as "TVA",
                        (SUM(il.unit_price * il.quantity) + ((SUM(il.unit_price * il.quantity) * 20) / 100)) as "TTC"
                from invoice_line il
                join invoice i on i.id = il.invoice_id
                group by i.id
                order by i.id
                """;
        List<InvoiceTaxSummary> findedInvoiceTaxSummaries = new ArrayList<>();

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                findedInvoiceTaxSummaries.add(
                        new InvoiceTaxSummary(
                                resultSet.getInt("id"),
                                resultSet.getDouble("HT"),
                                resultSet.getDouble("TVA"),
                                resultSet.getDouble("TTC")
                        )
                );
            }
        } catch (SQLException e){
            throw new RuntimeException("Error executing query", e);
        }
        return findedInvoiceTaxSummaries;
    }

    BigDecimal computeWeightedTurnoverTtc() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                SELECT SUM(
                    CASE
                        WHEN t.status = 'PAID'
                            THEN t.total + (t.total * 20 / 100)
                
                        WHEN status = 'CONFIRMED'
                            THEN ((t.total + (t.total * 20 / 100)) * 50) / 100
                
                        WHEN t.status = 'DRAFT'
                            THEN 0
                    END
                ) AS total
                FROM (
                    SELECT
                        i.status,
                        SUM(il.unit_price * il.quantity) AS total
                    FROM invoice_line il
                    JOIN invoice i ON i.id = il.invoice_id
                    GROUP BY i.id, i.status
                ) t
                """;
        BigDecimal result = BigDecimal.valueOf(0.0);

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery()
        ){
            while (resultSet.next()){
                result = resultSet.getBigDecimal("total");
            }
        } catch (SQLException e){
            throw new RuntimeException("Error executing query", e);
        }
        return result;
    }
}
