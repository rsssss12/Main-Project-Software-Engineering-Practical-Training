package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class ShuffleCoding is for json wrapping the protocol ShuffleCoding
 */
public class ShuffleCoding extends WrapperClass {
    private int clientID;

    /**
     * constructor of ShuffleCoding
     *
     * @param clientID : int ID of client
     */
    public ShuffleCoding(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
