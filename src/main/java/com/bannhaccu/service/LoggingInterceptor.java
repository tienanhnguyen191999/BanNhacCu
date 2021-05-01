/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.service;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author TienAnh
 */
public class LoggingInterceptor implements MethodInterceptor{
    @Inject
    private ILogger logger;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.showLog("TIENANH TEST LOGGING");
        
        Object[] objectArray = invocation.getArguments();
        for (Object object : objectArray) {
            System.out.println("Get the arguments: " + object);
        }
         
        // Proceeds to the next interceptor in the chain. 
        Object result = invocation.proceed();
         
        // Handle after
        return result;
    }
}
