package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class CardsYouGotNow is for json wrapping the protocol CardsYouGotNow
 *
 * @author Tim Kriegelsteiner
 */
public class CardsYouGotNow extends WrapperClass {
    private ArrayList<String> cards;

    /**
     * constructor of CardsYouGotNow
     *
     * @param cards : ArrayList<String> cards
     */
    public CardsYouGotNow(ArrayList<String> cards) {
        this.cards = cards;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }
}
