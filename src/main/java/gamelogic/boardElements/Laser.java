package gamelogic.boardElements;

import java.util.ArrayList;

/**
 * The class Laser represents the board element laser
 *
 * @author Tim Kriegelsteiner
 */
public class Laser extends BoardElement {
    private ArrayList<String> orientations;
    private int count;

    /**
     * constructor of laser
     *
     * @param isOnboard    : String board
     * @param orientations : ArrayList<String> orientations
     * @param count        : int count
     */
    public Laser(String isOnboard, ArrayList<String> orientations, int count) {
        this.type = "Laser";
        this.isOnBoard = isOnboard;
        this.count = count;
        this.orientations = orientations;
    }

    public ArrayList<String> getOrientations() {
        return orientations;
    }

    public void setOrientations(ArrayList<String> orientations) {
        this.orientations = orientations;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

