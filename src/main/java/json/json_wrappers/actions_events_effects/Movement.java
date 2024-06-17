package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Movement is for json wrapping the protocol Movement
 */
public class Movement extends WrapperClass {
    private int clientID;
    private int x;
    private int y;

    /**
     * constructor for Movement
     *
     * @param clientID : int ID of client
     * @param x        : x coordinate
     * @param y        : y coordinate
     */
    public Movement(int clientID, int x, int y) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
