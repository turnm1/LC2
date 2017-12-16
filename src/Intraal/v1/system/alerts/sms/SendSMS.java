/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system.alerts.sms;

import at.sms.business.sdk.client.impl.DefaultSmsClient;
import at.sms.business.sdk.domain.TextMessage;

/**
 *
 * @author Turna
 */
public class SendSMS {

    SmsConfig sc = new SmsConfig();
    
        public void send(String message, long tele) {
        try {
            // Hier sind die Daten von deinem https://app.websms.com/ Account, 50 SMS sind für 12 Tage kostenlos
            DefaultSmsClient smsClient = new DefaultSmsClient("fexiwoda@ucylu.com", "Astro080", "https://api.websms.com");

            long[] recipients = new long[]{tele};

            String messageContent = message;
            TextMessage textMessage = new TextMessage(recipients, messageContent);

            int maxSmsPerMessage = 1;
            boolean test = false;

            int statuscode = smsClient.send(textMessage, maxSmsPerMessage, test);
            if (statuscode == 2000) {
                System.out.println("Sending SMS successfully tested");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
