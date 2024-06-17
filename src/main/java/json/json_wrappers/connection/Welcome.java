package json.json_wrappers.connection;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Welcome is for json wrapping the protocol Welcome
 */
public class Welcome extends WrapperClass {

    private int clientID;

    /**
     * constructor of Welcome
     *
     * @param clientID : ID of client
     */
    public Welcome(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

}
