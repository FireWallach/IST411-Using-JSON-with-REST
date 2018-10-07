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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        ArrayList<Board> currBoardsList = BoardsList.getInstance().getBoardsList();
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
            Map<String, String> requestParams = new HashMap<>();
            if (he.getRequestURI().getQuery() != null) {
                requestParams = queryToMap(he.getRequestURI().getQuery());
            }
            if (requestParams.isEmpty()) {
                response = gson.toJson(BoardsList.getInstance().getBoardsList());
                he.sendResponseHeaders(200, response.length());
            } else if (requestParams.containsKey("title")) {

                for (Board b : currBoardsList) {
                    if (b.getTitle().equalsIgnoreCase(requestParams.get("title"))) {
                        response = gson.toJson(b);
                        he.sendResponseHeaders(200, response.length());
                    }
                }
                if (response.equalsIgnoreCase("ERROR")) {
                    response = "Board not found.";
                    he.sendResponseHeaders(404, response.length());
                }
            }

        }

        //POST handling
        if (he.getRequestMethod().equalsIgnoreCase("POST")) {

            boolean nameExists = false;

            int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
            InputStream inputStream = he.getRequestBody();
            byte[] cargo = new byte[contentLength];
            int length = inputStream.read(cargo);
            String inJson = new String(cargo, "UTF-8");

            Board inBoard = gson.fromJson(inJson, Board.class);

            for (Board board : currBoardsList) {
                if (inBoard.getTitle().equalsIgnoreCase(board.getTitle())) {
                    nameExists = true;
                }
            }

            if (inBoard == null) {
                response = "Malformed JSON.";
                he.sendResponseHeaders(400, response.length());
            } else if (nameExists) {
                response = "Board with that name already exists";
                he.sendResponseHeaders(400, response.length());
            } else {
                currBoardsList.add(inBoard);
                response = "Board added.";
                he.sendResponseHeaders(200, response.length());
            }

        }

        System.out.println(response);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * **************************************************************************************
     * Title: Get URL parameters using JDK HTTP server Author: Real'sHowTo Date:
     * 10/07/2018 Code version: 1.0 Availability:
     * http://www.rgagnon.com/javadetails/java-get-url-parameters-using-jdk-http-server.html
     * Code used: queryToMap
     * *************************************************************************************
     */
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

}
