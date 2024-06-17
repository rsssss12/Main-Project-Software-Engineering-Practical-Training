package gamelogic.round;

import gamelogic.Deck;
import gamelogic.Player;
import gamelogic.cards.Card;
import json.json_wrappers.round.programming_phase.SelectedCard;
import json.wrapper_utilities.CardWrapping;
import json.wrapper_utilities.WrapperClassSerialization;
import network.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Timer;

/**
 * The class ProgrammingPhase contains the logic for the programming phase
 *
 * @author Tim Kriegelsteiner
 */
public class ProgrammingPhase extends Phase {
    private final Logger logger = LogManager.getLogger(ProgrammingPhase.class.getName());
    private boolean isTimerRunning = false;
    private Timer timer = new Timer();

    /**
     * constructor of programmingPhase
     *
     * @param game : current Game
     */
    public ProgrammingPhase(Game game) {
        super(game);
        super.ID = 2;
    }

    /**
     * The method handles the protocol SelectCard and sends CardSelected protocol
     *
     * @param selectedCard  : SelectedCard protocol
     * @param clientHandler : ClientHandler
     */
    public void handleSelectCard(SelectedCard selectedCard, ClientHandler clientHandler) {
        String card = selectedCard.getCard();
        int register = selectedCard.getRegister();

        Player player = Player.getPlayerByClientID(game.getPlayers(), clientHandler.getClientID());

        assert player != null;
        handleRegisterCondition(card, player.getPlayerMat().getRegister(), player.getCardsInHand(), register);
        player.getPlayerMat().fillRegister(CardWrapping.selectCard(card), register);

        String json = WrapperClassSerialization.serializeCardSelected(player, card, register);
        clientHandler.sendMessageToAllClients(json);

        if (player.getPlayerMat().isProgrammingComplete() && !isTimerRunning) {
            sendSelectionFinished(player);
            startTimer(clientHandler, timer);
        } else if (player.getPlayerMat().isProgrammingComplete() && isTimerRunning) {
            sendSelectionFinished(player);
        }

        logger.debug("isAllProgrammingComplete(game.getPlayers()) = " + isAllProgrammingComplete(game.getPlayers()));

        if (isTimerRunning && isAllProgrammingComplete(game.getPlayers())) {
            timer.cancel();
            endTimer(clientHandler);
        }
    }


    public void handleRegisterCondition(String card, Card[] register, Deck<Card> deck, int registerNumber) {

        if (card == null || register[registerNumber - 1] != null) {
            deck.add(register[registerNumber - 1]);
        } else {

            for (Card card1 : deck) {
                if (card1.toString().equals(card)) {
                    deck.remove(card1);
                    break;
                }
            }
        }
    }

    /**
     * The method starts the 30 seconds timer and sends the protocol TimerStarted
     *
     * @param clientHandler : ClientHandler
     * @param timer         : Timer
     */
    public void startTimer(ClientHandler clientHandler, Timer timer) {

        String json = WrapperClassSerialization.serializeTimerStarted();
        clientHandler.sendMessageToAllClients(json);

        timer.scheduleAtFixedRate(new TimerServer(clientHandler, timer, this), 30000, 1);
        isTimerRunning = true;
    }

    /**
     * The method ends the timer and send the protocol TimerEnded
     *
     * @param clientHandler : ClientHandler
     */
    public void endTimer(ClientHandler clientHandler) {

        ArrayList<Player> unfinishedPlayers = getUnfinishedPlayers();

        String json = WrapperClassSerialization.serializeTimerEnded(unfinishedPlayers);
        clientHandler.sendMessageToAllClients(json);

        Game.delayingTime(3000);

        for (Player unfinishedPlayer : unfinishedPlayers) {
            unfinishedPlayer.fillRemainingRegisters(clientHandler);
        }

        cleanUpProgrammingPhase();

        game.startNewPhase(clientHandler);
    }

    /**
     * The method draws the nine programming cards
     *
     * @param game          : current Game
     * @param clientHandler : ClientHandler
     */
    public void drawProgrammingCards(Game game, ClientHandler clientHandler) {
        //draw cards till empty
        Deck<Card> remainingCards = new Deck<>();

        for (Player player : game.getPlayers()) {
            player.drawFromProgrammingDeck(clientHandler, 9);
        }
    }

    /**
     * The method returns list of players who didn't finish programming
     *
     * @return ArrayList<Player> : list of unfinished players
     */
    public ArrayList<Player> getUnfinishedPlayers() {
        ArrayList<Player> unfinishedPlayers = new ArrayList<>();
        for (Player player : game.getPlayers()) {

            if (!player.getPlayerMat().isProgrammingComplete()) {
                player.discardHand();
                unfinishedPlayers.add(player);
            }

        }
        return unfinishedPlayers;
    }

    /**
     * The method determines if all players completed their programming
     *
     * @param players : ArrayList<Player> all players
     * @return boolean : true if all complete
     */
    public boolean isAllProgrammingComplete(ArrayList<Player> players) {
        for (Player player : players) {

            if (!player.getPlayerMat().isProgrammingComplete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method sends the protocol SelectionFInished
     *
     * @param player : Player who is finished
     */
    public void sendSelectionFinished(Player player) {
        String json = WrapperClassSerialization.serializeSelectionFinished(player);
        player.getClientHandler().sendMessageToAllClients(json);
    }

    /**
     * The method resets the timer
     */
    public void cleanUpProgrammingPhase() {
        timer = new Timer();
        isTimerRunning = false;

    }

    /**
     * The method starts the programming phase
     *
     * @param clientHandler : ClientHandler
     */
    @Override
    public void start(ClientHandler clientHandler) {
        drawProgrammingCards(game, clientHandler);
    }

}
