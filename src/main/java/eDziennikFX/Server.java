package eDziennikFX;

import java.net.*;
import java.io.*;
import NetworkTools.*;

public class Server {
    private Socket client = null;
    private ServerSocket server = null;

    public Server(int port){
        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            while(true){
                Socket client = server.accept();
                ClientHandler clientSocket = new ClientHandler(client);
                new Thread(clientSocket).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if(server != null){
                try{
                    server.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
