package json.json_wrappers.lobby;

import com.google.gson.JsonElement;
import json.wrapper_utilities.WrapperClass;

import java.util.List;

/**
 * The class GameStarted is for json wrapping the protocol GameStarted
 *
 * @author Tim Kriegelsteiner
 */
public class GameStarted extends WrapperClass {

    private List<List<List<JsonElement>>> gameMap;

    /**
     * constructor of GameStarted
     *
     * @param gameMap : List<List<List<JsonElement>>> map
     */
    public GameStarted(List<List<List<JsonElement>>> gameMap) {
        this.gameMap = gameMap;
    }

    public List<List<List<JsonElement>>> getGameMap() {
        return gameMap;
    }

    public void setGameMap(List<List<List<JsonElement>>> gameMap) {
        this.gameMap = gameMap;
    }


}
