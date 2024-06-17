package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class BackUp implements the card effect BackUp
 *
 * @author Hannah Spierer
 */
public class BackUp extends ProgrammingCard {

    public BackUp() {

    }

    /**
     * The method implements the effect of the BackUp card
     * The robot moves one step backwards
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        effects.backUp(game, player);
    }

    public String toString() {
        return "BackUp";
    }
}
