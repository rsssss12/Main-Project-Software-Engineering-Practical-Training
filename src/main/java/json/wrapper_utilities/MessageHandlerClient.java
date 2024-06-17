package json.wrapper_utilities;

import AI.AIJoinTimer;
import AI.AIStartingPointTimer;
import javafx.application.Platform;
import json.json_wrappers.actions_events_effects.*;
import json.json_wrappers.chat.ReceivedChat;
import json.json_wrappers.connection.Welcome;
import json.json_wrappers.lobby.*;
import json.json_wrappers.round.ActivePhase;
import json.json_wrappers.round.CurrentPlayer;
import json.json_wrappers.round.activation_phase.CurrentCards;
import json.json_wrappers.round.activation_phase.ReplaceCard;
import json.json_wrappers.round.programming_phase.*;
import json.json_wrappers.round.setup_phase.StartingPointTaken;
import json.json_wrappers.special_messages.ConnectionUpdate;
import json.json_wrappers.special_messages.Error;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;

/**
 * The class MessageHandlerClient handles the messages sent to the client
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 * @author Robert Scholz
 */
public class MessageHandlerClient {
    private static Logger logger = LogManager.getLogger(MessageHandlerClient.class.getName());

    /**
     * The method handle the different messages which sends via json protocol
     *
     * @param wrapperClass: WrapperClass
     * @param client:       Client
     */
    public static void handleMessages(WrapperClass wrapperClass, Client client) {
        if (wrapperClass != null) {


            String messageType = wrapperClass.getClass().getSimpleName().toUpperCase();

            switch (MessageTypes.valueOf(messageType)) {

                case ANIMATION -> {
                    assert wrapperClass instanceof Animation;
                    Animation animation = (Animation) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleAnimation(animation));
                }
                case CHECKPOINTREACHED -> {
                    assert wrapperClass instanceof CheckPointReached;
                    CheckPointReached checkPointReached = (CheckPointReached) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleCheckpointReached(checkPointReached));
                }
                case ENERGY -> {
                    assert wrapperClass instanceof Energy;
                    Energy energy = (Energy) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleEnergy(energy));
                }
                case GAMEFINISHED -> {
                    assert wrapperClass instanceof GameFinished;
                    GameFinished gameFinished = (GameFinished) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleGameFinished(gameFinished));
                }
                case MOVEMENT -> {
                    assert wrapperClass instanceof Movement;
                    Movement movement = (Movement) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleMovement(movement));
                }
                case PLAYERTURNING -> {
                    assert wrapperClass instanceof PlayerTurning;
                    PlayerTurning playerTurning = (PlayerTurning) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handlePlayerTurn(playerTurning));
                }
                case REBOOT -> {
                    assert wrapperClass instanceof Reboot;
                    Reboot reboot = (Reboot) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleReboot(reboot));
                }
                case REBOOTDIRECTION -> {
                }
                case DRAWDAMAGE -> {
                    assert wrapperClass instanceof DrawDamage;
                    DrawDamage drawDamage = (DrawDamage) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleDrawDamage(drawDamage));
                }
                case PICKDAMAGE -> {
                }
                case SELECTEDDAMAGE -> {
                    assert wrapperClass instanceof SelectedDamage;
                    SelectedDamage selectedDamage = (SelectedDamage) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleSelectedDamage(selectedDamage));
                }
                case CARDPLAYED -> {
                }
                case PLAYCARD -> {
                }
                case RECEIVEDCHAT -> {
                    assert wrapperClass instanceof ReceivedChat;
                    ReceivedChat receivedChat = (ReceivedChat) wrapperClass;

                    String message = receivedChat.getMessage();

                    Platform.runLater(() -> client.getChatModel().handleReceivedChat(receivedChat));
                    logger.debug(message);

                }
                case ALIVE -> {

                    String json = WrapperClassSerialization.serializeAlive();

                    client.sendMessage(json);
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

                }
                case HELLOCLIENT -> {
                    String json = WrapperClassSerialization.serializeHelloServer(client.getGroup(), client.isAI(), client.getProtocol());
                    logger.debug("Send HelloServer to Server");
                    client.sendMessage(json);

                }
                case WELCOME -> {
                    assert wrapperClass instanceof Welcome;
                    Welcome welcome = (Welcome) wrapperClass;

                    client.setClientID(welcome.getClientID());

                    logger.debug("Got clientID : " + client.getClientID());
                    logger.debug("isAI : " + client.isAI());

                    if (client.isAI()) {
                        client.getAi().setClientID(welcome.getClientID());
                        Timer timer = new Timer();
                        timer.schedule(new AIJoinTimer(client.getAi()), 3000);
                    }
                }
                case GAMESTARTED -> {
                    assert wrapperClass instanceof GameStarted;
                    GameStarted gameStarted = (GameStarted) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleGameStarted(gameStarted));
                    Platform.runLater(() -> client.getLobbyModel().handleGameStarted(gameStarted));
                }
                case MAPSELECTED -> {
                    assert wrapperClass instanceof MapSelected;
                    MapSelected mapSelected = (MapSelected) wrapperClass;

                    Platform.runLater(() -> client.getLobbyModel().handleMapSelected(mapSelected));
                }
                case PLAYERADDED -> {
                    assert wrapperClass instanceof PlayerAdded;
                    PlayerAdded playerAdded = (PlayerAdded) wrapperClass;

                    Platform.runLater(() -> client.getLobbyModel().handlePlayerAdded(playerAdded));

                    if (client.isAI()) {
                        if (playerAdded.getClientID() == client.getAi().getClientID()) {
                            client.getAi().dumbAISignalizesReadiness();
                        }
                    }
                }
                case PLAYERSTATUS -> {
                    assert wrapperClass instanceof PlayerStatus;
                    PlayerStatus playerStatus = (PlayerStatus) wrapperClass;

                    Platform.runLater(() -> client.getLobbyModel().handlePlayerStatus(playerStatus));

                }
                case SELECTMAP -> {
                    assert wrapperClass instanceof SelectMap;
                    SelectMap selectMap = (SelectMap) wrapperClass;

                    Platform.runLater(() -> client.getLobbyModel().handleSelectMap(selectMap));
                }
                case CURRENTCARDS -> {
                    assert wrapperClass instanceof CurrentCards;
                    CurrentCards currentCards = (CurrentCards) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleCurrentCards(currentCards));
                }
                case REPLACECARD -> {
                    assert wrapperClass instanceof ReplaceCard;
                    ReplaceCard replaceCard = (ReplaceCard) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleReplaceCard(replaceCard));
                }
                case CARDSELECTED -> {
                    assert wrapperClass instanceof CardSelected;
                    CardSelected cardSelected = (CardSelected) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleCardSelected(cardSelected));
                }
                case CARDSYOUGOTNOW -> {
                    assert wrapperClass instanceof CardsYouGotNow;
                    CardsYouGotNow cardsYouGotNow = (CardsYouGotNow) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleCardsYouGotNow(cardsYouGotNow));
                }
                case NOTYOURCARDS -> {
                    assert wrapperClass instanceof NotYourCards;
                    NotYourCards notYourCards = (NotYourCards) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleNotYourCards(notYourCards));
                }
                case SELECTIONFINISHED -> {
                    assert wrapperClass instanceof SelectionFinished;
                    SelectionFinished selectionFinished = (SelectionFinished) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleSelectionFinished(selectionFinished));
                }
                case SHUFFLECODING -> {
                    assert wrapperClass instanceof ShuffleCoding;
                    ShuffleCoding shuffleCoding = (ShuffleCoding) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleShuffleCoding(shuffleCoding));
                }
                case TIMERENDED -> {
                    assert wrapperClass instanceof TimerEnded;
                    TimerEnded timerEnded = (TimerEnded) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleTimerEnded(timerEnded));
                }
                case TIMERSTARTED -> {
                    assert wrapperClass instanceof TimerStarted;
                    TimerStarted timerStarted = (TimerStarted) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleTimerStarted(timerStarted));
                }
                case YOURCARDS -> {
                    assert wrapperClass instanceof YourCards;
                    YourCards yourCards = (YourCards) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleYourCards(yourCards));

                    if (client.isAI() && yourCards.getCardsInHand().size() >= 9) {
                        client.getAi().dumbAIPlaysCards(yourCards.getCardsInHand(), client.getClientID());
                    }
                }
                case STARTINGPOINTTAKEN -> {
                    assert wrapperClass instanceof StartingPointTaken;
                    StartingPointTaken startingPointTaken = (StartingPointTaken) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleStartingPointTaken(startingPointTaken));
                }
                case ACTIVEPHASE -> {
                    assert wrapperClass instanceof ActivePhase;
                    ActivePhase activePhase = (ActivePhase) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleActivePhase(activePhase));
                }
                case CURRENTPLAYER -> {
                    assert wrapperClass instanceof CurrentPlayer;
                    CurrentPlayer currentPlayer = (CurrentPlayer) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleCurrentPlayer(currentPlayer));

                    if (client.isAI()) {
                        client.getAi().setClientID(currentPlayer.getClientID());
                        Timer timer = new Timer();
                        timer.schedule(new AIStartingPointTimer(client.getAi()), 3000);
                    }
                }
                case ERROR -> {
                    assert wrapperClass instanceof Error;
                    Error error = (Error) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleError(error));
                }
                case CONNECTIONUPDATE -> {
                    assert wrapperClass instanceof ConnectionUpdate;
                    ConnectionUpdate connectionUpdate = (ConnectionUpdate) wrapperClass;

                    Platform.runLater(() -> client.getGameModel().handleConnectionUpdate(connectionUpdate));
                }
            }
        }
    }
}