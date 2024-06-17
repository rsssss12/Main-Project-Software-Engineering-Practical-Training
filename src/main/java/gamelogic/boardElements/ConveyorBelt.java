package gamelogic.boardElements;


import java.util.ArrayList;

/**
 * The class ConveyorBelt represents the board element conveyorBelt
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class ConveyorBelt extends BoardElement {

    private int speed;
    private ArrayList<String> orientations;


    /**
     * constructor of conveyorBelt
     *
     * @param isOnBoard:    String board
     * @param orientations: ArrayList<String> orientations
     * @param speed:        int distinction of green and blue conveyor belt
     */
    public ConveyorBelt(String isOnBoard, ArrayList<String> orientations, int speed) {
        this.type = "ConveyorBelt";
        this.isOnBoard = isOnBoard;
        this.speed = speed;
        this.orientations = orientations;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<String> getOrientations() {
        return orientations;
    }

    public void setOrientations(ArrayList<String> orientations) {
        this.orientations = orientations;
    }
}





