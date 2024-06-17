package json.json_wrappers.round.activation_phase;

import json.wrapper_utilities.WrapperClass;

import java.util.HashMap;

/**
 * The class CurrentCards is for json wrapping the protocol CurrentCards
 *
 * @author Tim Kriegelsteiner
 */
public class CurrentCards extends WrapperClass {
    private HashMap<Integer, String> activeCards;

    /**
     * constructor of CurrentCards
     *
     * @param activeCards : HashMap<Integer, String> cards in registers
     */
    public CurrentCards(HashMap<Integer, String> activeCards) {
        this.activeCards = activeCards;
    }

    public HashMap<Integer, String> getActiveCards() {
        return activeCards;
    }

    public void setActiveCards(HashMap<Integer, String> activeCards) {
        this.activeCards = activeCards;
    }
}


