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
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author Turna
// */
//public class SensorStatus implements MqttCallback {
//
//    Connections con;
//    IPConnection ipcon;
//    MqttMessage message;
//    MQTTParameters p;
//    MQTTCommunication c;
//
//    // SIOT input
//    SiotDashboardInput sdi = new SiotDashboardInput();
//    
//    /////////////////// EDIT HERE ///////////////////////
//    private final String UID = "ServiceOnOff"; // keine
//    private final String ROOM = ""; // keine
//    private final String MODUL = "SensorStatus";
//    /////////////////////////////////////////////////////
//
//    public SensorStatus() throws Exception {
//        connectMQTT();
//    }
//
//
//     /*
//Connection with WLAN & MQTT Raspberry Pi Broker
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
//        c.subscribe(con.getBrokerTopic(), 0);
//        p.getLastWillMessage();
//    }
//    
//    
//    public void doObserver() throws Exception {
//        connectMQTT();
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage mm) throws Exception {
//
//        if (topic.endsWith("connection")) {
//            String messageVal = new String(mm.getPayload());
//            String[] res = topic.split("/", 5);
//            String sendedSensModul = res[2];
//            String sendedRoom = res[3];
//            if (messageVal.equals("offline")) {
//                // Send Temperatur Value to SIOT Dashboard
//                if (sendedSensModul.equals("Temperature")) {
//                    if (sendedRoom.equals("Eingang")) {
//                        sdi.setInputKey(con.getT_inputKey_eingang());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Küche")) {
//                        sdi.setInputKey(con.getT_inputKey_küche());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Schlafzimmer")) {
//                        sdi.setInputKey(con.getT_inputKey_schlafz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Wohnzimmer")) {
//                        sdi.setInputKey(con.getT_inputKey_wohnz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Bad")) {
//                        sdi.setInputKey(con.getT_inputKey_bad());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    }
//                }
//                // Send Ambiente Light Value to SIOT Dashboard
//                if (sendedSensModul.equals("AmbienteLight")) {
//                    if (sendedRoom.equals("Eingang")) {
//                        sdi.setInputKey(con.getAl_inputKey_eingang());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Küche")) {
//                        sdi.setInputKey(con.getAl_inputKey_küche());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Schlafzimmer")) {
//                        sdi.setInputKey(con.getAl_inputKey_schlafz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Wohnzimmer")) {
//                        sdi.setInputKey(con.getAl_inputKey_wohnz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Bad")) {
//                        sdi.setInputKey(con.getAl_inputKey_bad());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    }
//                }
//                // Send Motion Value to SIOT Dashboard
//                if (sendedSensModul.equals("Motion")) {
//                    if (sendedRoom.equals("Eingang")) {
//                        sdi.setInputKey(con.getM_inputKey_eingang());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Küche")) {
//                        sdi.setInputKey(con.getM_inputKey_küche());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Schlafzimmer")) {
//                        sdi.setInputKey(con.getM_inputKey_schlafz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Wohnzimmer")) {
//                        sdi.setInputKey(con.getM_inputKey_wohnz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Bad")) {
//                        sdi.setInputKey(con.getM_inputKey_bad());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    }
//                }
//                // Send Motion Value to SIOT Dashboard
//                if (sendedSensModul.equals("Passage")) {
//                    if (sendedRoom.equals("Eingang")) {
//                        sdi.setInputKey(con.getP_inputKey_eingang());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Küche")) {
//                        sdi.setInputKey(con.getP_inputKey_küche());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Schlafzimmer")) {
//                        sdi.setInputKey(con.getP_inputKey_schlafz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Wohnzimmer")) {
//                        sdi.setInputKey(con.getP_inputKey_wohnz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Bad")) {
//                        sdi.setInputKey(con.getP_inputKey_bad());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    }
//                }
//                 // Send CO2 to SIOT Dashboard
//                if (sendedSensModul.equals("CO2")) {
//                    if (sendedRoom.equals("Schlafzimmer")) {
//                        sdi.setInputKey(con.getCo2_inputKey_schlafz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    } if (sendedRoom.equals("Wohnzimmer")) {
//                        sdi.setInputKey(con.getCo2_inputKey_wohnz());       // inputKey
//                        sdi.setInputMessage(messageVal);
//                        sdi.sendInput();
//                    }
//                }
//            }
//            
//        }
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        //  System.out.println(" =========== Connection Lost =========== ");
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        //   System.out.println(" =========== Delivery Completed =========== ");
//    }
//
//        public static void main(String[] args) throws Exception {
//        SensorStatus start = new SensorStatus();
//    }
//    
//}