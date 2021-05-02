/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.binding;

import com.bannhaccu.service.IDatabase;
import com.bannhaccu.service.MySQLDatabase;
import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDatabase.class).to(MySQLDatabase.class);
    }
}
