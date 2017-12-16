/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intraal.v1.siot.input;

import Intraal.v1.host.Connections;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Turna
 */
public class SiotDashboardMessageInput {
    
    Connections siotCon = new Connections();
  //  private String USER_AGENT = "Mozilla/5.0";
  //  private String URL = "https://siot.net:12955";
  //  private String licence;
    public String inputKey;
    public String data;

    public void setInputTextMessage(String message) {
        data = message;
    }
    
    public void setInputTextKey(String key) {
        inputKey = key;
    }
    

    // HTTP POST request
    public void sendTextInput() throws Exception {

        String url = siotCon.getSIOTURL() + "/mqtt/request";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", siotCon.getSIOTUSER_AGENT()); //USER_AGENT
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "message=" + data + "&topic=siot/DAT/" + siotCon.getSIOTLicence() + "/" + inputKey;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }
   
}
