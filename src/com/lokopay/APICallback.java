package com.lokopay;

import com.lokopay.sdk.lib.LokoAPIContent;
import com.lokopay.sdk.lib.LokoAuth;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This example shows to how to verify a call back request from lokopay
 */
public class APICallback {
//    final static String apiSecret = "MOQFbdCrNR5uggg4XA06V1hZ8RWfkmFsT+LGb9WSZmbhQMoAsxrbGfSyoUFE7n3ApbENneHZ3k+8dQI3NKcbNg==";

    public static void main(String[] args) throws Exception {

        // Start at a local test server listening at: localhost:8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new CallBackHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class CallBackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try (InputStream resourceInput = new FileInputStream("resources/config.properties")) {
                Properties prop = new Properties();

                // load a properties file
                prop.load(resourceInput);

                Headers headers = t.getRequestHeaders();

                // Get request headers
                String nonceStr = "";
                String signature = "";
                String host = "";
                for (Map.Entry<String, List<String>> entry :
                        headers.entrySet()) {
                    if (entry.getKey().equals("X-nonce")) {
                        nonceStr = entry.getValue().get(0);
                    }
                    if (entry.getKey().equals("X-signature")) {
                        signature = entry.getValue().get(0);
                    }
                    if (entry.getKey().equals("Host")) {
                        host = entry.getValue().get(0);
                    }
                }

                // Get request method and body
                InputStream is = t.getRequestBody();
                String requestMethod = t.getRequestMethod();
                String path = t.getRequestURI().toString();

                int available = is.available();
                byte[] array = new byte[available];
                is.read(array);
                String requestBody = new String(array, "UTF-8");

                // Verify signature
                LokoAuth auth = new LokoAuth(prop.getProperty("api.secret"));
                boolean isAuthorized = auth.VerifyAPISignature(signature, requestMethod, host + path, requestBody, Long.parseLong(nonceStr));

                // Return response
                String response;
                if (isAuthorized) {
                    response = "[YES] This API request is verified ";
                } else {
                    response = "[NO] This API request is NOT verified ";
                }
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }catch (IOException io) {
                io.printStackTrace();
                String response = "There is no config file ";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
