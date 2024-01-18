package eDziennikFX;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server class listens for incoming client connections and handles them using a ClientHandler.
 */
public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class.getName());

    private ServerSocket server;

    public Server(int port) {
        try {

            server = new ServerSocket(port);
            server.setReuseAddress(true);

            logger.info("Server started on port " + port);


            while (true) {
                Socket client = server.accept();
                logger.info("Accepted connection from " + client.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            logger.error("Error in the server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (server != null && !server.isClosed()) {
                try {
                    server.close();
                    logger.info("Server stopped");
                } catch (IOException e) {
                    logger.error("Error while closing the server: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
