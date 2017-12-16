/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.sensors.wohnzimmer;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.host.MQTTParameters;
import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.input.SiotDashboardInput;
import com.tinkerforge.BrickletCO2;
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
public class wohnzCO2Sensor implements MqttCallback {

    BrickletCO2 tinkerforg;

    Connections con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    IntraalSettings is;

    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "x7e";
    private final String ROOM = "Wohnzimmer";
    private final String MODUL = "CO2";
    /////////////////////////////////////////////////////

    public wohnzCO2Sensor() {

    }

    /*
Connection with WLAN & MQTT Raspberry Pi Broker
     */
    public void connectHost() throws Exception {
        con = new Connections();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletCO2(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgWohnzimmerIP(), con.getTgPort());
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

    public void getCO2() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        // Add temperature reached listener (parameter has unit °C/100)
        tinkerforg.addCO2ConcentrationListener(new BrickletCO2.CO2ConcentrationListener() {
            public void co2Concentration(int co2Concentration) {
                message.setRetained(true);
                message.setQos(0);

                if (co2Concentration >= 800) {
                    message.setPayload((co2Concentration + " = > normal").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                    if (is.siotDash == true){
                                sdi.setInputKey(con.getCo2_inputKey_wohnz());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
                } else if (co2Concentration < 800) {
                    message.setPayload((co2Concentration + " = > hoch").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                    if (is.siotDash == true){
                                sdi.setInputKey(con.getCo2_inputKey_wohnz());       // inputKey
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

        // Set period for CO2 concentration callback to 1min = 60000 (1000ms = 1sek)
        // Note: The CO2 concentration callback is only called every second
        //       if the CO2 concentration has changed since the last call!
        tinkerforg.setCO2ConcentrationCallbackPeriod(60000);
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
