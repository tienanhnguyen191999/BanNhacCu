/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TienAnh
 */
public class MySQLDatabase {
    public static Connection conn;

    public static void main(String[] args) {
        try {
            // db parameters
            String url = "jdbc:mysql://127.0.0.1:3306/bannhaccu";
            String user = "root";
            String password = "root";

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("con" + conn.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
