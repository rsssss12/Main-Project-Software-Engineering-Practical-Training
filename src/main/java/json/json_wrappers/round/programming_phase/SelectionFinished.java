package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class SelectionFinished is for json wrapping the protocol SelectionFinished
 *
 * @author Tim Kriegelsteiner
 */
public class SelectionFinished extends WrapperClass {
    private int clientID;

    /**
     * constructor of SelectionFinished
     *
     * @param clientID : int ID of client
     */
    public SelectionFinished(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
