package gui.mvvm.connection;

import gui.mvvm.lobby.LobbyModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import network.Client;

/**
 * The class ConnectionModel contains the model for the connection view
 */
public class ConnectionModel {

    private Client client;

    private final BooleanProperty isAI = new SimpleBooleanProperty();

    private static volatile ConnectionModel connectionModel;

    public static ConnectionModel getInstance() {
        if (connectionModel == null) {
            synchronized (LobbyModel.class) {
                if (connectionModel == null) {
                    connectionModel = new ConnectionModel();

                }
            }
        }
        return connectionModel;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BooleanProperty isAIProperty() {
        return isAI;
    }

}
