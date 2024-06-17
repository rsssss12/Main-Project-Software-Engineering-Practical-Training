package json.json_wrappers.round.setup_phase;

import json.wrapper_utilities.WrapperClass;

/**
 * The class SetStartingPoint is for json wrapping the protocol SetStartingPoint
 *
 * @author Tim Krieglsteiner
 */
public class SetStartingPoint extends WrapperClass {
    private int x;
    private int y;

    /**
     * constructor of SetStartingPoint
     *
     * @param x : x coordinate
     * @param y : y coordinate
     */
    public SetStartingPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
