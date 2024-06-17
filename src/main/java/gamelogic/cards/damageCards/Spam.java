package gamelogic.cards.damageCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class Spam implements the damage card effect Spam
 *
 * @author Hannah Spierer
 */
public class Spam extends DamageCard {

    public Spam() {
    }

    /**
     * The method implements the effect of the Spam damage card
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        effectOfDamageCards(game, player, player.getPlayerMat().getCurrentRegister());
    }

    public String toString() {
        return "Spam";
    }
}
