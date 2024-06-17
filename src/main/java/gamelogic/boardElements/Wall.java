package gamelogic.boardElements;


import java.util.ArrayList;

/**
 * The class Wall represents the board element wall
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class Wall extends BoardElement {

    private ArrayList<String> orientations;

    /**
     * constructor of wall
     *
     * @param isOnBoard    : String board
     * @param orientations : ArrayList<String> orientations
     */
    public Wall(String isOnBoard, ArrayList<String> orientations) {
        this.type = "Wall";
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