/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static kodovi.Kodovi.PORT;

/**
 *
 * @author ivan
 */
public class Server {
    
    private void serverInit() throws IOException{
        ServerSocket server = new ServerSocket(PORT);
        while(true){
            Socket soket = server.accept();
            System.out.println("Klijent povezan");
            NitKlijent nit = new NitKlijent(soket);
            nit.start();
        }
    }
     
    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.serverInit();
    }
    
        
}
