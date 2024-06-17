package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Energy is for json wrapping the protocol Energy
 *
 * @author Tim Kriegelsteiner
 */
public class Energy extends WrapperClass {
    private int clientID;
    private int count;
    //enum?
    private String source;

    /**
     * constructor of Energy
     *
     * @param clientID : int ID of client
     * @param count    : int number of energy cubes
     * @param source   : String source of energy cubes
     */
    public Energy(int clientID, int count, String source) {
        this.clientID = clientID;
        this.count = count;
        this.source = source;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
