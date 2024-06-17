package network;


import gamelogic.lobby.Lobby;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The class NetworkManager manages all clientHandlers and the corresponding clientIDs
 */
public class NetworkManager {
    private static volatile NetworkManager networkManager;

    private static final Logger logger = LogManager.getLogger(NetworkManager.class.getName());

    private final ArrayList<Integer> clientIDs = new ArrayList<>();

    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private ArrayList<String> stateOfGame = new ArrayList<>();

    private Lobby lobby = new Lobby();


    public NetworkManager() {

    }

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            synchronized (NetworkManager.class) {
                if (networkManager == null) {
                    networkManager = new NetworkManager();
                }
            }
        }
        return networkManager;
    }

    public ClientHandler clientHandlerByID(int clientID) {

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientID == clientHandler.getClientID()) {
                return clientHandler;
            }
        }

        return null;
    }

    public void sendStateOfGame(ClientHandler clientHandler) {

        for (String event : stateOfGame) {
            clientHandler.sendMessageToSelf(event);
        }
    }


    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public ArrayList<String> getStateOfGame() {
        return stateOfGame;
    }

    public void setStateOfGame(ArrayList<String> stateOfGame) {
        this.stateOfGame = stateOfGame;
    }

    public ArrayList<Integer> getClientIDs() {
        return clientIDs;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

}
