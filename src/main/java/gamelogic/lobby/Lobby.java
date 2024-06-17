package gamelogic.lobby;

import gamelogic.Effects;
import gamelogic.Player;
import gamelogic.RobotEnum;
import gamelogic.boardElements.BoardElement;
import gamelogic.maps.BoardMap;
import gamelogic.maps.MapEnum;
import gamelogic.round.Game;
import json.json_wrappers.lobby.GameStarted;
import json.json_wrappers.lobby.MapSelected;
import json.json_wrappers.lobby.PlayerValues;
import json.json_wrappers.lobby.SetStatus;
import json.wrapper_utilities.MapWrapping;
import json.wrapper_utilities.WrapperClassSerialization;
import network.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static messages.MessagesError.PLAYERADDED_ERROR;

/**
 * The class Lobby contains the logic for the lobby view
 *
 * @author Tim Kriegelsteiner
 */
public class Lobby {

    private final Logger logger = LogManager.getLogger(Lobby.class.getName());

    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<RobotEnum> robotsTaken = new ArrayList<>();
    private final ArrayList<Player> playersReady = new ArrayList<>();

    private MapEnum chosenMap;

    private Player chooserOfMap;

    private Game game = new Game();

    /**
     * The method handles the protocol PlayerValues and sends the answer protocol PlayerAdded
     *
     * @param playerValues  : PlayerValues protocol
     * @param clientHandler : ClientHandler to distribute answer
     */
    public void handlePlayerValues(PlayerValues playerValues, ClientHandler clientHandler) {
        RobotEnum robotEnum = RobotEnum.getEnumFromNumber(playerValues.getFigure());

        if (!isRobotTaken(robotEnum)) {

            Player player = addPlayer(clientHandler.getClientID(), playerValues.getName(), playerValues.getFigure());

            player.setClientHandler(clientHandler);

            String json = WrapperClassSerialization.serializePlayerAdded(player);

            clientHandler.sendMessageToAllClients(json);

        } else {

            String json = WrapperClassSerialization.serializeError(PLAYERADDED_ERROR);

            clientHandler.sendMessageToSelf(json);
        }
    }

    /**
     * The method creates a new Player with the information of PlayerValues
     *
     * @param clientID    : int clientID
     * @param username    : String username
     * @param robotNumber : int robotNumber
     * @return Player : new Player
     */
    public Player addPlayer(int clientID, String username, int robotNumber) {

        Player player = new Player();
        player.setUsername(username);

        player.setRobotEnum(RobotEnum.getEnumFromNumber(robotNumber));

        robotsTaken.add(RobotEnum.getEnumFromNumber(robotNumber));
        player.setClientID(clientID);
        players.add(player);

        return player;
    }

    /**
     * The method checks if robot is already taken
     *
     * @param robotEnum : RobotEnum all robots
     * @return boolean : true if robot is taken
     */
    public boolean isRobotTaken(RobotEnum robotEnum) {

        return robotsTaken.contains(robotEnum);
    }

    /**
     * The method checks if game is ready to start
     *
     * @return boolean : true if ready
     */
    public boolean isGameReadyToBeStarted() {

        return chosenMap != null && playersReady.size() > 1;
    }


    /**
     * The method adds a player to ready players
     *
     * @param player : Player new ready player
     */
    public void increasePlayersReady(Player player) {
        playersReady.add(player);

        if (playersReady.size() == 1) {
            setChooserOfMap();
        }
    }

    /**
     * The method removes a player from ready players
     *
     * @param player : Player not ready
     */
    public void decreasePlayersReady(Player player) {
        playersReady.remove(player);

        if (playersReady.size() != 0) {
            setChooserOfMap();
        }
    }

    /**
     * The method handles the protocol SetStatus and answers with PlayerStatus protocol
     *
     * @param setStatus     : SetStatus protocol if ready
     * @param clientHandler : ClientHandler
     */
    public void handleSetStatus(SetStatus setStatus, ClientHandler clientHandler) {

        int clientID = clientHandler.getClientID();

        Player player = getPlayerByClientID(clientID);

        String json = WrapperClassSerialization.serializePlayerStatus(setStatus, clientHandler);
        clientHandler.sendMessageToAllClients(json);

        if (setStatus.isReady()) {
            increasePlayersReady(player);
        } else {
            decreasePlayersReady(player);
        }

    }

    /**
     * The method takes the clientID and returns the corresponding player
     *
     * @param clientID : int clientID
     * @return Player : corresponding player
     */
    public Player getPlayerByClientID(int clientID) {
        Player searchedPlayer = new Player();
        for (Player player : players) {
            if (player.getClientID() == clientID) {
                searchedPlayer = player;
            }
        }
        return searchedPlayer;
    }

    /**
     * The method decides who chooses the map and sends the protocol SelectMap
     */
    public void setChooserOfMap() {
        chooserOfMap = playersReady.get(0);

        String json = WrapperClassSerialization.serializeSelectMap();
        chooserOfMap.getClientHandler().sendMessageToSelf(json);
    }

    /**
     * The method handles the protocol MapSelected
     *
     * @param mapSelected   : MapSelected protocol
     * @param clientHandler : ClientHandler
     */
    public void handleMapSelected(MapSelected mapSelected, ClientHandler clientHandler) {
        String map = mapSelected.getMap();

        chosenMap = MapEnum.valueOf(map.toUpperCase());

        if (isGameReadyToBeStarted()) {
            initializeGame(clientHandler);
        }
    }

    /**
     * The method initializes the game
     *
     * @param clientHandler : ClientHandler
     */
    public void initializeGame(ClientHandler clientHandler) {
        game.setPlayers(players);
        game.getBoard().getMatsFromPlayers(players);

        String pathToFile = chosenMap.getPath();

        logger.debug(chosenMap);
        logger.debug(chosenMap.getPath());


        //Gamelogic Map

        GameStarted gameStarted = MapWrapping.readFileToGameStarted(pathToFile);

        List<List<List<BoardElement>>> racingCourse = MapWrapping.buildMapFromJson(gameStarted);
        game.getBoard().setMap(racingCourse);
        BoardMap.applyPositionToElementInMap(racingCourse);
        game.initializeAntenna();
        game.determineCheckpointCount();
        Effects effects = game.getActivationPhase().getEffects();

        effects.fillTileTypeLists(game.getBoard().getMap());
        effects.fillCompleteLasers(game.getBoard().getMap());
        game.setGameRunning(true);

        sendGameStarted(racingCourse, clientHandler);
        Game.delayingTime(10000);

        game.startGame(clientHandler);

    }

    /**
     * The method sends the protocol GameStarted
     *
     * @param map           : List<List<List<BoardElement>>> chosen map
     * @param clientHandler : ClientHandler
     */
    public void sendGameStarted(List<List<List<BoardElement>>> map, ClientHandler clientHandler) {
        String json = WrapperClassSerialization.serializeGameStarted(map);

        clientHandler.sendMessageToAllClients(json);
    }

    /**
     * The method removes a Player with a certain clientID
     *
     * @param clientID : int clientID
     */
    public void removePlayer(int clientID) {
        for (Player player : players) {
            if (player.getClientID() == clientID) {
                players.remove(player);
                robotsTaken.remove(player.getRobotEnum());
                playersReady.clear();

                break;
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
