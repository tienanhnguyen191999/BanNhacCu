/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.binding.DatabaseModule;
import com.bannhaccu.service.IDatabase;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TienAnh
 */
public class DAO {
    @Inject
    protected IDatabase db;
    public static Connection conn;

    public DAO() {
        // Singleton pattern => Create only 1 instance overall app life-time
        if (conn == null) {
            System.out.println("CREATE NEW INSTANCE");
            Injector injector = Guice.createInjector(new DatabaseModule());
            conn = injector.getInstance(IDatabase.class).getConnection();
        }
    }

    public static void main(String[] args) {
        try {
            DAO a = new DAO();
            Statement stmt = a.conn.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select * from hang");
            // show data
            while (rs.next()) {
                System.out.println(rs.getInt(3) + "  " + rs.getString(2)
                    + "  " + rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


