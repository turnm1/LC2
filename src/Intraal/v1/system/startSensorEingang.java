/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;


import Intraal.v1.sensors.eingang.eingangAmLightSensor;
import Intraal.v1.sensors.eingang.eingangMotionSensor;
import Intraal.v1.sensors.eingang.eingangPassageSensor;
import Intraal.v1.sensors.eingang.eingangTempSensor;


/**
 *
 * @author Turna
 */
public class startSensorEingang {
        
    // Eingang Sensor Classes
    eingangMotionSensor s5 = new eingangMotionSensor();
    eingangTempSensor s6 = new eingangTempSensor();
    eingangPassageSensor s7 = new eingangPassageSensor();
    eingangAmLightSensor s8 = new eingangAmLightSensor();
    
    
    public void startMessure()throws Exception{
       // EINGANG
       s5.doMotion();
       s6.getTemp();
       s7.doPassage();
       s8.getLight();
    }
    
//    public static void main(String[] args) throws Exception {
//        new startSensorEingang().startMessure();
//    }
    
}
