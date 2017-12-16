/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.system;

import Intraal.v1.siot.inputs.services.DangerLevel;
import Intraal.v1.siot.inputs.services.InOrOut;
import Intraal.v1.siot.inputs.services.NotifyingCarer;

/**
 *
 * @author Turna
 */
public class startIntraal {
    
    startSensorsKüche ssk = new startSensorsKüche();
    startSensorsBad ssb = new startSensorsBad();
    startSensorWohnzimmer ssw = new startSensorWohnzimmer();
    startSensorSchlafzimmer sss = new startSensorSchlafzimmer();
    startSensorEingang sse = new startSensorEingang();

            
    
    public void letsgo()throws Exception{
//        ssk.startMessure();
 //       ssb.startMessure();
        ssw.startMessure();
//        sss.startMessure();
//        sse.startMessure();

    }
    
    public static void main(String[] args) throws Exception {
        new startIntraal().letsgo();
        
        // Services
        DangerLevel dl = new DangerLevel();
        NotifyingCarer nc = new NotifyingCarer();
        InOrOut ioo = new InOrOut();
    }
    
}
