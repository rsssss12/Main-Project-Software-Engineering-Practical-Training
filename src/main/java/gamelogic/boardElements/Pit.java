package gamelogic.boardElements;

/**
 * The class Pit represents the board element pit
 *
 * @author Tim Kriegelsteiner
 */
public class Pit extends BoardElement {

    /**
     * constructor of pit
     *
     * @param isOnBoard : String board
     */
    public Pit(String isOnBoard) {
        this.type = "Pit";
        this.isOnBoard = isOnBoard;
    }

}