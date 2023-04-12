package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connect = null;

    public Connection connectDB(){
        try {
            this.connect = DriverManager.getConnection(config.DB_URL, config.DB_USERNAME, config.DB_PASSWORD);
            System.out.println("Başarılı bir şekilde veritabanına bağlandı.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this.connect;
    }

    public static Connection getInstance(){
        DBConnector db = new DBConnector();
        return db.connectDB();
    }

}
