package com.patikadev.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDbConnector {
    private Connection connection = null;

    private Connection connectDB(){
        try {
            this.connection = DriverManager.getConnection(MyHelper.DB_URL,MyHelper.DB_USERNAME,MyHelper.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static Connection getInstance(){
        MyDbConnector dbConnector = new MyDbConnector();
        return dbConnector.connectDB();
    }
}
