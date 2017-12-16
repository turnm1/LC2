/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.sensors.küche;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.input.SiotDashboardInput;
import com.tinkerforge.BrickletAmbientLightV2;
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
public class kücheAmLightSensor implements MqttCallback {

    BrickletAmbientLightV2 tinkerforg;

    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
        IntraalSettings is;

    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "yiz";
    private final String ROOM = "Küche";
    private final String MODUL = "AmbienteLight";
    /////////////////////////////////////////////////////
    
    private final static int offValue = 90 * 100;
    private final static int onValue = 100 * 100;

    public kücheAmLightSensor() {

    }

    /*
Connection with WLAN & MQTT Raspberry Pi Broker
     */
    public void connectHost() throws Exception {
        con = new Connections();
        ipcon = new IPConnection();
        tinkerforg = new BrickletAmbientLightV2(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgKücheIP(), con.getTgPort());
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
    public void getLight() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        tinkerforg.addIlluminanceReachedListener(new BrickletAmbientLightV2.IlluminanceReachedListener() {
            public void illuminanceReached(long illuminance) {

                message.setRetained(true);
                message.setQos(0);

                if (illuminance < offValue) {
                    message.setPayload((illuminance / 10.0 + " lux => Licht aus").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                     if (is.siotDash == true){
                        sdi.setInputKey(con.getAl_inputKey_küche());       // inputKey
                        sdi.setInputMessage(message.toString());
                        try {
                            sdi.sendInput();
                        } catch (Exception ex) {
                            Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     }
                } else if (illuminance >= onValue) {
                    message.setPayload((illuminance / 10.0 + "lux => Licht ein").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                    if (is.siotDash == true){
                        sdi.setInputKey(con.getAl_inputKey_küche());       // inputKey
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

        // Get threshold callbacks with a debounce time of 10 seconds (10000ms)
        tinkerforg.setDebouncePeriod(10000);
        // Configure threshold for illuminance "greater than 500 Lux" (unit is Lux/100)
        tinkerforg.setIlluminanceCallbackThreshold('o', offValue, onValue);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room:Küche, Typ:Ambient Light, Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room:Küche, Typ:Ambient Light, Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Room:Küche, Typ:Ambient Light, OK! =========== ");
    }
}
