package com.appraisal.utility;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
public class ServerConnection {
	public String sendGet(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            String encoding = Base64.getEncoder().encodeToString("NEPS:$2a$12$LtbYtEzOBdnsiD/E9Wtj2O3qeNaeYFP5jOqAmWX/3pUCK2yBn48Ta".getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + encoding);
            conn.setRequestProperty(HttpHeaders.ACCEPT, "application/json");
            conn.setRequestMethod("GET");

//            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println("Response Payload is " + response);

            }
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {

        }
        return response.toString();
    }

	/*
	 * public String sendPost(String url, String param) { StringBuilder response =
	 * new StringBuilder(); try { URL obj = new URL(url); HttpURLConnection conn =
	 * (HttpURLConnection) obj.openConnection(); conn.setRequestMethod("POST");
	 * String encoding = Base64.getEncoder().encodeToString(
	 * "NEPS:$2a$12$LtbYtEzOBdnsiD/E9Wtj2O3qeNaeYFP5jOqAmWX/3pUCK2yBn48Ta".getBytes(
	 * "UTF-8")); conn.setRequestProperty("Authorization", "Basic " + encoding);
	 * conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 * 
	 * // Send post request conn.setDoOutput(true); DataOutputStream wr = new
	 * DataOutputStream(conn.getOutputStream()); wr.writeBytes(param); wr.flush();
	 * wr.close();
	 * 
	 * BufferedReader in = new BufferedReader(new
	 * InputStreamReader(conn.getInputStream())); String inputLine;
	 * 
	 * while ((inputLine = in.readLine()) != null) { response.append(inputLine); } }
	 * catch (MalformedURLException ex) {
	 * Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null,
	 * ex); } catch (IOException e) {
	 * 
	 * } return response.toString(); }
	 */
    public String post(String url, StringEntity entity) {
        StringBuilder serverResponse = new StringBuilder();
        try {
            //System.err.println("Bill Advice URL: " + url);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");

            String encoding = Base64.getEncoder().encodeToString("NEPS:$2a$12$LtbYtEzOBdnsiD/E9Wtj2O3qeNaeYFP5jOqAmWX/3pUCK2yBn48Ta".getBytes("UTF-8"));
            //byte[] message = "NEPS:12345678".getBytes("UTF-8");
            //String encoding = DatatypeConverter.printBase64Binary(message);

            httpPost.addHeader("Authorization", "Basic " + encoding);
            httpPost.setEntity(entity);

            DefaultHttpClient client = new DefaultHttpClient();

            HttpResponse response = client.execute(httpPost);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                serverResponse.append(line);
            }
        } catch (Exception e) {
            return "error";
        }
        return serverResponse.toString();
    }

}
