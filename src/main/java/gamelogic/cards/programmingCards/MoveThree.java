package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class MoveThree implements the card effect MoveThree
 *
 * @author Hannah Spierer
 */
public class MoveThree extends ProgrammingCard {

    public MoveThree() {

    }

    /**
     * The method implements the effect of the MoveThree card
     * It calls moveOne three times
     * The robot moves 3 steps forward
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        for (int i = 0; i < 3; i++) {
            if(!player.isCurrentlyRebooted()) {
                effects.move(game, player);
            }
        }
    }

    public String toString() {
        return "MoveIII";
    }

}
