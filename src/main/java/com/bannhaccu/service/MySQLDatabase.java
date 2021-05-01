/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.service;

import com.bannhaccu.annotation.Loggable;
import com.bannhaccu.service.IDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TienAnh
 */
public class MySQLDatabase implements IDatabase {
    @Override
    public Connection getConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/bannhaccu";
        String user = "root";
        String password = "root";   
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connect Successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
