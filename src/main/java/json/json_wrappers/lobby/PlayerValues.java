package json.json_wrappers.lobby;

import json.wrapper_utilities.WrapperClass;

/**
 * The class PlayerValues is for json wrapping the protocol PlayerValues
 */
public class PlayerValues extends WrapperClass {
    private String name;
    private int figure;

    /**
     * constructor of PlayerValues
     *
     * @param name   : String username
     * @param figure : int robot
     */
    public PlayerValues(String name, int figure) {
        this.name = name;
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }
}
