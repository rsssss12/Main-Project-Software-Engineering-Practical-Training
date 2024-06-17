package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class CheckPointReached is for json wrapping the protocol CheckPointReached
 *
 * @author Tim Kriegelsteiner
 */
public class CheckPointReached extends WrapperClass {
    private int clientID;
    private int number;

    /**
     * constructor for CheckPointReached
     *
     * @param clientID : int ID of client
     * @param number   : int number of checkpoint
     */
    public CheckPointReached(int clientID, int number) {
        this.clientID = clientID;
        this.number = number;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
