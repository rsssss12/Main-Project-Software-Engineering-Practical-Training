package gamelogic.boardElements;


/**
 * The class CheckPoint represents the board element checkpoint
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class CheckPoint extends BoardElement {

    private int count;

    /**
     * constructor of checkpoint
     *
     * @param isOnBoard: String board
     * @param count:     int number
     */
    public CheckPoint(String isOnBoard, int count) {
        this.type = "CheckPoint";
        this.isOnBoard = isOnBoard;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}