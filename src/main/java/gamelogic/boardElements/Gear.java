package gamelogic.boardElements;

import java.util.ArrayList;


/**
 * The class Gear represents the board element gear
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class Gear extends BoardElement {

    private ArrayList<String> orientations;

    /**
     * constructor of gear
     *
     * @param isOnBoard:    String board
     * @param orientations: ArrayList<String> orientations
     */
    public Gear(String isOnBoard, ArrayList<String> orientations) {
        this.type = "Gear";
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

    public void setOrientations(ArrayList<String> orientations) {
        this.orientations = orientations;
    }

    public ArrayList<String> getOrientations() {
        return orientations;
    }

}

