package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class YourCards is for json wrapping the protocol YourCards
 */
public class YourCards extends WrapperClass {

    private ArrayList<String> cardsInHand;

    /**
     * constructor of YourCards
     *
     * @param cardsInHand : ArrayList<String> of cards in hand
     */
    public YourCards(ArrayList<String> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public ArrayList<String> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<String> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }
}
