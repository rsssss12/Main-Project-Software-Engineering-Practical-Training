package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class CardSelected is for json wrapping the protocol CardSelected
 *
 * @author Tim Kriegelsteiner
 */
public class CardSelected extends WrapperClass {
    private int clientID;
    private int register;
    private boolean filled;

    /**
     * constructor of CardSelected
     *
     * @param clientID : int ID of client
     * @param register : int current register
     * @param filled   : boolean if filled
     */
    public CardSelected(int clientID, int register, boolean filled) {
        this.clientID = clientID;
        this.register = register;
        this.filled = filled;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
