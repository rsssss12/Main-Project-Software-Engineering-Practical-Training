package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class UTurn implements the card effect UTurn
 *
 * @author Hannah Spierer
 */
public class UTurn extends ProgrammingCard {

    public UTurn() {

    }

    /**
     * The method implements the effect of the UTurn card
     * The robot rotates 180°
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {

        for (int i = 0; i < 2; i++) {
            effects.rotate(player, "clockwise");
        }
    }

    public String toString() {
        return "UTurn";
    }
}
