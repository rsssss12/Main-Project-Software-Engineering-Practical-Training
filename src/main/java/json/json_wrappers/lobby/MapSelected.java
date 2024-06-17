package json.json_wrappers.lobby;

import json.wrapper_utilities.WrapperClass;

/**
 * The class MapSelected is for json wrapping the protocol MapSelected
 *
 * @author Tim Kriegelsteiner
 */
public class MapSelected extends WrapperClass {

    private String map;

    /**
     * constructor of MapSelected
     *
     * @param map : String name of map
     */
    public MapSelected(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
