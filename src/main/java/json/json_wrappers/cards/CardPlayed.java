package json.json_wrappers.cards;

import json.wrapper_utilities.WrapperClass;

/**
 * The class CardPlayed is for json wrapping the protocol CardPlayed
 *
 * @author Tim Kriegelsteiner
 */
public class CardPlayed extends WrapperClass {
    private int clientID;
    private String card;

    /**
     * constructor of CardPlayed
     *
     * @param clientID : int ID of client
     * @param card     : String of card
     */
    public CardPlayed(int clientID, String card) {
        this.clientID = clientID;
        this.card = card;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}

