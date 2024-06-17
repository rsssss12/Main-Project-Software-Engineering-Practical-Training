package gamelogic.round;

import gamelogic.Direction;
import gamelogic.Robot;
import gamelogic.boardElements.BoardElement;
import gamelogic.maps.BoardMap;
import gamelogic.Player;
import gamelogic.boardElements.StartPoint;
import gui.mvvm.game.BoardMapCreator;
import json.json_wrappers.round.setup_phase.SetStartingPoint;
import json.wrapper_utilities.WrapperClassSerialization;
import messages.MessagesError;
import network.ClientHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The class SetupPhase handles the setup with the start point setting
 *
 * @author Tim Kriegelsteiner
 */
public class SetupPhase extends Phase {

    private final ArrayList<StartPoint> startPointsTaken = new ArrayList<>();

    private ArrayList<StartPoint> startPointsOnMap = new ArrayList<>();

    public SetupPhase(Game game) {
        super(game);
        super.ID = 0;
    }

    /**
     * The method starts the setup phase
     *
     * @param clientHandler : ClientHandler
     */
    @Override
    public void start(ClientHandler clientHandler) {
        startPointsOnMap = BoardMap.getBoardElementList(game.getBoard().getMap(), new StartPoint(""));
    }

    /**
     * The method handles the protocol SetStartingPoint
     *
     * @param setStartingPoint : SetStartingPoint protocol
     * @param clientHandler    : ClientHandler
     */
    public void handleSetStartingPoint(SetStartingPoint setStartingPoint, ClientHandler clientHandler) {
        int x = setStartingPoint.getX();
        int y = setStartingPoint.getY();

        Player player1 = new Player();

        if (isStartPointFree(startPointsTaken, setStartingPoint)) {

            for (Player player : game.getPlayers()) {
                if (clientHandler.getClientID() == player.getClientID()) {
                    player.getRobot().getPosition().setX(x);
                    player.getRobot().getPosition().setY(y);
                    player1 = player;
                }
            }

            for (StartPoint startPoint : startPointsOnMap) {
                if (startPoint.getX() == x && startPoint.getY() == y) {
                    startPointsTaken.add(startPoint);
                    player1.setStartPoint(startPoint);

                    BoardMapCreator boardMapCreator = new BoardMapCreator();
                    List<List<List<BoardElement>>> map = game.getBoard().getMap();
                    int columnCount = map.size();
                    int rowCount = map.get(0).size();
                    Direction startBoardSide = boardMapCreator.determineStartBoardSide(startPointsOnMap, columnCount, rowCount);
                    Robot robot = player1.getRobot();
                    robot.setDirection(startBoardSide.getOpposite());
                    robot.setMovingDirection(startBoardSide.getOpposite());
                    break;
                }
            }

            String json = WrapperClassSerialization.serializeStartingPointTaken(setStartingPoint, clientHandler);
            clientHandler.sendMessageToAllClients(json);

            game.startNewTurn(clientHandler);

        } else {
            String json = WrapperClassSerialization.serializeError(MessagesError.SET_STARTING_POINT_ERROR);
            clientHandler.sendMessageToSelf(json);
        }

    }

    /**
     * The method determines if a start point is still free
     *
     * @param startPointsTaken : ArrayList<StartPoint> start points already taken
     * @param setStartingPoint : SetStartingPoint protocol
     * @return boolean : true if chosen start point is still free
     */
    public boolean isStartPointFree(ArrayList<StartPoint> startPointsTaken, SetStartingPoint setStartingPoint) {
        for (StartPoint startPoint : startPointsTaken) {
            if ((startPoint.getX() == setStartingPoint.getX()) &&
                    (startPoint.getY() == setStartingPoint.getY())) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<StartPoint> getStartPointsTaken() {
        return startPointsTaken;
    }
}
