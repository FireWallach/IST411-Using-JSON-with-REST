/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ist411usingjsonwithrest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Dylan
 */
public class mainHandler implements HttpHandler {

    public mainHandler() {
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Serving Request");
        String response="ERROR";
        
        if (he.getRequestMethod().equalsIgnoreCase("GET")) {
            response = "GET GOT";

            he.sendResponseHeaders(200, response.length());
            
        }

        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            response = "POST POSTED";

            he.sendResponseHeaders(200, response.length());

        }
        
        System.out.println(response);
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
    }

}
