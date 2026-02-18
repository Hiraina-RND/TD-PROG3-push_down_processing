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
}
