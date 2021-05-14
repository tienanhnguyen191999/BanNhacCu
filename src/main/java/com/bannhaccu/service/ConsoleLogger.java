package com.bannhaccu.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class ConsoleLogger implements ILogger{

    @Override
    public void showLog(String message) {
        System.out.println("["+new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date())+"] " + message);
    }
   
}
