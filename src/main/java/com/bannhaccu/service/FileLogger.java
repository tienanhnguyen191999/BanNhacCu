/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TienAnh
 */
public class FileLogger implements ILogger{

    @Override
    public void showLog(String message) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("filename.txt", true);
            myWriter.write("["+new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date())+"] " + message + "\n");
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                myWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
