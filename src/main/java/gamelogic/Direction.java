package gamelogic;

/**
 * The enumeration Direction contains the four possible directions
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public enum Direction {

    TOP,
    BOTTOM,
    LEFT,
    RIGHT;


    /**
     * The method returns the opposite of a specific direction
     *
     * @return Direction : new direction
     */
    public Direction getOpposite() {
        switch (this) {
            case TOP -> {
                return BOTTOM;
            }
            case BOTTOM -> {
                return TOP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
        }
        return null;
    }

    /**
     * The method returns the direction after 90° clockwise rotation
     *
     * @return Direction : new direction
     */
    public Direction getClockwise() {
        switch (this) {
            case TOP -> {
                return RIGHT;
            }
            case BOTTOM -> {
                return LEFT;
            }
            case LEFT -> {
                return TOP;
            }
            case RIGHT -> {
                return BOTTOM;
            }
        }
        return null;
    }

    /**
     * The method returns the direction after 90° counterclockwise rotation
     *
     * @return Direction : new direction
     */
    public Direction getCounterClockwise() {
        switch (this) {
            case TOP -> {
                return LEFT;
            }
            case BOTTOM -> {
                return RIGHT;
            }
            case LEFT -> {
                return BOTTOM;
            }
            case RIGHT -> {
                return TOP;
            }
        }
        return null;
    }

    /**
     * The method checks if a direction is clockwise
     *
     * @param direction : Direction to check
     * @return boolean : true, if clockwise
     */
    public boolean isClockwise(Direction direction) {

        assert this.getClockwise() != null;
        return this.getClockwise().equals(direction);
    }

    /**
     * The method checks if a direction is counterclockwise
     *
     * @param direction : Direction to check
     * @return boolean : true, if counterclockwise
     */
    public boolean isCounterClockwise(Direction direction) {

        assert this.getCounterClockwise() != null;
        return this.getCounterClockwise().equals(direction);
    }

    /**
     * The method checks if a direction is opposite
     *
     * @param direction : Direction to check
     * @return boolean : true, if opposite
     */
    public boolean isOpposite(Direction direction) {

        assert this.getOpposite() != null;
        return this.getOpposite().equals(direction);
    }

    /**
     * The method checks from two directions if there is any clockwise
     *
     * @param direction1 : Direction 1
     * @param direction2 : Direction 2
     * @return boolean : true if there is clockwise part
     */
    public boolean hasClockwisePart(Direction direction1, Direction direction2) {

        return (this.isClockwise(direction1) || this.isClockwise(direction2));
    }

    /**
     * The method checks from two directions if there is any counterclockwise
     *
     * @param direction1 : Direction 1
     * @param direction2 : Direction 2
     * @return boolean : true if there is counterclockwise part
     */
    public boolean hasCounterClockwisePart(Direction direction1, Direction direction2) {

        return (this.isCounterClockwise(direction1) || this.isCounterClockwise(direction2));
    }

    /**
     * The method checks from two directions if there is any opposite
     *
     * @param direction1 : Direction 1
     * @param direction2 : Direction 2
     * @return boolean : true if there is opposite part
     */
    public boolean hasOppositePart(Direction direction1, Direction direction2) {

        return (this.isOpposite(direction1) || this.isOpposite(direction2));
    }


}


