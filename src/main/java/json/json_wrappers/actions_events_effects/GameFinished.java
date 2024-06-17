package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class GameFinished is for json wrapping the protocol GameFinished
 *
 * @author Tim Kriegelsteiner
 */
public class GameFinished extends WrapperClass {
    private int clientID;

    /**
     * constructor of GameFinished
     *
     * @param clientID : int ID of client
     */
    public GameFinished(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
