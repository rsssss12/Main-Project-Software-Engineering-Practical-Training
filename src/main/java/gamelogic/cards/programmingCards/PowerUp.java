package gamelogic.cards.programmingCards;

import gamelogic.Board;
import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The class PowerUp implements the card effect PowerUp
 *
 * @author Hannah Spierer
 */
public class PowerUp extends ProgrammingCard {

    public PowerUp() {
    }

    /**
     * The method implements the effect of the PowerUp card
     * The player gets one energy cube from the energy bank
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {
        Board board = game.getBoard();
        board.setEnergyBank(board.getEnergyBank() - 1);
        player.getPlayerMat().setEnergyReserve(player.getPlayerMat().getEnergyReserve() + 1);
        effects.sendEnergy(player, "PowerUp", 1);
    }

    public String toString() {
        return "PowerUp";
    }
}
