package json.json_wrappers.lobby;

import gamelogic.maps.MapEnum;
import json.wrapper_utilities.WrapperClass;

import java.util.List;

/**
 * The class SelectMap is for json wrapping the protocol SelectMap
 *
 * @author Tim Kriegelsteiner
 */
public class SelectMap extends WrapperClass {

    private List<String> availableMaps;

    /**
     * constructor of SelectMap
     * adds all maps from MapEnum to available maps
     */
    public SelectMap() {

        this.availableMaps = MapEnum.getNames();
    }

    public List<String> getAvailableMaps() {
        return availableMaps;
    }

    public void setAvailableMaps(List<String> availableMaps) {
        this.availableMaps = availableMaps;
    }
}
