package gamelogic.boardElements;


/**
 * The abstract class BoardElement is for all board elements
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public abstract class BoardElement {

    protected String type;

    protected String isOnBoard;

    private transient int x;

    private transient int y;

    public BoardElement() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsOnBoard() {
        return isOnBoard;
    }

    public void setIsOnBoard(String isOnBoard) {
        this.isOnBoard = isOnBoard;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
