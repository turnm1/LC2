/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;

import Intraal.v1.sensors.küche.kücheAmLightSensor;
import Intraal.v1.sensors.küche.kücheMotionSensor;
import Intraal.v1.sensors.küche.küchePassageSensor;
import Intraal.v1.sensors.küche.kücheTemperaturSensor;

/**
 *
 * @author Turna
 */
public class startSensorsKüche {
    
    // Küche Sensor Classes
    kücheAmLightSensor s9 = new kücheAmLightSensor();
    kücheMotionSensor s10 = new kücheMotionSensor();
    küchePassageSensor s11 = new küchePassageSensor();
    kücheTemperaturSensor s12 = new kücheTemperaturSensor();
  
    
    public void startMessure()throws Exception{
      
         // KÜCHE
         s9.getLight();
         s10.doMotion();
        // s11.doPassage();
         s12.getTemp();

    }
    
//    public static void main(String[] args) throws Exception {
//        new startSensorsKüche().startMessure();
//    }
    
}
