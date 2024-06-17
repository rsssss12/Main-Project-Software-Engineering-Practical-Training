package json.json_wrappers.lobby;

import json.wrapper_utilities.WrapperClass;

/**
 * The class SetStatus is for json wrapping the protocol SetStatus
 */
public class SetStatus extends WrapperClass {
    private boolean ready;

    /**
     * constructor of SetStatus
     *
     * @param ready : boolean if player ready
     */
    public SetStatus(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}