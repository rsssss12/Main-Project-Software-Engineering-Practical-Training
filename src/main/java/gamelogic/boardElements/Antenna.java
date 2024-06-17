package gamelogic.boardElements;

import java.util.ArrayList;

/**
 * The class Antenna represents the board element antenna
 *
 * @author Tim Kriegelsteiner
 */
public class Antenna extends BoardElement {

    private ArrayList<String> orientations;

    /**
     * constructor of Antenna
     *
     * @param isOnBoard    : String board
     * @param orientations : ArrayList<String> orientations
     */
    public Antenna(String isOnBoard, ArrayList<String> orientations) {
        this.type = "Antenna";
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

    public ArrayList<String> getOrientations() {
        return orientations;
    }

    public void setOrientations(ArrayList<String> orientations) {
        this.orientations = orientations;
    }
}
