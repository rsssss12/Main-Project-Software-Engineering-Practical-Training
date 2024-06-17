package gui.mvvm.game;

import gamelogic.*;
import gamelogic.boardElements.BoardElement;
import gamelogic.boardElements.CheckPoint;
import gamelogic.boardElements.EnergySpace;
import gamelogic.boardElements.StartPoint;
import gamelogic.maps.BoardMap;
import gamelogic.round.TimerClient;
import gui.mvvm.lobby.LobbyModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import json.json_wrappers.actions_events_effects.*;
import json.json_wrappers.lobby.GameStarted;
import json.json_wrappers.round.ActivePhase;
import json.json_wrappers.round.CurrentPlayer;
import json.json_wrappers.round.activation_phase.CurrentCards;
import json.json_wrappers.round.activation_phase.ReplaceCard;
import json.json_wrappers.round.programming_phase.*;
import json.json_wrappers.round.setup_phase.StartingPointTaken;
import json.json_wrappers.special_messages.ConnectionUpdate;
import json.json_wrappers.special_messages.Error;
import json.wrapper_utilities.MapWrapping;
import messages.*;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static gamelogic.Direction.LEFT;

/**
 * The class GameModel
 *
 * @author Tim Kriegelsteiner
 */
public class GameModel {

    private final Logger logger = LogManager.getLogger(GameModel.class.getName());
    private Client client;
    private LobbyModel lobbyModel;

    private static volatile GameModel gameModel;


    //Game
    private final IntegerProperty activePhaseNumber = new SimpleIntegerProperty();
    private final IntegerProperty currentPlayerID = new SimpleIntegerProperty();
    private final StringProperty activePhaseProperty = new SimpleStringProperty("Active Phase");
    private final BooleanProperty gameFinished = new SimpleBooleanProperty(false);

    //SetupPhase

    private final List<Position> startPointsTaken = new ArrayList<>();
    private final BooleanProperty areStartingPointsDisabled = new SimpleBooleanProperty(true);

    //Programming Phase
    private final StringProperty[] registerProperties = new StringProperty[5];
    private final StringProperty register1 = new SimpleStringProperty(null);
    private final StringProperty register2 = new SimpleStringProperty(null);
    private final StringProperty register3 = new SimpleStringProperty(null);
    private final StringProperty register4 = new SimpleStringProperty(null);
    private final StringProperty register5 = new SimpleStringProperty(null);


    private Timer timer = new Timer();
    private TimerClient timerClient = new TimerClient(timer);
    private BooleanProperty isTimerRunning = new SimpleBooleanProperty(false);
    private BooleanProperty timerHasEnded = new SimpleBooleanProperty(false);

    private final BooleanProperty selectionFinished = new SimpleBooleanProperty(false);

    //ActivationPhase
    private final BooleanProperty activatedTwisterBelts = new SimpleBooleanProperty(false);
    private List<CheckPoint> checkPointList = new ArrayList<>();
    private List<Position> oldCheckPointPositions = new ArrayList<>();
    private List<Position> newCheckPointPositions = new ArrayList<>();

    //Player

    private final StringProperty cardInHand1Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand2Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand3Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand4Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand5Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand6Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand7Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand8Property = new SimpleStringProperty(null);
    private final StringProperty cardInHand9Property = new SimpleStringProperty(null);
    private final List<StringProperty> cardsInHandPropertyArrayList = new ArrayList<>();


    private ObjectProperty<Position> positionRobot1 = new SimpleObjectProperty<>(new Position());
    private ObjectProperty<Position> positionRobot2 = new SimpleObjectProperty<>(new Position());
    private ObjectProperty<Position> positionRobot3 = new SimpleObjectProperty<>(new Position());
    private ObjectProperty<Position> positionRobot4 = new SimpleObjectProperty<>(new Position());
    private ObjectProperty<Position> positionRobot5 = new SimpleObjectProperty<>(new Position());
    private ObjectProperty<Position> positionRobot6 = new SimpleObjectProperty<>(new Position());
    private List<ObjectProperty<Position>> positionsRobots = new ArrayList<>();
    private Map<Integer, ObjectProperty<Position>> robotPositionPropertyByRobotNumber = new HashMap<>();


    private ObjectProperty<Direction> directionRobot1 = new SimpleObjectProperty<>(LEFT);
    private ObjectProperty<Direction> directionRobot2 = new SimpleObjectProperty<>(LEFT);
    private ObjectProperty<Direction> directionRobot3 = new SimpleObjectProperty<>(LEFT);
    private ObjectProperty<Direction> directionRobot4 = new SimpleObjectProperty<>(LEFT);
    private ObjectProperty<Direction> directionRobot5 = new SimpleObjectProperty<>(LEFT);
    private ObjectProperty<Direction> directionRobot6 = new SimpleObjectProperty<>(LEFT);

    private List<ObjectProperty<Direction>> directionsRobots = new ArrayList<>();
    private Map<Integer, ObjectProperty<Direction>> robotDirectionByRobotNumber = new HashMap<>();

    private final IntegerProperty ownEnergyReserve = new SimpleIntegerProperty(5);
    private final StringProperty ownEnergyReserveToString = new SimpleStringProperty(ownEnergyReserve.getValue().toString());
    private final StringProperty ownPlayerName = new SimpleStringProperty(" ");
    private final StringProperty ownRobotName = new SimpleStringProperty("Robot Name");

    private final IntegerProperty ownCheckpointsReached = new SimpleIntegerProperty(0);
    private StringProperty ownCheckpointsReachedToString = new SimpleStringProperty(ownCheckpointsReached.getValue().toString());
    private StringProperty ownClientID = new SimpleStringProperty();

    //PlayerMat


    //Board
    private List<List<List<BoardElement>>> map = new ArrayList<>();
    private List<StartPoint> startPoints = new ArrayList<>();
    private ObservableList<EnergySpace> energySpacesUsedObservable = FXCollections.observableArrayList();
    private ListProperty<EnergySpace> energySpacesUsed = new SimpleListProperty<>(energySpacesUsedObservable);


    //GUI
    private StringProperty eventMessage = new SimpleStringProperty();

    public GameModel() {

        lobbyModel = LobbyModel.getInstance();

        fillPositionsRobots();
        fillRobotPositionByRobot();
        fillCardsInHandPropertyArrayList();
        fillRegisterProperties();
        fillDirectionsRobots();
        initializeRobotDirectionByRobotNumber();

    }

    public static GameModel getInstance() {
        if (gameModel == null) {
            synchronized (GameModel.class) {
                if (gameModel == null) {
                    gameModel = new GameModel();
                }
            }
        }
        return gameModel;
    }

    /**
     * The method handles the protocol GameStarted
     *
     * @param gameStarted : GameStarted protocol
     */
    public void handleGameStarted(GameStarted gameStarted) {

        logger.debug("GameStarted in GameModel");

        map = MapWrapping.buildMapFromJson(gameStarted);
        BoardMap.applyPositionToElementInMap(map);
        startPoints = BoardMap.getBoardElementList(map, new StartPoint(""));
        checkPointList = BoardMap.getBoardElementList(map, new CheckPoint("", 0));

        initializeRobotPositions();
        initializeOwnProperties();

        Timer timer1 = new Timer();
        TimerTask timerTask = new TimerTask() {
            int times = 0;

            @Override
            public void run() {
                if (times < 1) {
                    Platform.runLater(() -> eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE2.getMessage()));
//                    eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE2.getMessage());
                } else {
                    Platform.runLater(() -> eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE3.getMessage()));
//                    eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE3.getMessage());
                    timer1.cancel();
                }
                times++;
            }
        };

        eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE1.getMessage());


//        eventMessage.setValue(MessagesSetupPhase.GAME_STARTED_MESSAGE2.getMessage());
        timer1.schedule(timerTask, 4000, 3500);

    }

    /**
     * The method handles the protocol CurrentPlayer
     *
     * @param currentPlayer : CurrentPlayer protocol
     */
    public void handleCurrentPlayer(CurrentPlayer currentPlayer) {
        int clientID = currentPlayer.getClientID();
        currentPlayerID.setValue(clientID);
        boolean isPlayerOneself = lobbyModel.isThisPlayerOneself(clientID);
        StringBuilder stringBuilder = new StringBuilder();
        String string = "";

        switch (activePhaseNumber.getValue()) {
            case 0 -> {
                if (isPlayerOneself) {

                    stringBuilder.append(MessagesGame.CURRENT_PLAYER_YOU.getMessage());
                    stringBuilder.append(" ");
                    stringBuilder.append(MessagesSetupPhase.SET_STARTING_POINT_YOU.getMessage());
                    string = stringBuilder.toString();
                } else {

                    stringBuilder.append(MessagesGame.CURRENT_PLAYER_OTHER.getMessage());
                    stringBuilder.append(" ");
                    stringBuilder.append(MessagesSetupPhase.SET_STARTING_POINT_OTHER.getMessage());

                    string = stringBuilder.toString();
                    string = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), string);
                }
            }
        }
        eventMessage.setValue(string);

    }

    /**
     * The method handles the protocol ActivePhase
     *
     * @param activePhase : ActivePhase protocol
     */
    public void handleActivePhase(ActivePhase activePhase) {
        int phase = activePhase.getPhase();
        activePhaseNumber.setValue(phase);
        String message = MessagesGame.ACTIVE_PHASE_GAME.getMessage();

        switch (phase) {
            case 0 -> {
                message = message.replace("$$phase", "Setup Phase");
                activePhaseProperty.setValue("Setup Phase");
                areStartingPointsDisabled.setValue(false);
            }
            case 2 -> {
                message = message.replace("$$phase", "Programming Phase");
                cleanUpActivationPhase();
                activePhaseProperty.setValue("Programming Phase");
            }
            case 3 -> {
                message = message.replace("$$phase", "Activation Phase");
                cleanUpProgrammingPhase();
                activePhaseProperty.setValue("Activation Phase");
            }
        }

        eventMessage.setValue(message);
    }

    //SetupPhase

    /**
     * The method handles the protocol StartingPointTaken
     *
     * @param startingPointTaken : StartingPointTaken protocol
     */
    public void handleStartingPointTaken(StartingPointTaken startingPointTaken) {
        int x = startingPointTaken.getX();
        int y = startingPointTaken.getY();
        Integer clientID = startingPointTaken.getClientID();
        int clientIDInt = startingPointTaken.getClientID();

        Position position = new Position(x, y);

        int robotNumber = lobbyModel.getRobotNumberByClientID().get(clientID);

        robotPositionPropertyByRobotNumber.get(robotNumber).setValue(position);
        startPointsTaken.add(position);

        BoardMapCreator boardMapCreator = new BoardMapCreator();
        Direction direction = boardMapCreator.determineStartBoardSide(BoardMap.getBoardElementList(gameModel.getMap(), new StartPoint("")), gameModel.getMap().get(0).size(), gameModel.getMap().get(0).get(0).size());
        robotDirectionByRobotNumber.get(robotNumber).setValue(direction.getOpposite());

        String message;

        if (lobbyModel.isThisPlayerOneself(clientIDInt)) {
            message = MessagesSetupPhase.STARTING_POINT_TAKEN_YOU.getMessage();

            areStartingPointsDisabled.setValue(true);

        } else {
            message = MessagesSetupPhase.STARTING_POINT_TAKEN_OTHER.getMessage();
            message = MessagesUtility.replaceUsername(clientIDInt, lobbyModel.getPlayers(), message);
        }

        message = MessagesUtility.replacePosition(x, y, message);

        eventMessage.setValue(message);
    }

    //ProgrammingPhase

    /**
     * The method handles the protocol YourCards
     *
     * @param yourCards : YourCards protocol
     */
    public void handleYourCards(YourCards yourCards) {
        ArrayList<String> cardsInHand = yourCards.getCardsInHand();

//        cardsInHandArrayList.addAll(cardsInHand);

        for (String card : cardsInHand) {

            for (StringProperty stringProperty : cardsInHandPropertyArrayList) {

                if (stringProperty.getValue() == null) {
                    stringProperty.setValue(card);
                    break;
                }
            }
        }

        if (isHandFull()) {
            eventMessage.setValue(MessagesProgrammingPhase.YOUR_CARDS.getMessage());
        }

    }

    /**
     * The method checks if cardsInHand is full
     *
     * @return boolean : true if hand full
     */
    public boolean isHandFull() {
        for (StringProperty stringProperty : cardsInHandPropertyArrayList) {
            if (stringProperty.getValue() == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * The method handles the protocol NotYourCards
     *
     * @param notYourCards : NotYourCards protocol
     */
    public void handleNotYourCards(NotYourCards notYourCards) {
        int clientID = notYourCards.getClientID();
        int cardsInHand = notYourCards.getCardsInHand();


        String message = MessagesProgrammingPhase.NOT_YOUR_CARDS.getMessage();
        message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        message = MessagesUtility.replaceNumber(cardsInHand, message);

//        eventMessage.setValue(message);
    }


    public void handleShuffleCoding(ShuffleCoding shuffleCoding) {
        eventMessage.setValue(MessagesProgrammingPhase.SHUFFLE_CODING.getMessage());
    }

    public void handleCardSelected(CardSelected cardSelected) {
    }

    /**
     * The method handles the protocol SelectionFinished
     *
     * @param selectionFinished : SelectionFinished protocol
     */
    public void handleSelectionFinished(SelectionFinished selectionFinished) {
        int clientID = selectionFinished.getClientID();

        String message;

        if (lobbyModel.isThisPlayerOneself(clientID)) {
            message = MessagesProgrammingPhase.REGISTER_FINISHED_YOU.getMessage();
            clearCardsInHand();
        } else {
            message = MessagesProgrammingPhase.REGISTER_FINISHED_OTHER.getMessage();
            message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        }

        eventMessage.setValue(message);
    }

    /**
     * The method handles the protocol TimerStarted
     *
     * @param timerStarted : TimerStarted
     */
    public void handleTimerStarted(TimerStarted timerStarted) {
        eventMessage.setValue(MessagesProgrammingPhase.TIMER_STARTED.getMessage());

        isTimerRunning.setValue(true);
        timer.scheduleAtFixedRate(timerClient, 0, 1000);

    }

    /**
     * The method handles the protocol TimerEnded
     *
     * @param timerEnded : TimerEnded protocol
     */
    public void handleTimerEnded(TimerEnded timerEnded) {
        ArrayList<Integer> clientIDs = timerEnded.getClientIDs();


        isTimerRunning.setValue(false);
        timer.cancel();
        timer = new Timer();
        timerClient = new TimerClient(timer);

        timerHasEnded.setValue(true);

        if (isSelfRegisterUnfinished(clientIDs)) {
            discardAllCardsInHand();
        }

        eventMessage.setValue(stringBuildTimerEnded(clientIDs));
    }

    public String stringBuildTimerEnded(ArrayList<Integer> clientIDs) {
        StringBuilder stringBuilder = new StringBuilder(MessagesProgrammingPhase.TIMER_ENDED.getMessage());
        String message;
        if (clientIDs.isEmpty()) {
            stringBuilder.append(MessagesProgrammingPhase.TIMER_ENDED_NONE.getMessage());
            message = stringBuilder.toString();
        } else if (clientIDs.size() == 1) {
            stringBuilder.append(MessagesProgrammingPhase.TIMER_ENDED_SINGLE.getMessage());
            message = stringBuilder.toString();
            message = MessagesUtility.replaceUsername(clientIDs.get(0), lobbyModel.getPlayers(), message);

        } else {
            stringBuilder.append(MessagesProgrammingPhase.TIMER_ENDED_MULTIPLE.getMessage());
            message = stringBuilder.toString();
            message = MessagesUtility.replaceUsernames(clientIDs, lobbyModel.getPlayers(), message);
        }

        return message;
    }

    public void discardAllCardsInHand() {
        for (StringProperty stringProperty : cardsInHandPropertyArrayList) {
            stringProperty.setValue(null);
        }
    }

    public boolean isSelfRegisterUnfinished(ArrayList<Integer> clientIDs) {

        for (Integer clientID : clientIDs) {
            if (lobbyModel.isThisPlayerOneself(clientID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method handles the protocol CardsYouGotNow
     *
     * @param cardsYouGotNow : CardsYouGotNow protocol
     */
    public void handleCardsYouGotNow(CardsYouGotNow cardsYouGotNow) {
        ArrayList<String> cards = cardsYouGotNow.getCards();


        for (String card : cards) {
            int index = 0;
            for (StringProperty registerProperty : registerProperties) {

                logger.debug("registerProperty.getValue() == null: " + (registerProperty.getValue() == null));

                if (registerProperty.getValue() == null) {
                    registerProperties[index].setValue(card);
                    break;
                }
                index++;
            }


        }

        for (StringProperty stringProperty : cardsInHandPropertyArrayList) {
            stringProperty.setValue(null);
        }

        String message = MessagesProgrammingPhase.CARDS_YOU_GOT_NOW.getMessage();
        message = MessagesUtility.replaceCards(cards, message);
        eventMessage.setValue(message);

    }

    public void isSelectionFinished() {
        for (StringProperty registerProperty : registerProperties) {
            if (registerProperty.getValue() == null) {
                return;
            }
        }
        selectionFinished.setValue(true);
        clearCardsInHand();

    }

    public void clearCardsInHand() {
        for (StringProperty stringProperty : cardsInHandPropertyArrayList) {
            stringProperty.setValue(null);
        }
    }


    public void cleanUpProgrammingPhase() {
        selectionFinished.setValue(false);
        timerHasEnded.setValue(false);
    }


    //ActivationPhase

    public void handleCurrentCards(CurrentCards currentCards) {
        HashMap<Integer, String> activeCards = currentCards.getActiveCards();


//        eventMessage.setValue(stringBuildCurrentCards(currentCards));
    }

    public String stringBuildCurrentCards(CurrentCards currentCards) {
        HashMap<Integer, String> activeCards = currentCards.getActiveCards();
        StringBuilder stringBuilder = new StringBuilder();
        String message = MessagesActivationPhase.CURRENT_CARDS.getMessage();

        activeCards.forEach((clientID, card) -> {

            String messageModified = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
            messageModified = MessagesUtility.replaceCard(card, messageModified);
            stringBuilder.append(messageModified);

//            if (!activeCards.get(activeCards.size() - 1).equals(card)) {
            stringBuilder.append("\n");
//            }
        });
        return stringBuilder.toString();
    }

    /**
     * The method handles the protocol ReplaceCard
     *
     * @param replaceCard : ReplaceCard protocol
     */
    public void handleReplaceCard(ReplaceCard replaceCard) {
        int clientID = replaceCard.getClientID();
        String newCard = replaceCard.getNewCard();
        int register = replaceCard.getRegister();

        if (lobbyModel.isThisPlayerOneself(clientID)) {
            registerProperties[register].setValue(newCard);
        }

        eventMessage.setValue(stringBuildReplaceCard(replaceCard));
    }

    public String stringBuildReplaceCard(ReplaceCard replaceCard) {
        int clientID = replaceCard.getClientID();
        String newCard = replaceCard.getNewCard();
        int register = replaceCard.getRegister();
        String message = MessagesActivationPhase.REPLACE_CARD.getMessage();

        message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        message = MessagesUtility.replaceCard(newCard, message);
        return MessagesUtility.replaceNumber(register, message);
    }

    //Action, Events, Effects

    /**
     * The method handles the protocol Movement
     *
     * @param movement : Movement protocol
     */
    public void handleMovement(Movement movement) {
        int clientID = movement.getClientID();
        int x = movement.getX();
        int y = movement.getY();

        Position position = new Position(x, y);

        int robotNumber = lobbyModel.getRobotNumberByClientID().get(clientID);

        robotPositionPropertyByRobotNumber.get(robotNumber).setValue(position);

        //Extra
        for (Player player : lobbyModel.getPlayers()) {
            if (player.getClientID() == clientID) {
                player.getRobot().setPosition(position);
            }
        }

        eventMessage.setValue(stringBuildMovement(movement));
    }

    public String stringBuildMovement(Movement movement) {
        int clientID = movement.getClientID();
        int x = movement.getX();
        int y = movement.getY();

        String message = MessagesActivationPhase.MOVEMENT.getMessage();

        message = replacePersonOfMessage(message, clientID);

        return MessagesUtility.replacePosition(x, y, message);
    }

    /**
     * The method handles the protocol PlayerTurning
     *
     * @param playerTurning : PlayerTurning protocol
     */
    public void handlePlayerTurn(PlayerTurning playerTurning) {
        int clientID = playerTurning.getClientID();
        String rotation = playerTurning.getRotation();
        Player player = Player.getPlayerByClientID(lobbyModel.getPlayers(), clientID);
        assert player != null;
        Integer robotNumber = player.getRobotEnum().getNumber();
        ObjectProperty<Direction> directionObjectProperty = robotDirectionByRobotNumber.get(robotNumber);


        if (rotation.equals("clockwise")) {
            directionObjectProperty.setValue(directionObjectProperty.getValue().getClockwise());
        } else {
            directionObjectProperty.setValue(directionObjectProperty.getValue().getCounterClockwise());
        }

        logger.debug("robotNumber + direction: " + robotNumber + " + " + directionObjectProperty.getValue());

        //Extra
        player.getRobot().setDirection(directionObjectProperty.getValue());

        eventMessage.setValue(stringBuildPlayerTurn(playerTurning));
    }

    public String stringBuildPlayerTurn(PlayerTurning playerTurning) {
        int clientID = playerTurning.getClientID();
        String rotation = playerTurning.getRotation();
        String message = MessagesActivationPhase.PLAYER_TURNING.getMessage();

        message = replacePersonOfMessage(message, clientID);
        if (rotation.equals("clockwise")) {
            message = message.replace("$$rotation", "clockwise");
        } else {
            message = message.replace("$$rotation", "counterclockwise");
        }

        return message;
    }

    public void handleDrawDamage(DrawDamage drawDamage) {

        eventMessage.setValue(stringBuildDrawDamage(drawDamage));
    }

    public String stringBuildDrawDamage(DrawDamage drawDamage) {
        int clientID = drawDamage.getClientID();
        ArrayList<String> cards = drawDamage.getCards();

        String message = MessagesActivationPhase.DRAW_DAMAGE.getMessage();
        message = MessagesUtility.replaceCards(cards, message);

        message = replacePersonOfMessage(message, clientID);

        return message;
    }

    public void handleSelectedDamage(SelectedDamage selectedDamage) {
    }

    public void handleReboot(Reboot reboot) {

        int clientID = reboot.getClientID();

        String message = MessagesActivationPhase.REBOOT.getMessage();

        eventMessage.setValue(replacePersonOfMessage(message, clientID));

    }

    /**
     * The method handles the protocol Energy
     *
     * @param energy : Energy protocol
     */
    public void handleEnergy(Energy energy) {
        int clientID = energy.getClientID();
        String source = energy.getSource();
        int count = energy.getCount();
        String message = MessagesGame.ENERGY.getMessage();

        if (clientID == client.getClientID()) {
            ownEnergyReserve.setValue(ownEnergyReserve.getValue() + count);
            ownEnergyReserveToString.set(ownEnergyReserve.getValue().toString());
            message = message.replace("$$username", "You");
        } else {
            message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        }
        message = MessagesUtility.replaceNumber(count, message);

        eventMessage.setValue(message);

        if (source.equals("EnergySpace")) {
            Player player = Player.getPlayerByClientID(lobbyModel.getPlayers(), clientID);
            assert player != null;
            Position position = player.getRobot().getPosition();
            List<BoardElement> list = map.get(position.getX()).get(position.getY());
            for (BoardElement boardElement : list) {
                if (boardElement instanceof EnergySpace energySpace && !energySpacesUsedObservable.contains(energySpace)) {
                    energySpacesUsedObservable.add(energySpace);
                }
            }

        }

        //Extra
        for (Player player : lobbyModel.getPlayers()) {
            if (player.getClientID() == clientID) {
                player.getPlayerMat().setEnergyReserve(player.getPlayerMat().getEnergyReserve() + 1);
            }
        }

    }

    /**
     * The method handles the protocol CheckPointReached
     *
     * @param checkPointReached : CheckPointReached protocol
     */
    public void handleCheckpointReached(CheckPointReached checkPointReached) {
        int clientID = checkPointReached.getClientID();
        int number = checkPointReached.getNumber();

        if (clientID == client.getClientID()) {
            ownCheckpointsReached.setValue(number);
            ownCheckpointsReachedToString.setValue(ownCheckpointsReached.getValue().toString());
        }

        //Extra
        for (Player player : lobbyModel.getPlayers()) {
            if (player.getClientID() == clientID) {
                player.getPlayerMat().setCheckpointsReached(number);
            }
        }
    }

    /**
     * The protocol handles the protocol Animation
     *
     * @param animation : Animation protocol
     */
    public void handleAnimation(Animation animation) {
        String type = animation.getType();
        String message = MessagesActivationPhase.ANIMATION.getMessage();
        String boardElementVariable = "$$boardElements";

        switch (AnimationEnum.valueOf(type.toUpperCase())) {

            case BLUECONVEYORBELT -> {
                message = message.replace(boardElementVariable, "Blue conveyor belts");

                if (lobbyModel.selectedMapProperty().getValue().equals("Twister")) {

                    oldCheckPointPositions.clear();
                    newCheckPointPositions.clear();
                    for (CheckPoint checkPoint : checkPointList) {
                        oldCheckPointPositions.add(new Position(checkPoint.getX(), checkPoint.getY()));
                    }

                    Effects effects = new Effects();
                    effects.twisterHandling(map);

                    for (CheckPoint checkPoint : checkPointList) {
                        newCheckPointPositions.add(new Position(checkPoint.getX(), checkPoint.getY()));
                    }

                    activatedTwisterBelts.setValue(true);
                    activatedTwisterBelts.setValue(false);
                }

            }

            case GREENCONVEYORBELT ->
                    message = message.replace(boardElementVariable, "Green conveyor belts");
            case PUSHPANEL -> message = message.replace(boardElementVariable, "Push panels");
            case GEAR -> message = message.replace(boardElementVariable, "Gears");
            case WALLSHOOTING -> message = message.replace(boardElementVariable, "Wall lasers");
            case PLAYERSHOOTING -> message = message.replace(boardElementVariable, "Robot lasers");
            case ENERGYSPACE -> message = message.replace(boardElementVariable, "Energy spaces");
            case CHECKPOINT -> message = message.replace(boardElementVariable, "Checkpoints");
        }

        eventMessage.setValue(message);
    }

    /**
     * The method handles the protocol GameFinished
     *
     * @param gameFinished : GameFinished protocol
     */
    public void handleGameFinished(GameFinished gameFinished) {
        int clientID = gameFinished.getClientID();
        Player player = Player.getPlayerByClientID(lobbyModel.getPlayers(), clientID);
        String message = MessagesGame.GAME_FINISHED.getMessage();
        message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        eventMessage.setValue(message);
        lobbyModel.handleGameFinished();


        Timer timer1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> gameFinishedProperty().setValue(true));
            }
        };

        timer1.schedule(task, 6000);


    }

    public void cleanUpActivationPhase() {
        clearRegister();
    }

    public void clearRegister() {
        for (StringProperty registerProperty : registerProperties) {
            registerProperty.setValue(null);
        }
    }


    //Others
    public void handleError(Error error) {
    }

    public void handleConnectionUpdate(ConnectionUpdate connectionUpdate) {
        int clientID = connectionUpdate.getClientID();
        boolean connected = connectionUpdate.isConnected();
        String action = connectionUpdate.getAction();

        if (action.equals("remove")) {

            lobbyModel.removePlayer(clientID);


        }
    }


    public String replacePersonOfMessage(String message, int clientID) {
        if (lobbyModel.isThisPlayerOneself(clientID)) {

            if (message.contains("$$username's")) {
                message = message.replace("$$username's", "Your");
            } else {
                message = message.replace("$$username", "You");
            }

        } else {
            message = MessagesUtility.replaceUsername(clientID, lobbyModel.getPlayers(), message);
        }

        return message;
    }

    public void initializeOwnProperties() {
        Integer clientID = client.getClientID();

        if (isClientIDPlayer(client.getClientID())) {
            int robotNumber = lobbyModel.getRobotNumberByClientID().get(clientID);

            RobotEnum robotEnum = RobotEnum.getEnumFromNumber(lobbyModel.getRobotNumberByClientID().get(clientID));

            ownPlayerName.setValue(lobbyModel.getPlayerLabelMap().get(robotNumber).getValue());

            assert robotEnum != null;
            ownRobotName.setValue(robotEnum.getName());
        } else {
            ownPlayerName.setValue("Spectator");
            ownRobotName.setValue("The Spectator");
        }


        ownClientID.setValue(Integer.toString(clientID));

    }

    public boolean isClientIDPlayer(int clientID) {
        for (Player player : lobbyModel.getPlayers()) {
            if (player.getClientID() == clientID) {
                return true;
            }
        }
        return false;
    }

    public int getRobotNumberByRobotDirection(ObjectProperty<Direction> directionObjectProperty) {
        for (int robotNumber = 1; robotNumber < robotDirectionByRobotNumber.size() + 1; robotNumber++) {

            if (robotDirectionByRobotNumber.get(robotNumber).equals(directionObjectProperty)) {
                return robotNumber;
            }
        }
        return 0;
    }

    public void initializeRobotDirectionByRobotNumber() {
        int robotNumber = 1;
        for (ObjectProperty<Direction> directionsRobot : directionsRobots) {
            robotDirectionByRobotNumber.put(robotNumber, directionsRobot);
            robotNumber++;
        }
    }

    public void fillDirectionsRobots() {
        directionsRobots.add(directionRobot1);
        directionsRobots.add(directionRobot2);
        directionsRobots.add(directionRobot3);
        directionsRobots.add(directionRobot4);
        directionsRobots.add(directionRobot5);
        directionsRobots.add(directionRobot6);

    }

    public void initializeRobotPositions() {
        int index = 0;
        for (Player player : lobbyModel.getPlayers()) {
            positionsRobots.get(index).setValue(player.getRobot().getPosition());
            index++;
        }
    }

    public void fillPositionsRobots() {
        positionsRobots.add(positionRobot1Property());
        positionsRobots.add(positionRobot2Property());
        positionsRobots.add(positionRobot3Property());
        positionsRobots.add(positionRobot4Property());
        positionsRobots.add(positionRobot5Property());
        positionsRobots.add(positionRobot6Property());
    }

    public void fillRobotPositionByRobot() {

        for (int robot = 1; robot < 7; robot++) {
            robotPositionPropertyByRobotNumber.put(robot, positionsRobots.get(robot - 1));
        }
    }

    public void fillCardsInHandPropertyArrayList() {
        cardsInHandPropertyArrayList.add(cardInHand1Property);
        cardsInHandPropertyArrayList.add(cardInHand2Property);
        cardsInHandPropertyArrayList.add(cardInHand3Property);
        cardsInHandPropertyArrayList.add(cardInHand4Property);
        cardsInHandPropertyArrayList.add(cardInHand5Property);
        cardsInHandPropertyArrayList.add(cardInHand6Property);
        cardsInHandPropertyArrayList.add(cardInHand7Property);
        cardsInHandPropertyArrayList.add(cardInHand8Property);
        cardsInHandPropertyArrayList.add(cardInHand9Property);
    }

    public void fillRegisterProperties() {
        registerProperties[0] = register1;
        registerProperties[1] = register2;
        registerProperties[2] = register3;
        registerProperties[3] = register4;
        registerProperties[4] = register5;

    }

    public int getRobotNumberByPositionProperty(ObjectProperty<Position> position) {
        for (int robotNumber = 1; robotNumber < robotPositionPropertyByRobotNumber.size() + 1; robotNumber++) {

            if (robotPositionPropertyByRobotNumber.get(robotNumber).equals(position)) {
                return robotNumber;
            }

        }
        return 0;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<List<List<BoardElement>>> getMap() {
        return map;
    }

    public void setMap(List<List<List<BoardElement>>> map) {
        this.map = map;
    }

    public Map<Integer, ObjectProperty<Position>> getRobotPositionPropertyByRobotNumber() {
        return robotPositionPropertyByRobotNumber;
    }

    public ObjectProperty<Position> positionRobot1Property() {
        return positionRobot1;
    }

    public ObjectProperty<Position> positionRobot2Property() {
        return positionRobot2;
    }

    public ObjectProperty<Position> positionRobot3Property() {
        return positionRobot3;
    }

    public ObjectProperty<Position> positionRobot4Property() {
        return positionRobot4;
    }

    public ObjectProperty<Position> positionRobot5Property() {
        return positionRobot5;
    }

    public ObjectProperty<Position> positionRobot6Property() {
        return positionRobot6;
    }

    public List<StartPoint> getStartPoints() {
        return startPoints;
    }

    public List<Position> getStartPointsTaken() {
        return startPointsTaken;
    }

    public int getActivePhaseNumber() {
        return activePhaseNumber.get();
    }

    public int getCurrentPlayerID() {
        return currentPlayerID.get();
    }

    public TimerClient getTimerClient() {
        return timerClient;
    }

    public BooleanProperty isTimerRunningProperty() {
        return isTimerRunning;
    }

    public List<StringProperty> getCardsInHandPropertyArrayList() {
        return cardsInHandPropertyArrayList;
    }

    public List<ObjectProperty<Position>> getPositionsRobots() {
        return positionsRobots;
    }

    public StringProperty[] getRegisterProperties() {
        return registerProperties;
    }

    public List<ObjectProperty<Direction>> getDirectionsRobots() {
        return directionsRobots;
    }

    public Map<Integer, ObjectProperty<Direction>> getRobotDirectionByRobotNumber() {
        return robotDirectionByRobotNumber;
    }

    public StringProperty ownEnergyReserveToStringProperty() {
        return ownEnergyReserveToString;
    }

    public StringProperty ownPlayerNameProperty() {
        return ownPlayerName;
    }

    public BooleanProperty selectionFinishedProperty() {
        return selectionFinished;
    }

    public String getOwnRobotName() {
        return ownRobotName.get();
    }

    public StringProperty ownRobotNameProperty() {
        return ownRobotName;
    }

    public StringProperty eventMessageProperty() {
        return eventMessage;
    }

    public BooleanProperty areStartingPointsDisabledProperty() {
        return areStartingPointsDisabled;
    }

    public BooleanProperty timerHasEndedProperty() {
        return timerHasEnded;
    }

    public StringProperty ownCheckpointsReachedToStringProperty() {
        return ownCheckpointsReachedToString;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }

    public StringProperty ownClientIDProperty() {
        return ownClientID;
    }

    public StringProperty activePhasePropertyProperty() {
        return activePhaseProperty;
    }

    public ListProperty<EnergySpace> energySpacesUsedProperty() {
        return energySpacesUsed;
    }

    public BooleanProperty gameFinishedProperty() {
        return gameFinished;
    }

    public BooleanProperty activatedTwisterBeltsProperty() {
        return activatedTwisterBelts;
    }

    public List<Position> getOldCheckPointPositions() {
        return oldCheckPointPositions;
    }

    public List<Position> getNewCheckPointPositions() {
        return newCheckPointPositions;
    }
}
