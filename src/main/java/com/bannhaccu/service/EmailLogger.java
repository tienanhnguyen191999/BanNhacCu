/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.service;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import com.bannhaccu.config.MailConfig;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EmailLogger implements ILogger{
    @Override
    public void showLog(String message) {
        // Send email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", MailConfig.HOST_NAME);
        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", MailConfig.SSL_PORT);
 
        // get Session
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
            }
        });
 
        // compose message
        try {
            MimeMessage messageSend = new MimeMessage(session);
            messageSend.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailConfig.RECEIVE_EMAIL));
            messageSend.setSubject("[Logging BanNhacCu "+new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date())+"]");
            messageSend.setContent(message, "text/html");
 
            // send message
            Transport.send(messageSend);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
 

 