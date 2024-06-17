package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class SelectedDamage is for json wrapping the protocol SelectedDamage
 *
 * @author Hannah Spierer
 */
public class SelectedDamage extends WrapperClass {

    private ArrayList<String> cards;

    /**
     * constructor of SelectedDamage
     *
     * @param cards : ArrayList<String> cards selected
     */
    public SelectedDamage(ArrayList<String> cards) {
        this.cards = cards;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }

}