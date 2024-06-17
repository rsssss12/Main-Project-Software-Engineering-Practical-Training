package gamelogic;


import gamelogic.boardElements.BoardElement;
import gamelogic.boardElements.RestartPoint;
import gamelogic.boardElements.StartPoint;
import gamelogic.cards.Card;
import gamelogic.maps.BoardMap;
import gamelogic.round.Game;
import json.wrapper_utilities.WrapperClassSerialization;
import network.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Player represents a player of the game
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class.getName());

    private boolean currentlyRebooted = false;
    private StartPoint startPoint = new StartPoint("");
    private String username;
    private Robot robot = new Robot();
    private RobotEnum robotEnum;
    private int clientID;
    private final PlayerMat playerMat = new PlayerMat();
    private final Deck<Card> cardsInHand = new Deck<>();
    private int distanceToAntenna;
    private ClientHandler clientHandler;

    private Deck<Card> allCards;


    public Player() {

    }

    /**
     * The method draws a certain number of cards from the programming deck
     *
     * @param clientHandler : clientHandler
     * @param cardsToDraw   : int number of cards to draw
     */
    public void drawFromProgrammingDeck(ClientHandler clientHandler, int cardsToDraw) {
        Deck<Card> remainingCards = new Deck<>();
        boolean hadToBeShuffled = false;
        for (ClientHandler handler : clientHandler.getNetworkManager().getClientHandlers()) {
            if (this.getClientID() == handler.getClientID()) {
                clientHandler = handler;

            }
        }
        for (int cardsDrawn = 0; cardsDrawn < cardsToDraw; cardsDrawn++) {


            if (!playerMat.getProgrammingCardDeck().isDeckEmpty()) {

                cardsInHand.drawCardFrom(playerMat.getProgrammingCardDeck());

                if (hadToBeShuffled) {
                    remainingCards.add(cardsInHand.get(cardsInHand.size() - 1));

                }

            } else {
                sendSerializeYourCards(cardsInHand);

                playerMat.getProgrammingCardDeck().shuffleDeck(playerMat.getDiscardPile());
                sendShuffleCoding();

                hadToBeShuffled = true;

                cardsInHand.drawCardFrom(playerMat.getProgrammingCardDeck());

                remainingCards.add(cardsInHand.get(cardsInHand.size() - 1));

            }
        }
        if (!hadToBeShuffled) {
            sendSerializeYourCards(cardsInHand);
        } else {
            sendSerializeYourCards(remainingCards);
        }

        sendNotYourCards();
    }

    /**
     * The method sends the protocol NotYourCards to everyone except oneself
     */
    public void sendNotYourCards() {
        String jsonNotYourCards = WrapperClassSerialization.serializeNotYourCards(this);
        clientHandler.sendMessageToEveryoneElse(jsonNotYourCards);
        Game.delayingTime(1000);
    }

    /**
     * The method sends the protocol YourCards to oneself
     *
     * @param deck : card deck
     */
    public void sendSerializeYourCards(Deck<Card> deck) {
        String jsonCardsInHand = WrapperClassSerialization.serializeYourCards(deck);
        clientHandler.sendMessageToSelf(jsonCardsInHand);
        Game.delayingTime(1000);
    }

    /**
     * The method sends the protocol ShuffleCoding to oneself
     */
    public void sendShuffleCoding() {
        String jsonShuffle = WrapperClassSerialization.serializeShuffleCoding(this);
        clientHandler.sendMessageToSelf(jsonShuffle);
        Game.delayingTime(1000);
    }


    /**
     * The method fills the registers which were still empty
     *
     * @param clientHandler : clientHandler
     */
    public void fillRemainingRegisters(ClientHandler clientHandler) {
        ArrayList<Card> fillingCards = new ArrayList<>();
        int index = 0;

        Deck<Card> programmingCardDeck = playerMat.getProgrammingCardDeck();
        Deck<Card> discardPile = playerMat.getDiscardPile();

        discardHand();


        for (Card card : playerMat.getRegister()) {

            if (playerMat.isRegisterEmpty(card)) {

                if (programmingCardDeck.isDeckEmpty()) {
                    programmingCardDeck.shuffleDeck(discardPile);
                }
                cardsInHand.drawCardFrom(programmingCardDeck);
                Card card1 = cardsInHand.get(0);

                playerMat.getRegister()[index] = card1;

                fillingCards.add(card1);
                cardsInHand.remove(card1);

            }
            index++;
        }

        sendCardsYouGotNow(fillingCards);
    }

    /**
     * The method sends the protocol CardsYouGotNow to oneself
     *
     * @param fillingCards : array list of filled cards
     */
    public void sendCardsYouGotNow(ArrayList<Card> fillingCards) {
        String json = WrapperClassSerialization.serializeCardsYouGotNow(fillingCards);
        clientHandler.sendMessageToSelf(json);
        Game.delayingTime(1000);
    }


    /**
     * The method discards the cards in hand to the discard pile
     */
    public void discardHand() {

        for (Card card : cardsInHand) {
            if(card != null) {
                playerMat.getDiscardPile().add(card);
            }
        }

        cardsInHand.clear();

    }


    /**
     * The method reboots the robot of the player
     *
     * @param game : current game
     */
    public void rebooting(Game game) {
        Effects effects = new Effects();

        logger.debug("draw damage form rebooting");
        game.getBoard().drawDamageCards(this, 2);

        discardHand();

        playerMat.cleanRegisters();

        List<List<List<BoardElement>>> map = game.getBoard().getMap();

        Position position = robot.getPosition();

        BoardElement boardElement = map.get(position.getX()).get(position.getY()).get(0);

        String isOnBoard = boardElement.getIsOnBoard();
        if (isOnBoard.contains("Start")) {
            robot.setPosition(new Position(startPoint.getX(), startPoint.getY()));
        } else {
            ArrayList<RestartPoint> boardElementList = BoardMap.getBoardElementList(map, new RestartPoint("", new ArrayList<>()));
            boolean restartPointFound = false;
            for (RestartPoint restartPoint : boardElementList) {
                if (restartPoint.getIsOnBoard().equals(isOnBoard)) {

                    if (effects.isPositionOccupied(game.getPlayers(), new Position(restartPoint.getX(), restartPoint.getY()))) {

                        handleRestartPositionOccupied(game, new Position(restartPoint.getX(), restartPoint.getY()), restartPoint.getOrientations().get(0));
                    }

                    restartPointFound = true;
                    robot.setPosition(new Position(restartPoint.getX(), restartPoint.getY()));
                    break;
                }
            }
            if (!restartPointFound) {
                RestartPoint restartPoint = boardElementList.get(0);

                if (effects.isPositionOccupied(game.getPlayers(), new Position(restartPoint.getX(), restartPoint.getY()))) {

                    handleRestartPositionOccupied(game, new Position(restartPoint.getX(), restartPoint.getY()), restartPoint.getOrientations().get(0));

                }

                robot.setPosition(new Position(restartPoint.getX(), restartPoint.getY()));
            }
        }

        if (robot.getDirection().isOpposite(Direction.TOP)) {
            effects.rotate(this, "clockwise");
            effects.rotate(this, "clockwise");
        } else if (robot.getDirection().isClockwise(Direction.TOP)) {
            effects.rotate(this, "clockwise");
        } else if (robot.getDirection().isCounterClockwise(Direction.TOP)) {
            effects.rotate(this, "counterclockwise");
        }

        robot.setDirection(Direction.TOP);
        robot.setMovingDirection(Direction.TOP);

        effects.sendMovement(this);
        sendReboot(this);
        currentlyRebooted = true;
    }

    /**
     * The method handles the case, if the restart point is already occupied
     *
     * @param game        : current game
     * @param position    : Position
     * @param orientation : String orientation
     */
    public void handleRestartPositionOccupied(Game game, Position position, String orientation) {
        Effects effects = new Effects();
        Direction direction = Direction.valueOf(orientation.toUpperCase());
        Player player = effects.getPositionOccupier(game.getPlayers(), new Position(position.getX(), position.getY()));
        player.getRobot().setMovingDirection(direction);
        effects.move(game, player);
        player.getRobot().setMovingDirection(player.getRobot().getDirection());

    }

    /**
     * The method sends the protocol Reboot to everyone
     *
     * @param player : player who is rebooted
     */
    public void sendReboot(Player player) {

        String json = WrapperClassSerialization.serializeReboot(player.getClientID());
        player.getClientHandler().sendMessageToAllClients(json);
    }

    /**
     * The method searches the player list for player with certain clientID
     *
     * @param playerList : List<Player> list of players
     * @param clientID   : int clientID of the player
     * @return Player : player with this id
     */
    public static Player getPlayerByClientID(List<Player> playerList, int clientID) {

        for (Player player : playerList) {
            if (player.getClientID() == clientID) {
                return player;
            }
        }

        return null;
    }


    public Deck<Card> getAllCards() {
        return allCards;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Deck<Card> getCardsInHand() {
        return cardsInHand;
    }

    public PlayerMat getPlayerMat() {
        return playerMat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RobotEnum getRobotEnum() {
        return robotEnum;
    }

    public void setRobotEnum(RobotEnum robotEnum) {
        this.robotEnum = robotEnum;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public boolean isCurrentlyRebooted() {
        return currentlyRebooted;
    }

    public void setStartPoint(StartPoint startPoint) {
        this.startPoint = startPoint;
    }

    public int getDistanceToAntenna() {
        return distanceToAntenna;
    }

    public void setDistanceToAntenna(int distanceToAntenna) {
        this.distanceToAntenna = distanceToAntenna;
    }

    public void setCurrentlyRebooted(boolean currentlyRebooted) {
        this.currentlyRebooted = currentlyRebooted;
    }
}
