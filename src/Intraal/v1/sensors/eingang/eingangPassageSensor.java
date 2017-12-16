/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.sensors.eingang;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.input.SiotDashboardInput;
import com.tinkerforge.BrickletDistanceIR;
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
public class eingangPassageSensor implements MqttCallback {

    BrickletDistanceIR tinkerforg;

    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    IntraalSettings is;

    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();


    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "tJ3";
    private final String ROOM = "Eingang";
    private final String MODUL = "Passage";
    /////////////////////////////////////////////////////
    
    private static int flag = 0;

    public eingangPassageSensor() {

    }

    /*
Connection with WLAN & MQTT Raspberry Pi Broker
     */
    public void connectHost() throws Exception {
        con = new Connections();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletDistanceIR(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgEingangIP(), con.getTgPort());
        /////////////////////////////////////////////////////
    }

    public void connectMQTT() throws Exception {
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
        p.getLastWillMessage();
    }

    /*
Infrarotsensor
     */
    public void doPassage() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        tinkerforg.addDistanceListener(new BrickletDistanceIR.DistanceListener() {
            public void distance(int distance) {
                message.setRetained(true);
                message.setQos(0);
                
                if (distance <= 350 && flag!=0) {
                    flag = 0;
                    message.setPayload("Passage Detected".getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                     if (is.siotDash == true){
                                sdi.setInputKey(con.getP_inputKey_eingang());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
                } else if (distance > 350 && flag != 1) {
                    flag = 1;
                    message.setPayload("No Passage".getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                     if (is.siotDash == true){
                                sdi.setInputKey(con.getP_inputKey_eingang());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
                }
            }
        });
        // Set period for distance callback to 0.5s (500ms)
        // Note: The distance callback is only called every 0.5 seconds
        //       if the distance has changed since the last call!
        tinkerforg.setDistanceCallbackPeriod(200);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Message Arrived =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Connection Lost motion_m1 =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }
}
