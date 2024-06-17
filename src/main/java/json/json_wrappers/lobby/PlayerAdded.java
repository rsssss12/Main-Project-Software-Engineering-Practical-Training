package json.json_wrappers.lobby;

import json.wrapper_utilities.WrapperClass;

/**
 * The class PlayerAdded is for json wrapping the protocol PlayerAdded
 */
public class PlayerAdded extends WrapperClass {
    private int clientID;
    private String name;
    private int figure;

    /**
     * constructor of PlayerAdded
     *
     * @param clientID : int ID of client
     * @param name     : String username
     * @param figure   : int robot
     */
    public PlayerAdded(int clientID, String name, int figure) {
        this.clientID = clientID;
        this.name = name;
        this.figure = figure;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }
}



