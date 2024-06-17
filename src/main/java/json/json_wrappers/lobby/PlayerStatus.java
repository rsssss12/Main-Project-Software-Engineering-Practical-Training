package json.json_wrappers.lobby;

import json.wrapper_utilities.WrapperClass;

/**
 * The class PlayerStatus is for json wrapping the protocol PlayerStatus
 */
public class PlayerStatus extends WrapperClass {
    private int clientID;
    private boolean ready;

    /**
     * constructor of PlayerStatus
     *
     * @param clientID : int ID of client
     * @param ready    : boolean if ready to play
     */
    public PlayerStatus(int clientID, boolean ready) {
        this.clientID = clientID;
        this.ready = ready;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}