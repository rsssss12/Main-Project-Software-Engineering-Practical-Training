package json.json_wrappers.actions_events_effects;

import json.wrapper_utilities.WrapperClass;

/**
 * The class Animation is for json wrapping the protocol Animation
 *
 * @author Tim Kriegelsteiner
 */
public class Animation extends WrapperClass {

    private String type;

    /**
     * constructor for Animation
     *
     * @param type : String type of animation
     */
    public Animation(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
