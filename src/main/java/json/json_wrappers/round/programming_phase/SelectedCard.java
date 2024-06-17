package json.json_wrappers.round.programming_phase;


import json.wrapper_utilities.WrapperClass;

/**
 * The class SelectedCard is for json wrapping the protocol SelectedCard
 *
 * @author Tim Kriegelsteiner
 */
public class SelectedCard extends WrapperClass {
    private String card;
    private int register;

    /**
     * constructor of SelectedCard
     *
     * @param card     : String of card
     * @param register : int number of register
     */
    public SelectedCard(String card, int register) {
        this.card = card;
        this.register = register;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }
}
