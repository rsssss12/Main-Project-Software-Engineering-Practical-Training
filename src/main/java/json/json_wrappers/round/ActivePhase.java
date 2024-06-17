package json.json_wrappers.round;

import json.wrapper_utilities.WrapperClass;

/**
 * The class ActivePhase is for json wrapping the protocol ActivePhase
 */
public class ActivePhase extends WrapperClass {

    private int phase;

    /**
     * constructor of ActivePhase
     *
     * @param phase : int number of phase
     */
    public ActivePhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
