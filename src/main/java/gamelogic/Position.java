package gamelogic;

/**
 * The class Position represents a position with x and y coordinate
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public class Position {

    private int x;

    private int y;

    /**
     * constructor
     *
     * @param x : int y coordinate
     * @param y : int y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * The method overrides the equals method
     *
     * @param object : Object to compare with
     * @return boolean : true if the position is equal
     */
    @Override
    public boolean equals(Object object) {
        Position position = (Position) object;
        boolean xEquality = this.getX() == position.getX();
        boolean yEquality = this.getY() == position.getY();

        return xEquality && yEquality;
    }

}
