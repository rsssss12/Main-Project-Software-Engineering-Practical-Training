package gamelogic.boardElements;


/**
 * The class StartPoint represents the board element startPoint
 *
 * @author Tim Kriegelsteiner
 */
public class StartPoint extends BoardElement {

    /**
     * constructor of StartPoint
     *
     * @param isOnBoard : String board
     */
    public StartPoint(String isOnBoard) {
        this.type = "StartPoint";
        this.isOnBoard = isOnBoard;

    }
}