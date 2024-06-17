
package gamelogic.boardElements;

/**
 * The class Empty represents the board element empty
 *
 * @author Tim Kriegelsteiner
 */
public class Empty extends BoardElement {


    /**
     * constructor of empty
     *
     * @param isOnBoard : String board
     */
    public Empty(String isOnBoard) {
        this.type = "Empty";
        this.isOnBoard = isOnBoard;
    }

}
