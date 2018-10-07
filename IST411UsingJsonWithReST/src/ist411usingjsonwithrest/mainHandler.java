/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ist411usingjsonwithrest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Gson gson = new GsonBuilder().create();
        String response = "ERROR";

        //Get the request headers
        Headers requestHeaders = he.getRequestHeaders();
        Set<String> keySet = requestHeaders.keySet();
        keySet.stream().map((key) -> {
            List values = requestHeaders.get(key);
            String header = key + " = " + values.toString() + "\n";
            return header;
        }).forEachOrdered((header) -> {
            System.out.print(header);
        });

        //GET handling
        if (he.getRequestMethod().equalsIgnoreCase("GET")) {

            response = gson.toJson(BoardsList.getInstance().getBoardsList());
            he.sendResponseHeaders(200, response.length());

        }

        //POST handling
        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            ArrayList currBoardsList = BoardsList.getInstance().getBoardsList();
            response = "POST POSTED";

            int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
            InputStream inputStream = he.getRequestBody();
            byte[] cargo = new byte[contentLength];
            int length = inputStream.read(cargo);
            String inJson = new String(cargo, "UTF-8");

            Board inBoard = gson.fromJson(inJson, Board.class);
            currBoardsList.add(inBoard);

            he.sendResponseHeaders(200, response.length());

        }

        System.out.println(response);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
