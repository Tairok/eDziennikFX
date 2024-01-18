package Client;

import NetworkTools.Packet;
import NetworkTools.PacketType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The ConnectionHandler class manages the communication with the server.
 * It handles the connection setup, sending and receiving messages, and closing the connection.
 */
public final class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);

    private static ConnectionHandler INSTANCE;
    private Socket socket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private boolean connected = false;

    private ConnectionHandler(String address, int port) {
        connect(address, port);
    }

    /**
     * Establishes a connection to the server with the specified address and port.
     *
     * @param address The server address.
     * @param port    The server port.
     */
    private void connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            logger.info("Connected to server: {}:{} - CONNECTED!", socket.getInetAddress().getHostAddress(), socket.getPort());
            connected = true;

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            logger.error("Unknown host: {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error connecting to server: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets an instance of the ConnectionHandler, establishing a new connection if needed.
     *
     * @param address The server address.
     * @param port    The server port.
     * @return The ConnectionHandler instance.
     */
    public static ConnectionHandler getInstance(String address, int port) {
        if (INSTANCE == null || !INSTANCE.connected) {
            INSTANCE = new ConnectionHandler(address, port);
        }
        return INSTANCE;
    }

    /**
     * Gets an instance of the ConnectionHandler.
     *
     * @return The ConnectionHandler instance.
     */
    public static ConnectionHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Sends a message to the server and receives a response.
     *
     * @param payload The message payload.
     * @param header  The message header.
     * @return The response packet from the server.
     */
    public Packet sendMessage(Object payload, PacketType header) {
        Packet packet = null;
        try {
            packet = new Packet(header, payload);
            outputStream.writeObject(packet);

            packet = (Packet) inputStream.readObject();

            switch (packet.getType()) {
                case ARRAY:
                    return packet;
                // Add more cases as needed based on PacketType values
            }

        } catch (ClassNotFoundException e) {
            logger.error("Error reading class from object stream: {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error sending/receiving message: {}", e.getMessage());
            e.printStackTrace();
        }

        return packet;
    }

    /**
     * Closes the connection to the server.
     */
    public void closeConnection() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
                socket.close();
                connected = false;
                logger.info("Connection closed.");
            }
        } catch (IOException e) {
            logger.error("Error closing connection: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
