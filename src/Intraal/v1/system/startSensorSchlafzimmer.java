/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;

import Intraal.v1.sensors.schlafzimmer.schlafzAmLightSensor;
import Intraal.v1.sensors.schlafzimmer.schlafzCO2Sensor;
import Intraal.v1.sensors.schlafzimmer.schlafzMotionSensor;
import Intraal.v1.sensors.schlafzimmer.schlafzPassageSensor;
import Intraal.v1.sensors.schlafzimmer.schlafzTemperaturSensor;
/**
 *
 * @author Turna
 */
public class startSensorSchlafzimmer {
    
    
    // Schlafzimmer Sensor Classes
    schlafzAmLightSensor s13 = new schlafzAmLightSensor();
    schlafzCO2Sensor s14 = new schlafzCO2Sensor();
    schlafzMotionSensor s15 = new schlafzMotionSensor();
    schlafzPassageSensor s16 = new schlafzPassageSensor();
    schlafzTemperaturSensor s17 = new schlafzTemperaturSensor();
    
    public void startMessure()throws Exception{
     
       // SCHLAFZIMMER
        s13.getLight();
        s14.getCO2();
        s15.doMotion();
        s16.doPassage();
        s17.getTemp();

    }
    
//    public static void main(String[] args) throws Exception {
//        new startSensorSchlafzimmer().startMessure();
//    }
    
}
