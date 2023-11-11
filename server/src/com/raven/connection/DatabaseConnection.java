package com.raven.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {

    }

    public void connectToDatabase() throws SQLException, ClassNotFoundException {
//    String server = "localhost"; // Tên máy chủ SQL Server
//    String database = "chat_application"; // Tên cơ sở dữ liệu
//    String userName = "sa"; // Tên người dùng SQL Server
//    String password = "1"; // Mật khẩu SQL Server
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    // Chuỗi kết nối cho SQL Server
    String connectionUrl = "jdbc:sqlserver://localhost:1433;database=chat_application;user=sa;password=1;encrypt=true;trustServerCertificate=true";

    try {
        // Kết nối tới cơ sở dữ liệu SQL Server
        connection = java.sql.DriverManager.getConnection(connectionUrl);
    } catch (SQLException e) {
        // Xử lý lỗi nếu có
        e.printStackTrace();
    }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
