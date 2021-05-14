/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.annotation.Loggable;
import com.bannhaccu.binding.DatabaseModule;
import com.bannhaccu.binding.LoggingAOP;
import com.bannhaccu.service.IDatabase;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.sql.Connection;


// DAO - Data Access Object
// Dung ra dam nhiem vai tro truy cap du lieu toi Database.
public class DAO {
    @Inject
    protected IDatabase db;
    protected static Connection conn;
    
    @Loggable
    public DAO() {
        // Singleton pattern => Create only 1 instance overall app life-time
        if (conn == null) {
            System.out.println("CREATE NEW INSTANCE");
            Injector injector = Guice.createInjector(new DatabaseModule(), new LoggingAOP());
            conn = injector.getInstance(IDatabase.class).getConnection();
            // conn = new IDatabase(); // Tao moi instance
        }
    }
    
    @Loggable
    public void hello () {
        System.out.println("HELLO");
    }

    public IDatabase getDb() {
        return db;
    }

    public static Connection getConn() {
        return conn;
    }
}


