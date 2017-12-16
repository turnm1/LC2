/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system.alerts.email;

/**
 *
 * @author Turna
 */
public class MailConfig {
    
    public MailConfig(){
        
    }
    // FINAL, no Edit
    private String username = "mail@intraal.com";
    private String password = "iintraalMail08";
    private String senderAddress ="mail@intraal.com";
    private String smtpHost = "asmtp.mail.hostpoint.ch";
    private String smptAuth = "mail.smtp.auth";
    
//    // Recipient Person
//    private String recipientsAddress = "turnamete@hotmail.com"; //somereceiver@web.de
//    private String subject = "INTRAAL";
//    private String text = "Beispiel Text";

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    } 
    
    public String getSmptAuth() {
        return smptAuth;
    }

    public void setSmptAuth(String smptAuth) {
        this.smptAuth = smptAuth;
    }
    
}
