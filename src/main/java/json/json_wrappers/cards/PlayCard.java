package json.json_wrappers.cards;

import json.wrapper_utilities.WrapperClass;

/**
 * The class PlayCard is for json wrapping the protocol PlayCard
 *
 * @author Tim Kriegelsteiner
 */
public class PlayCard extends WrapperClass {

    private String card;

    /**
     * constructor of PlayCard
     *
     * @param card : String of card
     */
    public PlayCard(String card) {
        this.card = card;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
