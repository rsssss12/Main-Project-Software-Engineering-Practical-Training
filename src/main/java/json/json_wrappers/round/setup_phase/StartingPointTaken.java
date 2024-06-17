package json.json_wrappers.round.setup_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class StartingPointTaken is for json wrapping the protocol StartingPointTaken
 */
public class StartingPointTaken extends WrapperClass {
    private int x;
    private int y;
    private int clientID;

    /**
     * constructor of StartingPointTaken
     *
     * @param x        : x coordinate
     * @param y        : y coordinate
     * @param clientID : int ID of client
     */
    public StartingPointTaken(int x, int y, int clientID) {
        this.x = x;
        this.y = y;
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

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
