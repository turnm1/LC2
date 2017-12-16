///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Intraal.v1.siot.inputs.services;
//
//import Intraal.v1.host.Connections;
//import Intraal.v1.host.MQTTCommunication;
//import Intraal.v1.host.MQTTParameters;
//import Intraal.v1.siot.input.SiotDashboardInput;
//import com.tinkerforge.IPConnection;
//import java.net.URI;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author Turna
// */
//public class RoomLocation implements MqttCallback {
//
//    Connections con;
//    IPConnection ipcon;
//    MqttMessage MQTTmessage;
//    MQTTParameters p;
//    MQTTCommunication c;
//
//    // SIOT input
//    SiotDashboardInput sdi = new SiotDashboardInput();
//
//    /////////////////// EDIT HERE ///////////////////////
//    private final String UID = "Service2"; // keine
//    private final String ROOM = ""; // keine
//    private final String MODUL = "RoomLocation";
//    /////////////////////////////////////////////////////
//
//    private String personLocationRoom = " ";
//    private String messageVal;
//
//    public RoomLocation() {
//        try {
//            connectMQTT();
//        } catch (Exception ex) {
//            Logger.getLogger(RoomLocation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /*
//    Connection with WLAN & MQTT Raspberry Pi Broker
//     */
//    public void connectMQTT() throws Exception {
//        con = new Connections();
//        c = new MQTTCommunication();
//        p = new MQTTParameters();
//        p.setClientID(con.getClientIDTopic(UID));
//        p.setIsCleanSession(false);
//        p.setIsLastWillRetained(true);
//        p.setLastWillMessage("offline".getBytes());
//        p.setLastWillQoS(0);
//        p.setServerURIs(URI.create(con.getBrokerConnection()));
//        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
//        p.setMqttCallback(this);
//        c.connect(p);
//        c.publishActualWill("online".getBytes());
//        c.subscribe("Gateway/#", 0);
//        p.getLastWillMessage();
//    }
//    
//    public void doLocation() throws Exception {
////        MQTTmessage = new MqttMessage();
////        MQTTmessage.setRetained(true);
////        MQTTmessage.setQos(0);
////        MQTTmessage.setPayload(personLocationRoom.getBytes());
////        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), MQTTmessage);
////        sdi.setInputKey(con.getService_location());       // inputKey
////        sdi.setInputMessage(personLocationRoom);                // noch nicht schön
////        sdi.sendInput();
////        System.out.println("Room: " + personLocationRoom);
//        
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//            MQTTmessage = new MqttMessage();
//            messageVal = new String(message.getPayload());
//            String[] res = topic.split("/", 5);
//            String sendedRoom = res[3];
//            if (messageVal.equals("Motion Detected")){
//                if (!sendedRoom.equals(personLocationRoom)){
//                    sendedRoom = personLocationRoom;
//                    MQTTmessage.setRetained(true);
//                    MQTTmessage.setQos(0);
//                    MQTTmessage.setPayload(personLocationRoom.getBytes());
//                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), MQTTmessage);
//                    sdi.setInputKey(con.getService_location());       // inputKey
//                    sdi.setInputMessage(personLocationRoom);                // noch nicht schön
//                    sdi.sendInput();
//                    System.out.println("Room: " + personLocationRoom);
//                }
//            }
//        
////        if (topic.endsWith("value")) {
////            messageVal = new String(message.getPayload());
////            String[] res = topic.split("/", 5);
////            String sendedRoom = res[3];
////            if (messageVal.equals("Motion Ended")){
////                System.out.println(sendedRoom);
////                System.out.println(messageVal);
////                if (!sendedRoom.equals(personLocationRoom)){
////                personLocationRoom = sendedRoom;
////                 doLocation();
////                }
////            }
////        }
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        System.out.println(" =========== SERVICE: Room Location, Connection Lost =========== ");
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println(" =========== SERVICE: Room Location, Delivery Completed =========== ");
//    }
//    
//      public static void main(String[] args) throws Exception {
//        RoomLocation start = new RoomLocation();
//    }
//
//}
