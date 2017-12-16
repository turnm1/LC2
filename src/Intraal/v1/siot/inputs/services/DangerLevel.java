/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.siot.inputs.services;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.siot.input.SiotDashboardDangerLevelInput;
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
public class DangerLevel implements MqttCallback {
    
    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    // SIOT input
    SiotDashboardDangerLevelInput sdi = new SiotDashboardDangerLevelInput();
    
    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "ServiceB"; // keine
    private final String ROOM = ""; // keine
    private final String MODUL = "DangerLevel";
    /////////////////////////////////////////////////////

    private String dLevel = "Normal";
    private String messageVal;
    private int levels = 0;
    private int inorout = 1;
    
    public DangerLevel(){
        try {
            connectMQTT();
        } catch (Exception ex) {
            Logger.getLogger(DangerLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
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


    public void setDangerLevel(String dl) {
        this.dLevel = dl;
    }

    public String getDangerLevel() {
        return dLevel;
    }
    
   
    public void levelOutOfHouse() throws Exception {
            message = new MqttMessage();
            message.setRetained(true);
            message.setQos(0);
            setDangerLevel("Nicht zu Hause");
            message.setPayload(getDangerLevel().getBytes());
            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
            sdi.setInputDangerLKey(con.getService_alarm());       // inputKey
            sdi.setInputDangerLMessage(message+"");                // noch nicht schön
            sdi.sendDangerLInput();
            System.out.println("Notify: " + getDangerLevel());
    }
    public void levelInHouse() throws Exception {
            message = new MqttMessage();
            message.setRetained(true);
            message.setQos(0);
            setDangerLevel("Normal");
            message.setPayload(getDangerLevel().getBytes());
            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
            sdi.setInputDangerLKey(con.getService_alarm());       // inputKey
            sdi.setInputDangerLMessage(message+"");                // noch nicht schön
            sdi.sendDangerLInput();
            System.out.println("Notify: " + getDangerLevel());
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        if (topic.endsWith("value")) {
            messageVal = new String(message.getPayload());

            String[] res = topic.split("/", 5);
            String sendedRoom = res[3];  
                if (messageVal.equals("Bewohner nicht zu Hause")) {
                    levelOutOfHouse();
                }
                if (messageVal.equals("Bewohner zu Hause")) {
                    levelInHouse();
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

//     public static void main(String[] args) throws Exception {
//        DangerLevel start = new DangerLevel();
//    }
    
}
