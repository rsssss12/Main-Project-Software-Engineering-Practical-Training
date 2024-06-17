
package json.wrapper_utilities;

import gamelogic.Deck;
import gamelogic.maps.MapEnum;
import gamelogic.Player;
import gamelogic.Position;
import gamelogic.RobotEnum;
import gamelogic.boardElements.BoardElement;
import gamelogic.cards.Card;
import gamelogic.round.Game;
import json.json_wrappers.actions_events_effects.*;
import json.json_wrappers.cards.CardPlayed;
import json.json_wrappers.cards.PlayCard;
import json.json_wrappers.chat.ReceivedChat;
import json.json_wrappers.chat.SendChat;
import json.json_wrappers.connection.Alive;
import json.json_wrappers.connection.HelloClient;
import json.json_wrappers.connection.HelloServer;
import json.json_wrappers.connection.Welcome;
import json.json_wrappers.lobby.*;
import json.json_wrappers.round.ActivePhase;
import json.json_wrappers.round.CurrentPlayer;
import json.json_wrappers.round.activation_phase.CurrentCards;
import json.json_wrappers.round.activation_phase.ReplaceCard;
import json.json_wrappers.round.programming_phase.*;
import json.json_wrappers.round.setup_phase.SetStartingPoint;
import json.json_wrappers.round.setup_phase.StartingPointTaken;
import json.json_wrappers.special_messages.ConnectionUpdate;
import json.json_wrappers.special_messages.Error;
import messages.MessagesError;
import network.ClientHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class WrapperClassSerialization serializes the different protocols
 *
 * @author Tim Kriegelsteiner
 */
public class WrapperClassSerialization {

    //actions_events_effects

    /**
     * The method serializes the Animation Protocol
     *
     * @param type: String type of animation
     * @return: String
     */
    public static String serializeAnimation(String type) {
        Animation animation = new Animation(type);

        return Serializer.serializeData(animation);
    }

    /**
     * The method serializes the CheckPointReached Protocol
     *
     * @param clientID:   int client id
     * @param checkpoint: int number of checkpoints
     * @return: String
     */
    public static String serializeCheckPointReached(int clientID, int checkpoint) {
        CheckPointReached checkPointReached = new CheckPointReached(clientID, checkpoint);

        return Serializer.serializeData(checkPointReached);
    }

    /**
     * The method serializes the DrawDamage protocol
     *
     * @param player: current Player
     * @param cards:  ArrayList<Card> cards
     * @return: String
     */
    public static String serializeDrawDamage(Player player, ArrayList<Card> cards) {

        ArrayList<String> cardsString = CardWrapping.cardListToStringArrayList(cards);

        DrawDamage drawDamage = new DrawDamage(player.getClientID(), cardsString);

        return Serializer.serializeData(drawDamage);
    }

    /**
     * The method serializes the Energy protocol
     *
     * @param clientID: int: client id
     * @param count:    int count of energy cubes
     * @param source:   String source of energy cubes
     * @return: String
     */
    public static String serializeEnergy(int clientID, int count, String source) {
        Energy energy = new Energy(clientID, count, source);

        return Serializer.serializeData(energy);
    }

    /**
     * The method serialize the GameFinished protocol
     *
     * @param clientID: int: client id
     * @return: String
     */
    public static String serializeGameFinished(int clientID) {
        GameFinished gameFinished = new GameFinished(clientID);

        return Serializer.serializeData(gameFinished);
    }

    /**
     * The method serialize the Movement protocol
     *
     * @param clientID: int: client id
     * @param position: Position: position
     * @return: String
     */
    public static String serializeMovement(int clientID, Position position) {

        Movement movement = new Movement(clientID, position.getX(), position.getY());

        return Serializer.serializeData(movement);
    }

    public static String serializePickDamage(int count, ArrayList<String> availablePiles) {

        PickDamage pickDamage = new PickDamage(count, availablePiles);

        return Serializer.serializeData(pickDamage);
    }

    /**
     * The method serializes the PlayerTurning protocol
     *
     * @param clientID: int: client id
     * @param rotation: String: rotation
     * @return: String
     */
    public static String serializePlayerTurning(int clientID, String rotation) {
        PlayerTurning playerTurning = new PlayerTurning(clientID, rotation);

        return Serializer.serializeData(playerTurning);
    }

    /**
     * The method serializes the Reboot protocol
     *
     * @param clientID: int: client id
     * @return: String
     */
    public static String serializeReboot(int clientID) {
        Reboot reboot = new Reboot(clientID);

        return Serializer.serializeData(reboot);
    }

    public static String serializeRebootDirection(String direction) {
        RebootDirection rebootDirection = new RebootDirection(direction);

        return Serializer.serializeData(rebootDirection);
    }

    public static String serializeSelectedDamage(ArrayList<String> cards) {
        SelectedDamage selectedDamage = new SelectedDamage(cards);

        return Serializer.serializeData(selectedDamage);
    }


//cards

    /**
     * The method serialize the CardPlayed protocol
     *
     * @param playCard:      PlayCard protocol
     * @param clientHandler: ClientHandler: clientHandler
     * @return: String
     */
    public static String serializeCardPlayed(PlayCard playCard, ClientHandler clientHandler) {

        CardPlayed cardPlayed = new CardPlayed(clientHandler.getClientID(), playCard.getCard());

        return Serializer.serializeData(cardPlayed);
    }

    public static String serializePlayCard(Card card) {

        PlayCard playCard = new PlayCard(card.toString());

        return Serializer.serializeData(playCard);
    }

    //chat

    /**
     * The method serializes the ReceivedChat protocol
     *
     * @param sendChat:      SendChat: sendChat
     * @param clientHandler: ClientHandler: clientHandler
     * @return: String
     */
    public static String serializeReceivedChat(SendChat sendChat, ClientHandler clientHandler) {

        ReceivedChat receivedChat =
                new ReceivedChat(sendChat.getMessage(), clientHandler.getClientID(), sendChat.getTo() != -1);

        return Serializer.serializeData(receivedChat);
    }

    /**
     * The method serializes the SendChat protocol
     *
     * @param message: String: message
     * @param to:      int: client id of receiver
     * @return: String
     */
    public static String serializeSendChat(String message, int to) {
        SendChat sendChat = new SendChat(message, to);

        return Serializer.serializeData(sendChat);

    }

    /**
     * The method serializes the Alive protocol
     *
     * @return: String
     */
    //connection
    public static String serializeAlive() {
        Alive alive = new Alive();
        return Serializer.serializeData(alive);
    }

    /**
     * The method serializes the HelloClient protocol
     *
     * @param protocol: String: protocolVersion
     * @return: String
     */
    public static String serializeHelloClient(String protocol) {

        HelloClient helloClient = new HelloClient(protocol);

        return Serializer.serializeData(helloClient);
    }

    /**
     * The method serializes the HelloServer protocol
     *
     * @param group:    String: group name
     * @param isAI:     boolean: isAi
     * @param protocol: String protocol version
     * @return String
     */
    public static String serializeHelloServer(String group, boolean isAI, String protocol) {

        HelloServer helloServer = new HelloServer(group, isAI, protocol);

        return Serializer.serializeData(helloServer);
    }

    /**
     * The method serialize the Welcome protocol
     *
     * @param clientID: int: clientId
     * @return: String
     */
    public static String serializeWelcome(int clientID) {
        Welcome welcome = new Welcome(clientID);

        return Serializer.serializeData(welcome);
    }


    //lobby

    /**
     * The method serialize the GameStarted protocol
     *
     * @param gameMap: List<List<List<BoardElement>>>: gameMap
     * @return: String
     */
    public static String serializeGameStarted(List<List<List<BoardElement>>> gameMap) {

        GameStarted gameStarted = new GameStarted(MapWrapping.buildJsonFromMap(gameMap));

        return Serializer.serializeData(gameStarted);
    }

    /**
     * The method serializes the MapSelected protocol
     *
     * @param mapEnum: MapEnum: map Enum
     * @return: String
     */
    public static String serializeMapSelected(MapEnum mapEnum) {
        MapSelected mapSelected = new MapSelected(mapEnum.getName());
        return Serializer.serializeData(mapSelected);
    }

    /**
     * The method serializes the PlayerAdded protocol
     *
     * @param player: added Player
     * @return: String
     */
    public static String serializePlayerAdded(Player player) {
        int clientID = player.getClientID();
        String name = player.getUsername();
        int figure = player.getRobotEnum().getNumber();

        PlayerAdded playerAdded = new PlayerAdded(clientID, name, figure);

        return Serializer.serializeData(playerAdded);

    }

    /**
     * The method serializes the PlayerValues protocol
     *
     * @param name:      String: username
     * @param robotEnum: RobotEnum: robot
     * @return: String
     */
    public static String serializePlayerValues(String name, RobotEnum robotEnum) {

        PlayerValues playerValues = new PlayerValues(name, robotEnum.getNumber());

        return Serializer.serializeData(playerValues);
    }

    /**
     * The method serializes the PlayerStatus protocol
     *
     * @param setStatus:     SetStatus protocol
     * @param clientHandler: ClientHandler: clientHandler
     * @return: String
     */
    public static String serializePlayerStatus(SetStatus setStatus, ClientHandler clientHandler) {
        PlayerStatus playerStatus = new PlayerStatus(clientHandler.getClientID(), setStatus.isReady());

        return Serializer.serializeData(playerStatus);
    }

    /**
     * The method serializes the SelectMap protocol
     *
     * @return String
     */
    public static String serializeSelectMap() {
        SelectMap selectMap = new SelectMap();
        return Serializer.serializeData(selectMap);
    }

    /**
     * The method serializes the SetStatus protocol
     *
     * @param ready: boolean: ready
     * @return: String
     */
    public static String serializeSetStatus(boolean ready) {
        SetStatus setStatus = new SetStatus(ready);


        return Serializer.serializeData(setStatus);
    }

    /**
     * The method serialize the ActivePhase protocol
     *
     * @param game: current Game
     * @return: String
     */
    //round
    public static String serializeActivePhase(Game game) {
        ActivePhase activePhase = new ActivePhase(game.getActivePhase().getID());
        return Serializer.serializeData(activePhase);
    }

    /**
     * The method serialize the CurrentPlayer protocol
     *
     * @param game: current Game
     * @return: String
     */
    public static String serializeCurrentPlayer(Game game) {

        CurrentPlayer currentPlayer = new CurrentPlayer(game.getCurrentPlayer().getClientID());
        return Serializer.serializeData(currentPlayer);
    }


    //activation_phase

    /**
     * The method serializes the CurrentCards
     *
     * @param players:  ArrayList<Player>: players
     * @param register: int: register
     * @return: String
     */
    public static String serializeCurrentCards(ArrayList<Player> players, int register) {
        HashMap<Integer, String> registerCards = new HashMap<>();
        for (Player player : players) {
            Card card = (Card) Array.get(player.getPlayerMat().getRegister(), register);
            registerCards.put(player.getClientID(), card.toString());
        }

        CurrentCards currentCards = new CurrentCards(registerCards);

        return Serializer.serializeData(currentCards);
    }

    /**
     * The method serialize the ReplaceCard protocol
     *
     * @param player:   Player: player
     * @param register: int register
     * @return: String
     */
    public static String serializeReplaceCard(Player player, int register) {

        Card card = (Card) Array.get(player.getPlayerMat().getRegister(), register);


        ReplaceCard replaceCard = new ReplaceCard(register, card.toString(), player.getClientID());


        return Serializer.serializeData(replaceCard);
    }

    /**
     * The method serialize the CardSelected protocol
     *
     * @param player:   Player: player
     * @param card:     String card
     * @param register: int: register number
     * @return: String
     */
    //programming_phase
    public static String serializeCardSelected(Player player, String card, int register) {
        boolean filled;
        filled = card != null;
        CardSelected cardSelected = new CardSelected(player.getClientID(), register, filled);

        return Serializer.serializeData(cardSelected);
    }

    /**
     * The method serializes the CardsYouGotNow protocol
     *
     * @param cards: ArrayList<Card>: cards
     * @return: String
     */
    public static String serializeCardsYouGotNow(ArrayList<Card> cards) {

        ArrayList<String> cardsString = CardWrapping.cardListToStringArrayList(cards);

        CardsYouGotNow cardsYouGotNow = new CardsYouGotNow(cardsString);

        return Serializer.serializeData(cardsYouGotNow);
    }

    /**
     * The method serializes the NotYourCards protocol
     *
     * @param player: current Player
     * @return: String
     */
    public static String serializeNotYourCards(Player player) {
        NotYourCards notYourCards = new NotYourCards(player.getClientID(), player.getCardsInHand().size());
        return Serializer.serializeData(notYourCards);
    }

    /**
     * The method serializes the SelectedCard protocol
     *
     * @param card:     String: card
     * @param register: int: register number
     * @return: String
     */
    public static String serializeSelectedCard(String card, int register) {

        SelectedCard selectedCard = new SelectedCard(card, register);

        return Serializer.serializeData(selectedCard);
    }

    /**
     * The method serializes the SelectionFinished protocol
     *
     * @param player: current Player
     * @return: String
     */
    public static String serializeSelectionFinished(Player player) {
        SelectionFinished selectionFinished = new SelectionFinished(player.getClientID());

        return Serializer.serializeData(selectionFinished);
    }

    /**
     * The method serializes the ShuffleCoding protocol
     *
     * @param player: current Player
     * @return: String
     */
    public static String serializeShuffleCoding(Player player) {
        ShuffleCoding shuffleCoding = new ShuffleCoding(player.getClientID());
        return Serializer.serializeData(shuffleCoding);
    }

    /**
     * The method serializes the TimerEnded protocol
     *
     * @param players: ArrayList<Player>: players
     * @return: String
     */
    public static String serializeTimerEnded(ArrayList<Player> players) {
        ArrayList<Integer> clientIDs = new ArrayList<>();
        for (Player player : players) {
            clientIDs.add(player.getClientID());
        }

        TimerEnded timerEnded = new TimerEnded(clientIDs);

        return Serializer.serializeData(timerEnded);
    }

    /**
     * The method serializes the TimerStarted protocol
     *
     * @return: String
     */
    public static String serializeTimerStarted() {
        TimerStarted timerStarted = new TimerStarted();

        return Serializer.serializeData(timerStarted);
    }

    /**
     * The method serializes YourCards protocol
     *
     * @param cardsInHand: Deck<Card> cardsInHand
     * @return: String
     */
    public static String serializeYourCards(Deck<Card> cardsInHand) {

        ArrayList<String> stringCardsInHand = CardWrapping.cardListToStringArrayList(cardsInHand);

        YourCards yourCards = new YourCards(stringCardsInHand);
        return Serializer.serializeData(yourCards);
    }

    //setup_phase

    /**
     * The method serializes teh protocol SetStartingPoint
     *
     * @param x : int x coordinate
     * @param y : int y coordinate
     * @return String
     */
    public static String serializeSetStartingPoint(int x, int y) {
        SetStartingPoint setStartingPoint = new SetStartingPoint(x, y);
        return Serializer.serializeData(setStartingPoint);
    }

    /**
     * The method serializes the protocol StartingPointTaken
     *
     * @param setStartingPoint : SetStartingPoint protocol
     * @param clientHandler    : ClientHandler
     * @return String
     */
    public static String serializeStartingPointTaken(SetStartingPoint setStartingPoint, ClientHandler clientHandler) {
        //Server Seite. Koordinaten.

        StartingPointTaken startingPointTaken = new StartingPointTaken(setStartingPoint.getX(), setStartingPoint.getY(), clientHandler.getClientID());

        return Serializer.serializeData(startingPointTaken);
    }


    //upgrade_phase


    //special messages

    /**
     * The method serializes the protocol Error
     *
     * @param messagesError : MessagesError specific error messages
     * @return String
     */
    public static String serializeError(MessagesError messagesError) {
        Error error = new Error(messagesError.getMessage());

        return Serializer.serializeData(error);
    }

    /**
     * The method serializes the protocol ConnectionUpdate
     *
     * @param clientID    : int clientID
     * @param isConnected : boolean isConnected
     * @param action      : String action
     * @return String
     */
    public static String serializeConnectionUpdate(int clientID, boolean isConnected, String action) {
        ConnectionUpdate connectionUpdate = new ConnectionUpdate(clientID, isConnected, action);

        return Serializer.serializeData(connectionUpdate);
    }


}
