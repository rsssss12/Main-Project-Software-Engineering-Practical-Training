package gamelogic.round;

import gamelogic.Board;
import gamelogic.Player;
import gamelogic.Position;
import gamelogic.boardElements.Antenna;
import gamelogic.boardElements.CheckPoint;
import gamelogic.boardElements.StartPoint;
import gamelogic.lobby.Lobby;
import gamelogic.maps.BoardMap;
import json.json_wrappers.actions_events_effects.PickDamage;
import json.json_wrappers.actions_events_effects.RebootDirection;
import json.wrapper_utilities.WrapperClassSerialization;
import network.ClientHandler;
import network.NetworkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Math.abs;

/**
 * The class Game manages the phases of the game
 *
 * @author Tim Kriegelsteiner
 */
public class Game {

    private final Logger logger = LogManager.getLogger(Game.class.getName());
    private final SetupPhase setupPhase = new SetupPhase(this);
    private Board board = new Board();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> playerOrder = new ArrayList<>();
    private boolean isFirstRoundOver = false;
    private Player currentPlayer;
    private Phase activePhase;
    private Antenna antenna;
    private int checkpointNumber;

    private boolean isGameRunning = false;

    private final ArrayList<Phase> phases = new ArrayList<>();
    private final UpgradePhase upgradePhase = new UpgradePhase(this);
    private final ProgrammingPhase programmingPhase = new ProgrammingPhase(this);
    private final ActivationPhase activationPhase = new ActivationPhase(this);

    /**
     * constructor of Game
     * adds the phases
     */
    public Game() {
        this.phases.add(setupPhase);
        this.phases.add(upgradePhase);
        this.phases.add(programmingPhase);
        this.phases.add(activationPhase);
        skipUpgradePhase();
    }

    /**
     * The method starts the game
     *
     * @param clientHandler : ClientHandler
     */
    public void startGame(ClientHandler clientHandler) {
        startNewRound();
        startNewPhase(clientHandler);
        startNewTurn(clientHandler);
    }

    public void startNewRound() {
    }

    /**
     * The method starts a new phase and sends the ActivePhase
     *
     * @param clientHandler : ClientHandler
     */
    public void startNewPhase(ClientHandler clientHandler) {

        logger.debug("startNewPhase: activePhase: " + activePhase);

        if (activePhase != null && isLastInList(phases, activePhase)) {
            if (isGameOver()) {
                finishGame();
                return;
            } else {
                if (!isFirstRoundOver) {
                    setFirstRoundOver(true);
                }
                startNewRound();
            }
        }
        Phase phase = nextListElementModulo(phases, activePhase);
        if (phase.getID() == 2) {
            phases.remove(setupPhase);
        }
        setActivePhase(phase);
        logger.debug("startNewPhase: new activePhase: " + activePhase);

        sendActivePhase(clientHandler);
        determinePriority();
        phase.start(clientHandler);

    }

    /**
     * The method skips the upgrade phase
     */
    public void skipUpgradePhase() {
        phases.remove(upgradePhase);
    }

    /**
     * The method starts a new turn and sends the current player
     *
     * @param clientHandler : ClientHandler
     */
    public void startNewTurn(ClientHandler clientHandler) {

        if (currentPlayer != null && isLastInList(playerOrder, currentPlayer) && (!activePhase.equals(activationPhase))) {
            startNewPhase(clientHandler);

        } else {
            currentPlayer = nextListElementModulo(playerOrder, currentPlayer);
            sendCurrentPlayer(clientHandler);
        }
    }


    /**
     * The method determines if an object is last in their list
     *
     * @param list   : ArrayList<T> list of objects
     * @param object : T object
     * @return boolean : true if it is last
     */
    public <T> boolean isLastInList(ArrayList<T> list, T object) {

        return object.equals(list.get(list.size() - 1));
    }


    /**
     * The method determines if game is over
     *
     * @return boolean : true if game over
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getPlayerMat().getCheckpointsReached() == checkpointNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method determines the next object in list
     *
     * @param list   : ArrayList<T> list of objects
     * @param object : T object
     * @return T : next object
     */
    public <T> T nextListElementModulo(ArrayList<T> list, T object) {
        if (object == null) {
            return list.get(0);
        } else {
            int nextIndex = (list.indexOf(object) + 1) % (list.size());
            return list.get(nextIndex);
        }
    }

    /**
     * The method determines the player order in the phases
     */
    public void determinePriority() {

        if (activePhase.equals(setupPhase)) {
            playerOrder = players;
        } else {
            playerOrder = compareDistanceToAntenna(players);
        }
    }

    /**
     * The method calculates the distance of a robot to the antenna
     *
     * @param player : current Player
     * @return int : distance to antenna
     */
    public int calculateDistanceToAntenna(Player player) {
        int distance;

        int antennaX = antenna.getX();
        int antennaY = antenna.getY();

        int robotX = player.getRobot().getPosition().getX();
        int robotY = player.getRobot().getPosition().getY();

        int distanceX = abs(antennaX - robotX);
        int distanceY = abs(antennaY - robotY);

        distance = distanceY + distanceX;

        return distance;
    }

    /**
     * The method compares the distance to the antenna with other players
     *
     * @param players : ArrayList<Player> list of players
     * @return ArrayList<Player> sorted list
     */
    public ArrayList<Player> compareDistanceToAntenna(ArrayList<Player> players) {

        ArrayList<Player> sortedPlayers = new ArrayList<>(players);

        for (Player player : players) {
            int distance = calculateDistanceToAntenna(player);
            player.setDistanceToAntenna(distance);
        }

        sortedPlayers.sort(Comparator.comparing(Player::getDistanceToAntenna));


        return sortedPlayers;
    }

    /**
     * The method initializes the antenna board element
     */
    public void initializeAntenna() {
        ArrayList<Antenna> antennas = BoardMap.getBoardElementList(board.getMap(), new Antenna("", new ArrayList<>()));
        this.antenna = antennas.get(0);
    }


    /**
     * The method determines how many checkpoints exist
     */
    public void determineCheckpointCount() {
        ArrayList<CheckPoint> boardElementList = BoardMap.getBoardElementList(board.getMap(), new CheckPoint("", 0));
        checkpointNumber = boardElementList.size();
    }

    /**
     * The method finishes the game
     */
    public void finishGame() {
        Player winner;

        if (players.size() > 1) {
            winner = determineWinner();
        } else {
            winner = players.get(0);
        }

        winner.getClientHandler().getNetworkManager().setLobby(new Lobby());
        sendGameFinished(winner);
        cleanUp();

    }

    /**
     * Clean up the game
     */
    public void cleanUp() {
        NetworkManager networkManager = players.get(0).getClientHandler().getNetworkManager();
        networkManager.setLobby(new Lobby());
        networkManager.setStateOfGame(new ArrayList<>());

    }

    /**
     * The method determines which player is the winner
     *
     * @return Player : winner
     */
    public Player determineWinner() {
        for (Player player : players) {
            if (player.getPlayerMat().getCheckpointsReached() == checkpointNumber) {
                return player;
            }
        }
        return null;
    }

    /**
     * The method sends the protocol ActivePhase
     *
     * @param clientHandler : ClientHandler
     */
    public void sendActivePhase(ClientHandler clientHandler) {
        String json = WrapperClassSerialization.serializeActivePhase(this);
        clientHandler.sendMessageToAllClients(json);
        delayingTime(3000);
    }

    /**
     * The method sends the protocol CurrentPlayer
     *
     * @param clientHandler : ClientHandler
     */
    public void sendCurrentPlayer(ClientHandler clientHandler) {
        String json = WrapperClassSerialization.serializeCurrentPlayer(this);
        clientHandler.sendMessageToAllClients(json);
        delayingTime(3000);
    }

    /**
     * The method sends the protocol GameFinished
     *
     * @param player : Player winner
     */
    public void sendGameFinished(Player player) {
        String json = WrapperClassSerialization.serializeGameFinished(player.getClientID());
        player.getClientHandler().sendMessageToAllClients(json);
        isGameRunning = false;
        delayingTime(3000);
    }

    /**
     * The method implements a delay of time
     *
     * @param time: long time which delay
     */
    public static void delayingTime(long time) {
        try {
            Thread.sleep(time);
//              NetworkManager.getInstance().getLobby().getGame().getPlayers().get(0).getClientHandler().getTimer().wait(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method remove a player and sends the removement to the other players
     *
     * @param clientHandler: ClientHandler
     */
    public void removePlayer(ClientHandler clientHandler) {
        int clientID = clientHandler.getClientID();
        for (Player player : players) {
            if (player.getClientID() == clientID) {
                player.rebooting(this);
                players.remove(player);

                if (player.equals(currentPlayer)) {
                    startNewTurn(clientHandler);
                }
                playerOrder.remove(player);
                for (StartPoint startPoint : setupPhase.getStartPointsTaken()) {
                    if (player.getRobot().getPosition().equals(new Position(startPoint.getX(), startPoint.getY()))) {
                        setupPhase.getStartPointsTaken().remove(startPoint);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void setFirstRoundOver(boolean firstRoundOver) {
        this.isFirstRoundOver = firstRoundOver;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Phase getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(Phase activePhase) {
        this.activePhase = activePhase;
    }

    public SetupPhase getSetupPhase() {
        return setupPhase;
    }

    public Antenna getAntenna() {
        return antenna;
    }

    public void setAntenna(Antenna antenna) {
        this.antenna = antenna;
    }


    public ProgrammingPhase getProgrammingPhase() {
        return programmingPhase;
    }


    public void handlePickDamage(PickDamage pickDamage) {
    }

    public void handleRebootDirection(RebootDirection rebootDirection) {
    }

    public ArrayList<Player> getPlayerOrder() {
        return playerOrder;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public ActivationPhase getActivationPhase() {
        return activationPhase;
    }
}
