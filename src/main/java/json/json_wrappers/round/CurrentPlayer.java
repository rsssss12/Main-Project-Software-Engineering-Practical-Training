package json.json_wrappers.round;

import json.wrapper_utilities.WrapperClass;

/**
 * The class CurrentPlayer is for json wrapping the protocol CurrentPlayer
 */
public class CurrentPlayer extends WrapperClass {

    private int clientID;

    /**
     * constructor of CurrentPlayer
     *
     * @param clientID : int ID of current client
     */
    public CurrentPlayer(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
