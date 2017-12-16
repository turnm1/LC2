/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Intraal.v1.siot.inputs.services.InOrOut;
import Intraal.v1.system.startSensorsKüche;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Turna
 */
public class TestThread {

   public static void main(String args[]) throws Exception {
       
      // ExecutorService exeS = Executors.newFixedThreadPool(1);
      // ExecutorService exeS = Executors.newCachedThreadPool();
      // exeS.execute(new thread());
      // exeS.execute(new InOrOut());
      //exeS.execute(new startSensorsKüche());
       
      thread R1 = new thread();
      R1.start();
     
   }   
}