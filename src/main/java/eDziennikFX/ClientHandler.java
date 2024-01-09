package eDziennikFX;

import java.io.*;
import java.net.*;
import java.util.*;
import NetworkTools.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private int clientID;
    private static int clientIDCounter = 0;

    public ClientHandler(Socket socket){
        clientSocket = socket;
        clientID = nextID();
        System.out.println("Nowy klient ID: " + clientID + " IP: " + socket.getInetAddress().getHostAddress() + " - POLACZONO");
    }

    @Override
    public void run(){
        try{
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            Packet packet = null;

            do{
                packet = (Packet) inputStream.readObject();
                switch(packet.getType()){
                    case QUERY:

                        break;
                    case ARRAY:

                        break;
                    case ERROR_MSG:

                        break;
                }

            }while(packet.getType() != PacketType.END_MSG);

            System.out.println("Klient ID: " + clientID + " - ROZLACZONO!");

        }catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
                if(inputStream != null){
                    inputStream.close();
                    clientSocket.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    private static int nextID(){
        return clientIDCounter++;
    }
}
