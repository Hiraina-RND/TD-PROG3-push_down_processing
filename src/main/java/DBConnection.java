import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection (){
        try {
            String jdbcUrl = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
