package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class PickDamage is for json wrapping the protocol PickDamage
 *
 * @author Hannah Spierer
 */
public class PickDamage extends WrapperClass {

    private int count;
    private ArrayList<String> availablePiles;

    /**
     * constructor of PickDamage
     *
     * @param count          : int number of damages
     * @param availablePiles : ArrayList<String> piles available
     */
    public PickDamage(int count, ArrayList<String> availablePiles) {
        this.count = count;
        this.availablePiles = availablePiles;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getAvailablePiles() {
        return availablePiles;
    }

    public void setAvailablePiles(ArrayList<String> availablePiles) {
        this.availablePiles = availablePiles;
    }
}




