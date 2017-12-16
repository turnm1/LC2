/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;

import Intraal.v1.sensors.wohnzimmer.wohnzAmLightSensor;
import Intraal.v1.sensors.wohnzimmer.wohnzCO2Sensor;
import Intraal.v1.sensors.wohnzimmer.wohnzMotionSensor;
import Intraal.v1.sensors.wohnzimmer.wohnzPassageSensor;
import Intraal.v1.sensors.wohnzimmer.wohnzTempSensor;

/**
 *
 * @author Turna
 */
public class startSensorWohnzimmer {
    
    // Wohnzimmer Sensor Classes
    wohnzAmLightSensor s18 = new wohnzAmLightSensor();
    wohnzCO2Sensor s19 = new wohnzCO2Sensor();
    wohnzMotionSensor s20 = new wohnzMotionSensor();
    wohnzPassageSensor s21 = new wohnzPassageSensor();
    wohnzTempSensor s22 = new wohnzTempSensor();
       
    public void startMessure()throws Exception{
       
       // WOHNZIMMER 
       //s18.getLight();
      // s19.getCO2();
       s20.doMotion();
   //    s21.doPassage();
     //  s22.getTemp();
       
    }
    
//    public static void main(String[] args) throws Exception {
//        new startSensorWohnzimmer().startMessure();
//    }
//    
}
