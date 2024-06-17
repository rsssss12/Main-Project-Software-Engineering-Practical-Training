package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class NotYourCards is for json wrapping the protocol NotYourCards
 *
 * @author Tim Kriegelsteiner
 */
public class NotYourCards extends WrapperClass {
    private int clientID;
    private int cardsInHand;

    /**
     * constructor of NotYourCards
     *
     * @param clientID    : int ID of client
     * @param cardsInHand : int amount of cards
     */
    public NotYourCards(int clientID, int cardsInHand) {
        this.clientID = clientID;
        this.cardsInHand = cardsInHand;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(int cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
}
