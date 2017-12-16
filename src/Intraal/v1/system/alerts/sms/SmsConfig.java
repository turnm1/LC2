/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system.alerts.sms;

/**
 *
 * @author Turna
 */
public class SmsConfig {
    
    private long telenummer;
//41763070624L;//41788312364L;
    private String message;

    public long getTelenummer() {
        return telenummer;
    }

    public void setTelenummer(long telenummer) {
        this.telenummer = telenummer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
