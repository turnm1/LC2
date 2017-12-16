/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system.alerts.email;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 

/**
 *
 * @author Turna
 */
public class SendMail {
    
   
public void sendMail(String smtpHost,String username,String password,String senderAddress,String recipientsAddress,String subject,String text ){
        MailAuthenticator auth = new MailAuthenticator(username, password);
 
        Properties properties = new Properties();
 
        // Den Properties wird die ServerAdresse hinzugefügt
        properties.put("mail.smtp.host", smtpHost);
        properties.setProperty( "mail.smtp.port", "587");
 
        // !!Wichtig!! Falls der SMTP-Server eine Authentifizierung
        // verlangt
        // muss an dieser Stelle die Property auf "true" gesetzt
        // werden
        properties.put("mail.smtp.auth", "true");
 
        // Hier wird mit den Properties und dem implements Contructor
        // erzeugten
        // MailAuthenticator eine Session erzeugt
        Session session = Session.getDefaultInstance(properties, auth);
 
        try {
            // Eine neue Message erzeugen
            Message msg = new MimeMessage(session);
 
            // Hier werden die Absender- und Empfängeradressen gesetzt
            msg.setFrom(new InternetAddress(senderAddress));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                    recipientsAddress, false));
 
            // Der Betreff und Body der Message werden gesetzt
            msg.setSubject(subject);
            msg.setText(text);
 
            // Hier lassen sich HEADER-Informationen hinzufügen
            msg.setHeader("Test", "Test");
            msg.setSentDate(new Date( ));
 
            // Zum Schluss wird die Mail natürlich noch verschickt
            Transport.send(msg);
 
        }
        catch (Exception e) {
            e.printStackTrace( );
        }
    }
   
    class MailAuthenticator extends Authenticator {
 
        /**
         * Ein String, der den Usernamen nach der Erzeugung eines
         * Objektes<br>
         * dieser Klasse enthalten wird.
         */
        private final String user;
 
        /**
         * Ein String, der das Passwort nach der Erzeugung eines
         * Objektes<br>
         * dieser Klasse enthalten wird.
         */
        private final String password;
 
        /**
         * Der Konstruktor erzeugt ein MailAuthenticator Objekt<br>
         * aus den beiden Parametern user und passwort.
         *
         * @param user
         *            String, der Username fuer den Mailaccount.
         * @param password
         *            String, das Passwort fuer den Mailaccount.
         */
        public MailAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }
 
        /**
         * Diese Methode gibt ein neues PasswortAuthentication
         * Objekt zurueck.
         *
         * @see javax.mail.Authenticator#getPasswordAuthentication()
         */
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user, this.password);
        }
    }
   
//    public static void main(String[] args) {
// 
//        MailConfig ms = new MailConfig();
//        
//        String username = ms.getUsername();
//        String password = ms.getPassword();
//        String senderAddress = ms.getSenderAddress();
//        String recipientsAddress = ms.getRecipientsAddress(); 
//        String subject = ms.getSubject();
//        String text = ms.getText();
//        String smtpHost = ms.getSmtpHost();
//              
//        new SendMail().sendMail(smtpHost, username, password, senderAddress, recipientsAddress, subject, text);
//    }
//  
}