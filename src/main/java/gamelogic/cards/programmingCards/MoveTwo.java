package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class MoveTwo implements the card effect MoveTwo
 *
 * @author Hannah Spierer
 */
public class MoveTwo extends ProgrammingCard {

    public MoveTwo() {

    }

    /**
     * The method implements the effect of the MoveTwo card
     * It calls moveOne two times
     * The robot moves 2 steps forward
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        for (int i = 0; i < 2; i++) {
            if(!player.isCurrentlyRebooted()) {
                effects.move(game, player);
            }
        }
    }

    public String toString() {
        return "MoveII";
    }


}
