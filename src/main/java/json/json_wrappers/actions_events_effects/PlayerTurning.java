package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class PlayerTurning is for json wrapping the protocol PlayerTurning
 *
 * @author Tim Kriegelsteiner
 */
public class PlayerTurning extends WrapperClass {
    private int clientID;
    private String rotation;

    /**
     * constructor of PlayerTurning
     *
     * @param clientID : int ID of client
     * @param rotation : String direction of rotation
     */
    public PlayerTurning(int clientID, String rotation) {
        this.clientID = clientID;
        this.rotation = rotation;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }
}
