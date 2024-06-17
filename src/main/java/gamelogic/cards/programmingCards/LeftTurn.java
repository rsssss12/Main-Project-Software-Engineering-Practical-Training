package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class LeftTurn implements the card effect LeftTurn
 *
 * @author Hannah Spierer
 */
public class LeftTurn extends ProgrammingCard {

    public LeftTurn() {

    }

    /**
     * The method implements the effect of the LeftTurn card
     * The robot rotates 90° counterclockwise
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        effects.rotate(player,"counterclockwise");
    }

    public String toString() {
        return "TurnLeft";
    }

}
