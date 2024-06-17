package network;


import gamelogic.Robot;
import gamelogic.lobby.Lobby;
import gamelogic.round.Game;
import json.json_wrappers.connection.HelloServer;
import json.wrapper_utilities.Deserializer;
import json.wrapper_utilities.MessageHandlerServer;
import json.wrapper_utilities.WrapperClassSerialization;
import messages.MessagesError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The class ClientHandler
 */
public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class.getName());

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private int clientID;
    private NetworkManager networkManager;

    private final String protocol = "Version 1.0";
    private boolean aliveReceived = false;
    private int connectionCount = 0;
    private long aliveReceivedAt = 0;
    private Timer timer = new Timer();

    private String username;

    private Robot playerCharacter;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        try {
            this.networkManager = NetworkManager.getInstance();

            handshake();
            listenForMessageFromClient();
        } catch (IOException e) {
            closeEverything();
        }
    }

    /**
     * The method handles the handshake between Client and Server
     * It sends HelloClient, then waits for HelloServer.
     * When successful, it sends Welcome to client.
     *
     * @throws IOException
     */
    public void handshake() throws IOException {

        long start;

        sendMessageToSelf(WrapperClassSerialization.serializeHelloClient(protocol));

        start = System.currentTimeMillis();

        while (true) {

            String hopefullyHelloServer = bufferedReader.readLine();

            boolean isHelloServerMessage = false;

            if (Deserializer.deserializeData(hopefullyHelloServer) != null) {
                isHelloServerMessage = Deserializer.deserializeData(hopefullyHelloServer).getClass().equals(HelloServer.class);
            }


            if (isHelloServerMessage) {

                break;
            } else if ((System.currentTimeMillis() - start) > 5) {
                //disconnect

                sendMessageToSelf(WrapperClassSerialization.serializeError(MessagesError.ERROR_ERROR));

            }
        }

        String json = WrapperClassSerialization.serializeWelcome(createClientID());
        logger.debug("Send Welcome to Client.");

        sendMessageToSelf(json);

        if (networkManager.getStateOfGame().size() != 0) {
            networkManager.sendStateOfGame(this);
        }

        initializeTimer();

        logger.debug(networkManager.getClientHandlers());
        networkManager.getClientHandlers().add(this);
        logger.debug(networkManager.getClientHandlers());
        networkManager.getClientIDs().add(this.clientID);
        logger.debug(networkManager.getClientIDs());
    }

    /**
     * The method initializes the timer for the alive messages
     */
    public void initializeTimer() {

        TimerTask sender = new AliveSender(this);
        TimerTask waiter = new AliveWaiter(this);

        timer.schedule(sender, 0, 5000);
        timer.schedule(waiter, 4500, 5000);
    }

    /**
     * The method creates a random  unique clientID
     *
     * @return int : clientID
     */
    public int createClientID() {
        Random random = new Random();

        int iD = random.nextInt(1000);

        boolean clientIDTaken = true;

        if (networkManager.getClientIDs() != null) {
            while (clientIDTaken) {

                iD = random.nextInt(1000);

                if (!networkManager.getClientIDs().contains(iD)) {
                    clientIDTaken = false;

                }
            }
        }

        setClientID(iD);

        return iD;
    }

    public void listenForMessageFromClient() throws IOException {

        while (socket.isConnected()) {
            String clientMessage = bufferedReader.readLine();

            handleMessage(clientMessage);

        }
    }


    public void handleMessage(String message) {

        MessageHandlerServer messageHandlerServer = new MessageHandlerServer();

        messageHandlerServer.handleMessages(Deserializer.deserializeData(message), this);

    }

    public void sendMessageToSpecificClient(String message, int clientID) {
        try {
            for (ClientHandler clientHandler : networkManager.getClientHandlers()) {
                if ((clientHandler == this) || clientHandler.getClientID() == (clientID)) {

                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendMessageToSelf(String message) {
        try {

            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            handleLostConnection();
        }
    }

    public void sendMessageToEveryoneElse(String message) {
        try {

            for (ClientHandler clientHandler : networkManager.getClientHandlers()) {
                if (!(clientHandler.getClientID() == clientID)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Error sending message to the client");
            handleLostConnection();
        }
    }

    public void sendMessageToAllClients(String messageToSend) {
        try {
            networkManager.getStateOfGame().add(messageToSend);

            for (ClientHandler clientHandler : networkManager.getClientHandlers()) {
                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Error sending message to the client");
            handleLostConnection();
        }
    }

    public void handleLostConnection() {
        Lobby lobby = networkManager.getLobby();
        lobby.removePlayer(clientID);
        Game game = lobby.getGame();
        if (game.isGameRunning()) {
            game.removePlayer(this);
        }

        timer.cancel();
        removeClientHandler();
        if (networkManager.getClientHandlers().size() > 0) {
            sendConnectionUpdate();
        }

//        if(game.isGameRunning() && game.getPlayers().size() < 2) {
//            game.finishGame();
//        }
    }

    public void sendConnectionUpdate() {
        String json = WrapperClassSerialization.serializeConnectionUpdate(clientID, false, "remove");

        sendMessageToAllClients(json);
    }

    public void removeClientHandler() {
        networkManager.getClientHandlers().remove(this);
        Integer clientID = this.clientID;
        networkManager.getClientIDs().remove(clientID);
        closeEverything();
    }

    public void closeEverything() {
        try {

            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public boolean isAliveReceived() {
        return aliveReceived;
    }

    public void setAliveReceived(boolean aliveReceived) {
        this.aliveReceived = aliveReceived;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public void setAliveReceivedAt(long aliveReceivedAt) {
        this.aliveReceivedAt = aliveReceivedAt;
    }

    public long getAliveReceivedAt() {
        return aliveReceivedAt;
    }

}
