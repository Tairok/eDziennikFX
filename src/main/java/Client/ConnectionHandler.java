package Client;

import java.net.*;
import java.io.*;
import NetworkTools.*;
import java.util.Scanner;


public final class ConnectionHandler {
    private static ConnectionHandler INSTANCE;
    private Socket socket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private boolean connected = false;

    private ConnectionHandler(String address, int port){
        connect(address, port);
    }

    private void connect(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Serwer: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " - POLACZONO!");
            connected = true;

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());


        }
        catch(UnknownHostException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static ConnectionHandler getInstance(String address, int port){
        if(INSTANCE == null || !INSTANCE.connected){
            INSTANCE = new ConnectionHandler(address, port);
        }
        return INSTANCE;
    }

    public static ConnectionHandler getInstance(){
        return INSTANCE;
    }


    public Packet sendMessage(Object payload, PacketType header){
        Packet packet = null;
        try{
            packet = new Packet(header, payload);
            outputStream.writeObject(packet);

            packet = (Packet) inputStream.readObject();

            switch(packet.getType()){
                case ARRAY:
                    return packet;


            }

        }

        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        /*finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
                if(inputStream != null){
                    inputStream.close();
                    socket.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }*/

        return packet;
    }

    public void closeConnection(){
        try{
            if(outputStream != null){
                outputStream.close();
            }
            if(inputStream != null){
                inputStream.close();
                socket.close();
                connected = false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
