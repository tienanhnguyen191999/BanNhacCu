/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.service;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author TienAnh
 */
public class SQLServerDatabase implements IDatabase{
    @Override
    public Connection getConnection() {
        String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=bannhaccu;integratedSecurity=true";
        String USER_NAME = "sa";
        String PASSWORD = "sa";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
