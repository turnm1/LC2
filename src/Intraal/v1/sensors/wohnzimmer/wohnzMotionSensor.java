/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.sensors.wohnzimmer;

import Intraal.v1.host.Connections;
import Intraal.v1.host.MQTTCommunication;
import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.settings.IntraalSettings;
import Intraal.v1.siot.input.SiotDashboardInput;
import com.tinkerforge.BrickletMotionDetector;
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
public class wohnzMotionSensor implements MqttCallback {

BrickletMotionDetector tinkerforg;
    
Connections con;
IPConnection ipcon;
MqttMessage message;
MQTTParameters p;
MQTTCommunication c;
        IntraalSettings is;

    // SIOT input
    SiotDashboardInput sdi = new SiotDashboardInput();
/////////////////// EDIT HERE ///////////////////////
private final String MODUL = "Motion";
private final String UID = "wtF";
private final String ROOM = "Wohnzimmer";
/////////////////////////////////////////////////////

public wohnzMotionSensor() {
}

/*
Connection with WLAN & MQTT Raspberry Pi Broker
*/
public void connectHost()throws Exception{
        con = new Connections();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletMotionDetector(UID,ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgWohnzimmerIP(), con.getTgPort());
        /////////////////////////////////////////////////////
}
public void connectMQTT()throws Exception{
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
Motionsensor
*/
public void doMotion() throws Exception {
       connectHost();
       connectMQTT();
       message = new MqttMessage();
        // send (id, value, unit, date, time, status)
        
                // Add motion detected listener
		tinkerforg.addMotionDetectedListener(new BrickletMotionDetector.MotionDetectedListener() {
			public void motionDetected() {
                            // publish Value
                            message.setPayload("Motion Detected".getBytes());
                            message.setRetained(true);
                            message.setQos(0);
                            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                            System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID)+": "+message);
                            if (is.siotDash == true){
                                sdi.setInputKey(con.getM_inputKey_wohnz());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
			}
		});
		// Add detection cycle ended listener
		tinkerforg.addDetectionCycleEndedListener(new BrickletMotionDetector.DetectionCycleEndedListener() {
			public void detectionCycleEnded() {
                            
                            message.setPayload("Motion Ended".getBytes());
                            message.setRetained(true);
                            message.setQos(0);
                            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                            System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID)+": "+message);
                            if (is.siotDash == true){
                                sdi.setInputKey(con.getM_inputKey_wohnz());       // inputKey
                                sdi.setInputMessage(message.toString());
                                try {
                                    sdi.sendInput();
                                } catch (Exception ex) {
                                    Logger.getLogger(badAmLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
			}
		});
}


    @Override
    public void messageArrived(String topic, MqttMessage message)throws Exception {
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
