package json.adapters;

import json.wrapper_utilities.WrapperClass;

import java.util.List;

public class GameStartedArray extends WrapperClass {

    private List<Object>[][] gameMap;

    public GameStartedArray(List<Object>[][] gameMap) {
        this.gameMap = gameMap;
    }

    public void setGameMap(List<Object>[][] gameMap) {
        this.gameMap = gameMap;
    }

    public List<Object>[][] getGameMap() {
        return gameMap;
    }
}
