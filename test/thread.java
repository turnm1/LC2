/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Turna
 */
class thread implements Runnable {
   private Thread t;

   public thread(){
   }
   
   @Override
   public void run() {
       for (int i=10; i>0; i--){
           System.out.println(i);
           try{
               TimeUnit.MILLISECONDS.sleep(1000);
           } catch (InterruptedException ex) {
               ex.printStackTrace();
           }
       }
   }
      
   public void start () {
        run();
   }
   
   public void stop(){
       t.stop();
   }
   
   public void sendDemoAlert(){
       t.stop();
       
   }
   
}
