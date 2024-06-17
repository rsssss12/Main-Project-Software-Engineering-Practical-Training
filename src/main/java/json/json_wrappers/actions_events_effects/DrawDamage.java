package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class DrawDamage is for json wrapping the protocol DrawDamage
 *
 * @author Tim Kriegelsteiner
 */
public class DrawDamage extends WrapperClass {

    private int clientID;
    private ArrayList<String> cards;

    /**
     * constructor of DrawDamage
     *
     * @param clientID : int ID of client
     * @param cards    : ArrayList<String> cards to draw
     */
    public DrawDamage(int clientID, ArrayList<String> cards) {
        this.clientID = clientID;
        this.cards = cards;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }
}
