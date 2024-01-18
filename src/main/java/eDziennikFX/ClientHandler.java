package eDziennikFX;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import DbTools.Query;
import GuiTools.User;
import NetworkTools.*;

/**
 * The ClientHandler class manages communication with a client on the server side.
 */
public class ClientHandler implements Runnable {

    private static final Logger logger = LogManager.getLogger(ClientHandler.class.getName());

    private Socket clientSocket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private int clientID;
    private static final AtomicInteger clientIDCounter = new AtomicInteger(0);

    /**
     * Initializes a new ClientHandler for a client connection.
     *
     * @param socket The Socket representing the client connection.
     */
    public ClientHandler(Socket socket) {
        clientSocket = socket;
        clientID = nextID();
        logger.info("New client ID: " + clientID + " IP: " + socket.getInetAddress().getHostAddress() + " - CONNECTED");
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            Packet packet = null;

            do {
                packet = (Packet) inputStream.readObject();
                switch (packet.getType()) {
                    case QUERY:
                        List<Object[]> data = Query.select((String) packet.getPayload());
                        outputStream.writeObject(new Packet(PacketType.ARRAY, data));
                        break;
                    case ARRAY:
                        // Handle ARRAY packet type if needed
                        break;
                    case ERROR_MSG:
                        // Handle ERROR_MSG packet type if needed
                        break;
                    case LOGIN_MSG:
                        String[] loginData = ((String) (packet.getPayload())).split("\n", 2);
                        Object[] user = User.checkUser(loginData[0], loginData[1]);
                        if (user != null) {
                            outputStream.writeObject(new Packet(PacketType.ARRAY, user));
                        } else {
                            outputStream.writeObject(new Packet(PacketType.ERROR_MSG, null));
                        }
                        break;
                }

            } while (packet.getType() != PacketType.END_MSG);

            logger.info("Client ID: " + clientID + " - DISCONNECTED");

        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error in client handling: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("SQL error in client handling: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                logger.error("Error while closing resources: " + e.getMessage());
            }
        }
    }

    private int nextID() {
        return clientIDCounter.getAndIncrement();
    }
}
