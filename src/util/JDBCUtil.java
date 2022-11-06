package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtil {
    static Connection conn = null;

    static {
        String url = "jdbc:mysql://localhost:3306/laf";
        String user = "root";
        String password = "splendid";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void release(ResultSet set, Statement stmt, Connection connection) throws SQLException {
        if (set != null) {
            set.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
