/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;

import Intraal.v1.sensors.bad.badAmLightSensor;
import Intraal.v1.sensors.bad.badMotionSensor;
import Intraal.v1.sensors.bad.badPassageSensor;
import Intraal.v1.sensors.bad.badTempSensor;


/**
 *
 * @author Turna
 */
public class startSensorsBad {
    
    // Bad Sensor Classes
    badMotionSensor s1 = new badMotionSensor();
    badTempSensor s2 = new badTempSensor();
    badPassageSensor s3 = new badPassageSensor();
    badAmLightSensor s4 = new badAmLightSensor();
     
    
    public void startMessure()throws Exception{
        // BAD
       s1.doMotion();
       s2.getTemp();
       s3.doPassage();
       s4.getLight();
    }
    
//    public static void main(String[] args) throws Exception {
//        new startSensorsBad().startMessure();
//    }
    
}
