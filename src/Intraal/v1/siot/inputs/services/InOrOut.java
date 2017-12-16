/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.siot.inputs.services;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.sensors.bad.badMotionSensor;
import Intraal.v1.sensors.bad.badPassageSensor;
import Intraal.v1.sensors.bad.badTempSensor;
import Intraal.v1.siot.input.SiotDashboardInput;
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
public class InOrOut implements MqttCallback {
    
    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    
    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();
    
    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "Service1"; // keine
    private final String ROOM = ""; // keine
    private final String MODUL = "InOrOut";
    /////////////////////////////////////////////////////

    private String personAtHouse = "zu Hause";
    private String messageVal;
    private int levels = 0;
    private int inorout = 1;
    
    public InOrOut(){
        try {
            connectMQTT();
        } catch (Exception ex) {
            Logger.getLogger(InOrOut.class.getName()).log(Level.SEVERE, null, ex);
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


    public void setPersonAtHouse(String personAtHouse) {
        this.personAtHouse = personAtHouse;
    }

    public String getPersonAtHouse() {
        return personAtHouse;
    }
    
   
    public void doLevelCheck() throws Exception {
        if (levels == 3){
           // connectMQTT();
            message = new MqttMessage();
            message.setRetained(true);
            message.setQos(0);
            setPersonAtHouse("Bewohner nicht zu Hause");
            message.setPayload(getPersonAtHouse().getBytes());
            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
            sdi.setInputKey(con.getService_inOrOut());       // inputKey
            sdi.setInputMessage(message+"");                // noch nicht schön
            sdi.sendInput();
            System.out.println("Notify: " + getPersonAtHouse());
        } if (levels == 5 || levels == 8){
           // connectMQTT(); // mit // hat es funktioniert
            message = new MqttMessage();
            message.setRetained(true);
            message.setQos(0);
            setPersonAtHouse("Bewohner zu Hause");
            message.setPayload(getPersonAtHouse().getBytes());
            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
            sdi.setInputKey(con.getService_inOrOut());       // inputKey
            sdi.setInputMessage(message+"");                // noch nicht schön
            sdi.sendInput();
            levels = 0;
            System.out.println("Notify: " + getPersonAtHouse());
        }
    }
    
    
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        String HauseingangRoom = "Bad"; // Orginal BFH "Eingang"
        
        /**
         * Sub Client with Topic Entry(Eingang)
         */
        if (topic.endsWith("value")) {
            messageVal = new String(message.getPayload());

            String[] res = topic.split("/", 5);
            String sendedRoom = res[3];  
            /*
            * Start (levels = 0; inorout = 1)
            * Case 1 = Person starts intraal, person is in House!
            *   Action: 1.) Motion detection[1] 2.) Passage detection[2] 3.) No Motion detection[3] => Person out of House (levels = 3; inorout = 0)
            * Case 2 = Person is out of House!
            *   Action: 1.) Passage detection[4] 2.) Motion detection[5] => Person in House (levels = 5; inorout = 1)
            * Case 3 = if detected motion in House when inorout = 0 = Person in House
            *   Action: 1.) Motion  detection[6] 2.) Passage detection[7] => [5] Person in House 
            */
            if(sendedRoom.equals(HauseingangRoom)){
                if (messageVal.equals("Motion Detected")) {
                    if (inorout == 1){
                        if (levels == 0){
                            levels = 1;
                            System.out.println(" =========== 1 =========== ");
                            doLevelCheck();
                        } 
                    } if (inorout == 0){
                        if (levels == 4){
                            levels = 5;
                            inorout = 1;
                            System.out.println(" =========== 5 =========== ");
                            doLevelCheck();
                        } if (levels == 3){
                            levels = 6;
                            System.out.println(" =========== 6 =========== ");
                            doLevelCheck();
                        } if (levels == 7){
                            levels = 8;
                            inorout = 1;
                            System.out.println(" =========== 8 =========== ");
                            doLevelCheck();
                        } 
                    }
                }
                if (messageVal.equals("Passage Detected")) {
                    if (inorout == 1){
                        if (levels == 1 || levels == 0){
                            levels = 2;
                            System.out.println(" =========== 2 =========== ");
                            doLevelCheck();
                        }
                    } if (inorout == 0){
                        if (levels == 3){
                            levels = 4;
                            System.out.println(" =========== 4 =========== ");
                            doLevelCheck();
                        } if (levels == 6){
                            levels = 5;
                            inorout = 1;
                            System.out.println(" =========== 5.1 =========== ");
                            doLevelCheck();
                        }
                }
                }
                if (messageVal.equals("Motion Ended")) {
                    if (inorout == 1){
                        if (levels == 2){
                            levels = 3;
                            inorout = 0;
                            System.out.println(" =========== 3 =========== ");
                            doLevelCheck();
                        } 
                    } if (inorout == 0){
                        if (levels == 6){ //3
                            levels = 7;
                            System.out.println(" =========== 7 =========== ");
                            doLevelCheck();
                        } 
                    }
                }
            // Testing
            } else if(!sendedRoom.equals(HauseingangRoom)){
                if (messageVal.equals("Motion Detected")) {
                    if (inorout == 0){
                        if (levels == 6){
                            levels = 5;
                            inorout = 1;
                            System.out.println(" =========== 5.1 =========== ");
                            doLevelCheck();
                        }
                    }
                }
            }
            // Testing
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
//        InOrOut start = new InOrOut();
//        //start.startMessure();
//    }
}
