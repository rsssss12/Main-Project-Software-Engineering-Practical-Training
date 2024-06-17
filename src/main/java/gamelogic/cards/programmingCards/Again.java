package gamelogic.cards.programmingCards;

import gamelogic.Player;
import gamelogic.cards.Card;
import gamelogic.round.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class Again implements the card effect Again
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public class Again extends ProgrammingCard {

    private final Logger logger = LogManager.getLogger(Again.class.getName());

    public Again() {

    }

    /**
     * The method implements the effect of the Again card
     * if it is the first register, Again is not allowed
     * if all registers before have an Again card, effect is nullified
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        int currentRegister = player.getPlayerMat().getCurrentRegister();

        if (currentRegister == 0) {
            logger.debug("Warning: Card Again cannot be played in first round");
            return;
        }

        for (int i = 1; i < currentRegister + 1; i++) {
            Card cardAtRecentRegister = player.getPlayerMat().getRegisterAtPosition(currentRegister - i);
            if (!(cardAtRecentRegister instanceof Again)) {

                cardAtRecentRegister.effect(game, player);
                return;
            }
        }

    }



    public String toString() {
        return "Again";
    }
}
