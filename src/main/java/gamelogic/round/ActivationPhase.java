package gamelogic.round;

import gamelogic.Effects;
import gamelogic.Player;
import gamelogic.cards.Card;
import json.wrapper_utilities.WrapperClassSerialization;
import network.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The class ActivationPhase contains the logic for the last phase
 * @author Tim Kriegelsteiner
 */
public class ActivationPhase extends Phase {

    private final Logger logger = LogManager.getLogger(ActivationPhase.class.getName());
    private final Effects effects = new Effects();

    /**
     * constructor ActivationPhase
     *
     * @param game: current game
     */
    public ActivationPhase(Game game) {
        super(game);
        super.ID = 3;
    }

    /**
     * The method activates one register of one player
     *
     * @param player:   current player
     * @param register: current register
     */
    public void activateRegister(Player player, int register) {
        Card card = player.getPlayerMat().getRegister()[register];

        logger.debug("player + card + register = " + player.getUsername() + " + " + card + " + " + register);

        card.effect(game, player);

    }

    /**
     * The method activates the registers of the players in right order with the effects
     *
     * @param clientHandler: clientHandler
     */
    public void activateRegisters(ClientHandler clientHandler) {

        for (int registerNumber = 0; registerNumber < 5; registerNumber++) {
//            sendCurrentCards(game.getPlayerOrder(),registerNumber);
            for (Player player : game.getPlayerOrder()) {
                player.getPlayerMat().setCurrentRegister(registerNumber);

                if (!player.isCurrentlyRebooted()) {
                    activateRegister(player, registerNumber);
                }
            }

            effects.activateBoardElements(game);
            if (game.isGameOver()) {
                break;
            }
            game.determinePriority();

        }
        cleanUpActivationPhase();
        game.startNewPhase(clientHandler);

    }

    /**
     * Sends the protocol message "CurrentCards".
     */
    public void sendCurrentCards(ArrayList<Player> players, int registerNumber) {
        String json = WrapperClassSerialization.serializeCurrentCards(players, registerNumber);
        players.get(0).getClientHandler().sendMessageToAllClients(json);
        Game.delayingTime(3000);
    }

    /**
     * The method resets the registers
     */
    public void cleanUpActivationPhase() {
        for (Player player : game.getPlayers()) {
            player.getPlayerMat().cleanRegisters();
            player.discardHand();
            player.setCurrentlyRebooted(false);
        }
    }

    /**
     * The method starts the phase
     *
     * @param clientHandler: clientHandler
     */
    @Override
    public void start(ClientHandler clientHandler) {

        activateRegisters(clientHandler);
    }

    public Effects getEffects() {
        return effects;
    }
}
