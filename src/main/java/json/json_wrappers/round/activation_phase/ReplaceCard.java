package json.json_wrappers.round.activation_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class ReplaceCard is for json wrapping the protocol ReplaceCard
 *
 * @author Tim Kriegelsteiner
 */
public class ReplaceCard extends WrapperClass {
    private int register;
    private String newCard;
    private int clientID;

    /**
     * constructor of ReplaceCard
     *
     * @param register : int current register
     * @param newCard  : String new card
     * @param clientID : int ID of client
     */
    public ReplaceCard(int register, String newCard, int clientID) {
        this.register = register;
        this.newCard = newCard;
        this.clientID = clientID;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public String getNewCard() {
        return newCard;
    }

    public void setNewCard(String newCard) {
        this.newCard = newCard;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
