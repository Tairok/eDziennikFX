package Client;

import java.net.*;
import java.io.*;
import NetworkTools.*;
import java.util.Scanner;


public class ConnectionHandler {
    private Socket socket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    public ConnectionHandler(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("Serwer: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " - POLACZONO!");

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

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

            //outputStream.close();
            //inputStream.close();
            //socket.close();


        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        catch(IOException e){
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
                    socket.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
