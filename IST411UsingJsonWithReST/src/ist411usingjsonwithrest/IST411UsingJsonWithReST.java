/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ist411usingjsonwithrest;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dylan
 */
public class IST411UsingJsonWithReST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/boards", new mainHandler());
            server.setExecutor(null); 
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(IST411UsingJsonWithReST.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
