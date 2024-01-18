package eDziennikFX;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import DbTools.Query;
import GuiTools.User;
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
                        List<Object[]> data = Query.select((String)packet.getPayload());
                        outputStream.writeObject(new Packet(PacketType.ARRAY, data));
                        break;
                    case ARRAY:

                        break;
                    case ERROR_MSG:

                        break;
                    case LOGIN_MSG:
                        String[] loginData = ((String) (packet.getPayload())).split("\n", 2);
                        Object[] user = User.checkUser(loginData[0], loginData[1]);
                        if(user != null){
                            outputStream.writeObject(new Packet(PacketType.ARRAY, user));
                        }
                        else{
                            outputStream.writeObject(new Packet(PacketType.ERROR_MSG, null));
                        }
                        break;
                }

            }while(packet.getType() != PacketType.END_MSG);

            System.out.println("Klient ID: " + clientID + " - ROZLACZONO!");

        }catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
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
