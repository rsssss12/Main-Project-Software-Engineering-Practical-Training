package json.json_wrappers.special_messages;

import json.wrapper_utilities.WrapperClass;

/**
 * The class ConnectionUpdate is for json wrapping the protocol ConnectionUpdate
 */
public class ConnectionUpdate extends WrapperClass {

    private int clientID;
    private boolean isConnected;
    private String action;

    /**
     * constructor of ConnectionUpdate
     *
     * @param clientID    : int ID of client
     * @param isConnected : boolean if connected
     * @param action      : String of action
     */
    public ConnectionUpdate(int clientID, boolean isConnected, String action) {
        this.clientID = clientID;
        this.isConnected = isConnected;
        this.action = action;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
