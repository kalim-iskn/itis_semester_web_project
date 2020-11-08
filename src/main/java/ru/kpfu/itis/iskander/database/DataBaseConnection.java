package ru.kpfu.itis.iskander.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private Connection connection;

    public DataBaseConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/board?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "root";
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
    }

    public void close() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
