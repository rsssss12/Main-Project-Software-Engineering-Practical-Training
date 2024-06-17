package json.json_wrappers.round.programming_phase;

import json.wrapper_utilities.WrapperClass;

import java.util.ArrayList;

/**
 * The class TimerEnded is for json wrapping the protocol TimerEnded
 *
 * @author Tim Kriegelsteiner
 */
public class TimerEnded extends WrapperClass {

    private ArrayList<Integer> ClientIDs;

    /**
     * constructor of TimerEnded
     *
     * @param clientIDs : ArrayList<Integer> of clientIDs
     */
    public TimerEnded(ArrayList<Integer> clientIDs) {
        ClientIDs = clientIDs;
    }

    public ArrayList<Integer> getClientIDs() {
        return ClientIDs;
    }

    public void setClientIDs(ArrayList<Integer> clientIDs) {
        ClientIDs = clientIDs;
    }
}
