package gamelogic.boardElements;


import java.util.ArrayList;

/**
 * The class RestartPoint represents the board element restartPoint
 *
 * @author Tim Kriegelsteiner
 */
public class RestartPoint extends BoardElement {

    private ArrayList<String> orientations;


    /**
     * constructor of RestartPoint
     *
     * @param isOnBoard    : String board
     * @param orientations : ArrayList<String> orientations
     */
    public RestartPoint(String isOnBoard, ArrayList<String> orientations) {
        this.type = "RestartPoint";
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