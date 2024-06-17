package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Reboot is for json wrapping the protocol Reboot
 *
 * @author Tim Kriegelsteiner
 */
public class Reboot extends WrapperClass {
    private int clientID;

    /**
     * constructor of Reboot
     *
     * @param clientID : int ID of client
     */
    public Reboot(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
