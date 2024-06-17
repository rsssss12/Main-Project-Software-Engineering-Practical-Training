package gamelogic.cards.damageCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class Worm implements the damage card effect Worm
 *
 * @author Hannah Spierer
 */
public class Worm extends DamageCard {

    public Worm() {
    }

    /**
     * The method implements the effect of the Worm damage card
     * The robot has to reboot
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        player.rebooting(game);
    }

    public String toString() {
        return "Worm";
    }
}
