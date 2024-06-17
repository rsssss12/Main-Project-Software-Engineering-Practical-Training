package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class MoveOne implements the card effect MoveOne
 *
 * @author Hannah Spierer
 */
public class MoveOne extends ProgrammingCard {

    public MoveOne() {

    }

    /**
     * The method implements the effect of the MoveOne card
     * The robot moves one step forward
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        effects.move(game,player);
    }

    public String toString() {
        return "MoveI";
    }
}
