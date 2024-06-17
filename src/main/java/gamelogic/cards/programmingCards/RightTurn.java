package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class RightTurn implements the card effect RightTurn
 *
 * @author Hannah Spierer
 */
public class RightTurn extends ProgrammingCard {

    public RightTurn() {

    }

    /**
     * The method implements the effect of the RightTurn card
     * The robot rotates 90° clockwise
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        effects.rotate(player, "clockwise");
    }

    public String toString() {
        return "TurnRight";
    }

}
