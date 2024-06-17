package network;


import AI.DumbAI;
import gui.mvvm.chat.ChatModel;
import gui.mvvm.game.GameModel;
import gui.mvvm.lobby.LobbyModel;
import json.wrapper_utilities.Deserializer;
import json.wrapper_utilities.MessageHandlerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * The class Client represents a client who can either be a real user or an AI
 */
public class Client {
    private final Logger logger = LogManager.getLogger(Client.class.getName());

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private final String group = "NeidischeNarwale";
    private final String protocol = "Version 1.0";

    private boolean firstAlive = true;
    private int clientID;

    private final DumbAI ai = new DumbAI();

    private boolean isAI = false;

    private LobbyModel lobbyModel;

    private GameModel gameModel;
    private ChatModel chatModel;

    //    private ConnectionModel connectionModel;


    private String username;


    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            setupModels();
            ai.setClient(this);


        } catch (IOException e) {
            logger.debug("Error creating client :(");
            e.printStackTrace();
            closeEverything();
        }
    }

    public void sendMessage(String messageToSend) {
        try {
            if(!messageToSend.contains("Alive")) {
                logger.debug("Client sent: " + messageToSend);
            }

            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Error sending message to the client");
            closeEverything();
        }
    }

    public void listenForMessageFromServer() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {

                    String messageFromChat = bufferedReader.readLine();

                    MessageHandlerClient.handleMessages(Deserializer.deserializeData(messageFromChat), this);


                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug("Error receiving message :(");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }

    /**
     * The method sets up all models
     */
    public void setupModels() {
        lobbyModel = LobbyModel.getInstance();
        chatModel = ChatModel.getInstance();
        gameModel = GameModel.getInstance();

        lobbyModel.setClient(this);
        chatModel.setClient(this);
        gameModel.setClient(this);

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

    public Socket getSocket() {
        return socket;
    }

    public String getGroup() {
        return group;
    }

    public String getProtocol() {
        return protocol;
    }

    public boolean isFirstAlive() {
        return firstAlive;
    }

    public void setFirstAlive(boolean firstAlive) {
        this.firstAlive = firstAlive;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public DumbAI getAi() {
        return ai;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }


    public static Client start() throws IOException {
        Socket socket = new Socket("localhost", 1235);
//        Socket socket = new Socket("sep21.dbs.ifi.lmu.de",52019);
        Client client = new Client(socket);
        client.listenForMessageFromServer();


        return client;
    }

    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("localhost", 1235);
        Socket socket = new Socket("sep21.dbs.ifi.lmu.de",52019);
        Client client = new Client(socket);
        client.listenForMessageFromServer();

    }
}
