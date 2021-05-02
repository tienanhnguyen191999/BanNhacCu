/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.binding;

import com.bannhaccu.annotation.Loggable;
import com.bannhaccu.service.ConsoleLogger;
import com.bannhaccu.service.EmailLogger;
import com.bannhaccu.service.FileLogger;
import com.bannhaccu.service.ILogger;
import com.bannhaccu.service.LoggingInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

/**
 *
 * @author TienAnh
 */
public class LoggingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ILogger.class).to(EmailLogger.class);
    }
}
