package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class RebootDirection is for json wrapping the protocol RebootDirection
 *
 * @author Tim Kriegelsteiner
 */
public class RebootDirection extends WrapperClass {
    String direction;

    /**
     * constructor of RebootDirection
     *
     * @param direction : String of direction
     */
    public RebootDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
