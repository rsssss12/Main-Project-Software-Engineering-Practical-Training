package gamelogic.cards.damageCards;

import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class TrojanHorse implements the damage card effect Trojan
 *
 * @author Hannah Spierer
 */
public class TrojanHorse extends DamageCard {

    public TrojanHorse() {
    }

    /**
     * The method implements the effect of the Trojan damage card
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        game.getBoard().drawDamageCards(player, 2);
        effectOfDamageCards(game, player, player.getPlayerMat().getCurrentRegister());
    }

    public String toString() {
        return "Trojan";
    }
}
