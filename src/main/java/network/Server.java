package network;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The class Server represents a server that waits for clients to play the game "Robo Rally".
 */
public class Server {

    private final Logger logger = LogManager.getLogger(Server.class.getName());
    private final ServerSocket serverSocket;
    private final NetworkManager networkManager;

    /**
     * Constructor of Server
     * Contains the ServerSocket and the instance of NetworkManager
     *
     * @param serverSocket : socket of server
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.networkManager = NetworkManager.getInstance();
    }

    /**
     * In this method the server is waiting for a client.
     * If the connection is successful, a new Thread is made for the respective client.
     */
    public void startServer() {

        try {

            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                logger.debug("New client has connected to server");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServer();
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1235);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}