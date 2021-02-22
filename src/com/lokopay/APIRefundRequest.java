package com.lokopay;

import com.lokopay.sdk.lib.LokoAuth;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;

/**
 * This example shows to how to send a API request to lokopay.
 */
public class APIRefundRequest {
    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void main(String[] args) {
        try (InputStream resourceInput = new FileInputStream("resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(resourceInput);

            // Set up LokoAuth
            LokoAuth auth = new LokoAuth(prop.getProperty("api.secret"));

            // Set up connection
            URL url = new URL(prop.getProperty("api.host") + "/refund");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Read request body
            String file = "src/com/lokopay/API_Create_Refund_Request_Body_V1.json";
            String apiRequestBody = readFileAsString(file);

            // Generate API signature
            long nonce = Instant.now().getEpochSecond();
            String signature = auth.GenerateAPISignature("POST", prop.getProperty("api.host") + "/refund", apiRequestBody, nonce);

            // Set up request Headers
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("x-api-key", prop.getProperty("api.key"));
            connection.setRequestProperty("x-signature", signature);
            connection.setRequestProperty("x-nonce", "" + nonce);

            // Set up request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = apiRequestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Send and print response
            connection.getResponseCode();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("response from lokopay: " + response.toString());
            }
            connection.disconnect();
        }catch (Exception e) {e.printStackTrace();
        }
    }
}

