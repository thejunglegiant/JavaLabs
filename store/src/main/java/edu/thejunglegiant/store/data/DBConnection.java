package edu.thejunglegiant.store.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String dbUrl = "jdbc:postgresql://localhost/Store";

    private static DBConnection instance;

    private static Connection _connection;

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (_connection == null || _connection.isClosed()) {
            Properties props = new Properties();
            props.setProperty("user","admin");

            _connection = DriverManager.getConnection(dbUrl, props);
        }

        return _connection;
    }
}
