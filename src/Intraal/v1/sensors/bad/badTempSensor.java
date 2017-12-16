/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.sensors.bad;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.input.SiotDashboardInput;
import com.tinkerforge.BrickletTemperature;
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
public class badTempSensor implements MqttCallback {

    BrickletTemperature tinkerforg;

    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    IntraalSettings is;

    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "qvy";
    private final String ROOM = "Bad";
    private final String MODUL = "Temperature";
    /////////////////////////////////////////////////////
    
    public badTempSensor() {

    }

    /*
Connection with WLAN & MQTT Raspberry Pi Broker
     */
    public void connectHost() throws Exception {
        con = new Connections();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletTemperature(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgBadIP(), con.getTgPort());
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

    public void getTemp() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        // Add temperature reached listener (parameter has unit °C/100)
        tinkerforg.addTemperatureListener(new BrickletTemperature.TemperatureListener() {
            public void temperature(short temperature) {

                int toHigh = 25 * 100;
                int toLow = 21 * 100;

                if (temperature > toHigh) {
                    message.setPayload((temperature / 100.0 + " Grad => Hoch").getBytes());
                    message.setRetained(true);
                    message.setQos(0);
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                     if (is.siotDash == true){
                                sdi.setInputKey(con.getT_inputKey_bad());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
                } else if (temperature <= toLow) {
                    message.setPayload((temperature / 100.0 + " Grad => Tief").getBytes());
                    message.setRetained(true);
                    message.setQos(0);
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                    if (is.siotDash == true){
                                sdi.setInputKey(con.getT_inputKey_bad());       // inputKey
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

        // Set period for temperature callback to 1s (1000ms)
        // Note: The temperature callback is only called every second
        //       if the temperature has changed since the last call!
        tinkerforg.setTemperatureCallbackPeriod(10000);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room:Bad, Typ:Temperatur, Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room:Bad, Typ:Temperatur, Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Room:Bad, Typ:Temperatur, OK! =========== ");
    }
}
