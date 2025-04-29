package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    // Thay đổi các thông số kết nối tương ứng với CSDL của bạn
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=dbRapChieuPhim;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    private static Connection connection = null;

    // Phương thức để mở kết nối đến CSDL
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Tạo kết nối
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Phương thức để đóng kết nối đến CSDL
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
