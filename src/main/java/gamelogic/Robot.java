package gamelogic;

import static gamelogic.Direction.RIGHT;

/**
 * The class Robot represents a robot in the game
 *
 * @author Hannah Spierer
 */
public class Robot {

    private Position position = new Position();

    private Position nextPosition;

    private Direction direction = RIGHT;

    private Direction movingDirection = direction;

    private Board board = new Board();


    public Robot() {

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


}
