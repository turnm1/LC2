/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.siot.inputs.services;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.siot.input.SiotDashboardMessageInput;
import Intraal.v1.system.alerts.SendingMessageService;
import com.tinkerforge.IPConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Turna
 */
public class NotifyingCarer implements MqttCallback {
    
    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    // SIOT input
    SiotDashboardMessageInput sdi = new SiotDashboardMessageInput();
    SendingMessageService sendMessage = new SendingMessageService();
    
    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "ServiceA"; // keine
    private final String ROOM = ""; // keine
    private final String MODUL = "Notifying";
    ///////////////////////////////////////////////////// 
    
    private String messageVal;
    private static String mtext;

    public NotifyingCarer(){
        try {
            connectMQTT();
        } catch (Exception ex) {
            Logger.getLogger(NotifyingCarer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setMessageText(String text) {
        NotifyingCarer.mtext = text;
    }

    public static String getMessageText() {
        return mtext;
    }
    
    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
    */
    public void connectMQTT() throws Exception {
        con = new Connections();
        c = new MQTTCommunication();
        p = new MQTTParameters();
        p.setClientID(con.getClientIDTopic(UID));
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getBrokerConnection()));
        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        c.subscribe("Gateway/#", 0);
        p.getLastWillMessage();
    }

     public void doNotify() throws Exception {
         message = new MqttMessage();
            message.setRetained(true);
            message.setQos(0);
            setMessageText(sendMessage.textOutOfHouse());
            message.setPayload(getMessageText().getBytes());
            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
            sdi.setInputTextKey(con.getService_message());       // inputKey
            sdi.setInputTextMessage(message+"");                // noch nicht schön
            sdi.sendTextInput();
            System.out.println("Notify: " + getMessageText());
     }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.endsWith("value")) {
            messageVal = new String(message.getPayload());
            
            String[] res = topic.split("/", 5);
            String sendedRoom = res[3];  
                if (sendedRoom.equals("")) {
                    if (messageVal.equals("Bewohner nicht zu Hause")) {
                    sendMessage.sendAlert();
                    }
                }
                
        }
    }
    

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Connection Lost =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }
    
//        public static void main(String[] args) throws Exception {
//        NotifyingCarer start = new NotifyingCarer();
//    }

}