/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system.alerts;

import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.inputs.services.NotifyingCarer;
import Intraal.v1.system.alerts.email.MailConfig;
import Intraal.v1.system.alerts.email.SendMail;
import Intraal.v1.system.alerts.sms.SendSMS;
import Intraal.v1.system.alerts.sms.SmsConfig;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Turna
 */
public class SendingMessageService {

    IntraalSettings settings = new IntraalSettings();
    SendSMS sms = new SendSMS();
    SmsConfig sc = new SmsConfig();
    SendMail mail = new SendMail();
    MailConfig ms = new MailConfig();
    private String MessageText;
    private String MessageSubject;
    private String recipientsEMail = settings.fam1_eMail;
    private long recipientsTelenummer = settings.fam1_tel;
    
    Date dt = new Date();
    // Festlegung des Formats:
    SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    public String textOutOfHouse(){
        String text = "INTRAAL Meldung: Bewohner hat das Haus verlassen\n" + 
                            "Gefahrstufe: Normal\n" +
                            "Datum & Uhrzeit: "+df.format(dt) +"\n"+
                            "Raum: Ausserhalb der Wohnung\n";
        return text;
    }
    
    private void prepareAlert() {
//        if (nc.getDangeLevel().equals("Danger")) {
//            MessageSubject = "INTRAAL: Es wurde ein Gefahr endeckt!";
//            MessageText = "Das INTRAAL System hat ein: UNFALL – Sturz detektiert\n"
//                    + "Datum: 02.01.2017 \n"
//                    + "Uhrzeit: 13:44 \n"
//                    + "\n"
//                    + "Empfohlenes Vorgehen:\n"
//                    + "1. Versuchen Sie als erstes die Nummer: 078 831 23 64 anzurufen. \n"
//                    + "a. Situation nachfragen. Wie geht es der Person? Wie können Sie helfen? Ambulanz rufen?\n"
//                    + "2. Person nicht ERREICHT: \n"
//                    + "a. Gehen Sie an die Adresse: Brünnenstrasse 118, 3018 Bern (Route öffnen) \n"
//                    + "b. Leisten Sie Vorort erste Hilfe. Ambulanzrufen: 144\n"
//                    + "3. Wenn Sie nicht an die Adresse GEHEN können: \n"
//                    + "a. Rufen Sie diese Nummer an: 0800 20 20\n"
//                    + "\n"
//                    + "--- Ihr Tür Eintrittscode: 02A7B ---";
//        } else if (nc.getDangeLevel().equals("Normal")) {
        
            MessageSubject = "INTRAAL MELDUNG";
            MessageText =   textOutOfHouse();
      //  }
    }

    /*
    * prepare and sending the sms
    */
    private void prepareSMS() {
        sms.send(MessageText, recipientsTelenummer);
    }

    /*
    * prepare and sending the e-mail
    */
    private void prepareMail() {
        String username = ms.getUsername();
        String password = ms.getPassword();
        String senderAddress = ms.getSenderAddress();
        String smtpHost = ms.getSmtpHost();
        mail.sendMail(smtpHost, username, password, senderAddress, recipientsEMail, MessageSubject, MessageText);
    }

    /*
    * start hole sending process
    */
    private void sendProcess() {
        prepareAlert();
        prepareSMS();
        prepareMail();
    }

    public void sendAlert() throws Exception {
        boolean onOff = settings.sendingMessage;
        if (onOff == true){
            sendProcess();
        }
    }

//    public static void main(String[] args) throws Exception {
//        SendingMessageService s = new SendingMessageService();
//        s.sendAlert();
//    }

}
