/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.siot.output;

import Intraal.v1.host.Connections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Turna
 */
public class SiotGetDashboardMessageSetting {
     private final String USER_AGENT = "Mozilla/5.0";
    Connections siotCon = new Connections();
    public String inputKey;
    public String data;

    public String getOutputGetMessageSetting() {
       return data;
    }
    
    public void setOutputKey(String key) {
        inputKey = key;
    }
    
    // HTTP GET request
    private void sendGetData() throws Exception {

        String url = siotCon.getMessageOnOff() + "/mqtt/request";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Post Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {//read line by line
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Get-Message (URL): " + url);
        System.out.println("Get-Message (JSON): " + response.toString());
    }
    
//    public static void main(String[] args) throws Exception {
//
//        SiotGetDashboardMessageSetting restTest = new SiotGetDashboardMessageSetting();
//        System.out.println("Testing SIOT - Send Https <getData> request:");
//        restTest.sendGetData();
//
//    }
}
