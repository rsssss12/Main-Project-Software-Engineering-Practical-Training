package gamelogic;


import gamelogic.maps.BoardMap;
import gamelogic.boardElements.*;
import gamelogic.round.Game;
import json.wrapper_utilities.WrapperClassSerialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static gamelogic.Direction.*;
import static java.lang.Math.abs;

/**
 * The class Effects contains all effects of the board element activation and of the robot
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public class Effects {

    private final Logger logger = LogManager.getLogger(Effects.class.getName());

    private ArrayList<EnergySpace> energySpaceList = new ArrayList<>();
    private ArrayList<ConveyorBelt> conveyorBeltList = new ArrayList<>();
    private final ArrayList<ConveyorBelt> blueConveyorBelts = new ArrayList<>();
    private final ArrayList<ConveyorBelt> greenConveyorBelts = new ArrayList<>();
    private ArrayList<Gear> gearList = new ArrayList<>();
    private ArrayList<PushPanel> pushPanelList = new ArrayList<>();
    private ArrayList<Laser> laserList = new ArrayList<>();
    private ArrayList<CheckPoint> checkPointList = new ArrayList<>();
    private final ArrayList<Position> laserBeamList = new ArrayList<>();
    private final ArrayList<ArrayList<BoardElement>> completeLasers = new ArrayList<>();
    private final ArrayList<ArrayList<Position>> robotLasers = new ArrayList<>();


    public Effects() {

    }

    /**
     * The method fills one ArrayList with the greenConveyorBelts and
     * the other ArrayList with the blue ones
     */
    public void fillConveyorLists() {
        for (ConveyorBelt conveyorBelt : conveyorBeltList) {
            if (conveyorBelt.getSpeed() == 1) {
                greenConveyorBelts.add(conveyorBelt);
            } else {
                blueConveyorBelts.add(conveyorBelt);
            }
        }
    }

    /**
     * The method activates the conveyorBelt
     *
     * @param game         : current Game
     * @param player       : current Player
     * @param conveyorBelt : ConveyorBelt boardElement
     */
    public void activateConveyorBelt(Game game, Player player, ConveyorBelt conveyorBelt) {
        ArrayList<Player> players = game.getPlayers();

        ConveyorBelt currentConveyorBelt = conveyorBelt;
        Direction currentConveyorBeltDirection = Direction.valueOf(currentConveyorBelt.getOrientations().get(0).toUpperCase());

        Robot robot = player.getRobot();

        int speed = conveyorBelt.getSpeed();

        for (int i = 0; i < speed; i++) {
            Position nextPosition = determineNextPosition(robot.getPosition(), currentConveyorBeltDirection);
            robot.setMovingDirection(currentConveyorBeltDirection);

            logger.debug("player: " + player.getUsername());
            logger.debug("nextPosition: " + nextPosition.getX() + ", " + nextPosition.getY());
            logger.debug("currentConveyorBeltDirection start of loop: " + currentConveyorBeltDirection);
            logger.debug("currentConveyorBelt start of loop: " + currentConveyorBelt.getX() + ", " + currentConveyorBelt.getY());


            boolean foeInFront = isPositionOccupied(players, nextPosition);
            boolean stillOnBeltNextTime = isStillOnBelt(nextPosition);
            boolean stillOnBelt = isStillOnBelt(robot.getPosition());

            if (foeInFront && !stillOnBeltNextTime) {
                return;
            }


            if (i > 0) {
                if (!stillOnBelt) {
                    return;
                }
                currentConveyorBelt = getElementByBoardPosition(conveyorBeltList, robot.getPosition());
                currentConveyorBeltDirection = Direction.valueOf(currentConveyorBelt.getOrientations().get(0).toUpperCase());

                logger.debug("currentConveyorBeltDirection middle 2nd of loop: " + currentConveyorBeltDirection);
                logger.debug("currentConveyorBelt middle 2nd of loop: " + currentConveyorBelt.getX() + ", " + currentConveyorBelt.getY());

                if (!isMovementStraight(conveyorBelt, currentConveyorBelt)) {

                    String rotation = determineRotation(conveyorBelt, currentConveyorBelt);

                    rotate(player, rotation);

                    robot.setMovingDirection(currentConveyorBeltDirection);

                }
            }

            logger.debug("RobotPosition before move: " + robot.getPosition().getX() + " + " + robot.getPosition().getY());

            move(game, player);
            robot.setMovingDirection(robot.getDirection());

            logger.debug("RobotPosition after move: " + robot.getPosition().getX() + " + " + robot.getPosition().getY());
        }
    }

    /**
     * The method sends the protocol Movement
     *
     * @param player : current Player
     */
    public void sendMovement(Player player) {
        Position position = player.getRobot().getPosition();
        String json = WrapperClassSerialization.serializeMovement(player.getClientID(), position);
        player.getClientHandler().sendMessageToAllClients(json);
        Game.delayingTime(3000);
    }

    /**
     * The method sends the protocol PlayerTurning
     *
     * @param player   : current Player
     * @param rotation : String rotate direction
     */
    public void sendPlayerTurning(Player player, String rotation) {
        String json = WrapperClassSerialization.serializePlayerTurning(player.getClientID(), rotation);
        player.getClientHandler().sendMessageToAllClients(json);
        Game.delayingTime(3000);
    }

    /**
     * The method activates the push panel
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param pushPanel PushPanel boardElement
     */
    public void activatePushPanel(Game game, Player player, PushPanel pushPanel) {

        int currentRegister = player.getPlayerMat().getCurrentRegister();

        ArrayList<Integer> registers = pushPanel.getRegisters();
        ArrayList<String> orientations = pushPanel.getOrientations();
        Direction direction = Direction.valueOf(orientations.get(0).toUpperCase());
        player.getRobot().setMovingDirection(direction);

        if (registers.contains(currentRegister)) {
            move(game, player);
            player.getRobot().setMovingDirection(player.getRobot().getDirection());
        }
    }

    /**
     * The method activates the gear
     *
     * @param player : current Player
     * @param gear   : Gear boardElement
     */
    public void activateGear(Player player, Gear gear) {
        String orientation = gear.getOrientations().get(0);
        rotate(player, orientation);
    }

    /**
     * The method activates the board laser
     *
     * @param game  : current Game
     * @param tList : List<T> laser list
     */
    public <T extends BoardElement> void activateBoardLaser(Game game, List<T> tList) {
        ArrayList<Player> players = game.getPlayerOrder();
        for (Player player : players) {
            if (isPlayerInTileTypePosition(player.getRobot().getPosition(), tList)) {
                Position position = player.getRobot().getPosition();
                T t = getElementByBoardPosition(tList, position);
                logger.debug("Laser List: " + tList);
                logger.debug("Laser in first place: " + tList.get(0));


                Laser laser = (Laser) tList.get(0);
                int count = laser.getCount();

                logger.debug("draw damage from BoardLaser");
                game.getBoard().drawDamageCards(player, count);

                break;
            }
        }
    }

    /**
     * The method activates the robot laser
     *
     * @param game : current Game
     */
    public void activateRobotLaser(Game game) {
        ArrayList<Player> playerOrder = game.getPlayerOrder();

        boolean isRobotHit = false;


        for (ArrayList<Position> robotLaser : robotLasers) {
            for (Position position : robotLaser) {
                for (Player player : playerOrder) {
                    Position robotPlayer = player.getRobot().getPosition();
                    Position robotInList = robotLaser.get(0);
                    if (!robotPlayer.equals(robotInList)) {
                        if (robotPlayer.equals(position)) {

                            logger.debug("Draw Damage from robot laser");

                            game.getBoard().drawDamageCards(player, 1);
                            isRobotHit = true;
                            break;
                        }
                    }
                }
                if (isRobotHit) {
                    break;
                }
            }
        }
    }


    /**
     * The method activates the energy space
     *
     * @param game        : current Game
     * @param player      : current Player
     * @param energySpace : EnergySpace boardElement
     */
    public void activateEnergySpace(Game game, Player player, EnergySpace energySpace) {

        int currentRegister = player.getPlayerMat().getCurrentRegister();

        if ((energySpace.getCount() > 0)) {
            energySpace.setCount(energySpace.getCount() - 1);
            player.getPlayerMat().setEnergyReserve(player.getPlayerMat().getEnergyReserve() + 1);
            energySpace.setCount(0);
            sendEnergy(player, "EnergySpace", 1);
        }

        if (currentRegister == 4) {
            player.getPlayerMat().setEnergyReserve(player.getPlayerMat().getEnergyReserve() + 1);
            sendEnergy(player, "EnergySpace", 1);
        }


    }

    /**
     * The method sends the protocol Energy
     *
     * @param player : current Player
     * @param source : String source of energy
     * @param count  : int amount of energy
     */
    public void sendEnergy(Player player, String source, int count) {

        String json = WrapperClassSerialization.serializeEnergy(player.getClientID(), count, source);

        player.getClientHandler().sendMessageToAllClients(json);

        Game.delayingTime(3000);
    }


    /**
     * The method activates the checkpoint
     *
     * @param player     : current Player
     * @param checkPoint : CheckPoint boardElement
     */
    public void activateCheckpoint(Player player, CheckPoint checkPoint) {
        PlayerMat playerMat = player.getPlayerMat();

        if (playerMat.getCheckpointsReached() > checkPoint.getCount()) {
            logger.debug("Player was on this checkpoint before");
            return;
        }

        if (playerMat.getCheckpointsReached() == (checkPoint.getCount() - 1)) {
            playerMat.setCheckpointsReached(checkPoint.getCount());
            logger.debug("Player reached right checkpoint");
            sendCheckPointReached(player, checkPoint);
        }

        if (playerMat.getCheckpointsReached() == checkPointList.size()) {
            logger.debug("Player reached all checkpoints");
        }
    }

    /**
     * The method sends the protocol CheckPointReached
     *
     * @param player     : current Player
     * @param checkPoint : CheckPoint boardElement
     */
    public void sendCheckPointReached(Player player, CheckPoint checkPoint) {

        String json = WrapperClassSerialization.serializeCheckPointReached(player.getClientID(), checkPoint.getCount());

        player.getClientHandler().sendMessageToAllClients(json);

        Game.delayingTime(3000);
    }


    //ConveyorBeltChecks

    /**
     * The method checks if two conveyorBelts line up straight
     *
     * @param conveyorBeltOld : ConveyorBelt old
     * @param conveyorBeltNew : ConveyorBelt new
     * @return boolean : true if they are straight
     */
    public boolean isMovementStraight(ConveyorBelt conveyorBeltOld, ConveyorBelt conveyorBeltNew) {

        return conveyorBeltOld.getOrientations().get(0).equals(conveyorBeltNew.getOrientations().get(0));
    }

    /**
     * The method checks if a position is on the conveyorBelt
     *
     * @param position : Position new
     * @return boolean : true if it is
     */
    public boolean isStillOnBelt(Position position) {

        for (ConveyorBelt conveyorBelt : conveyorBeltList) {
            if (new Position(conveyorBelt.getX(), conveyorBelt.getY()).equals(position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * The method determines in which direction the conveyorBelt rotates something
     *
     * @param conveyorBeltOld : ConveyorBelt old
     * @param conveyorBeltNew : ConveyorBelt new
     * @return String : rotation
     */
    public String determineRotation(ConveyorBelt conveyorBeltOld, ConveyorBelt conveyorBeltNew) {
        String orientationOld = conveyorBeltOld.getOrientations().get(0);
        Direction directionOld = Direction.valueOf(orientationOld.toUpperCase());

        String orientationNew = conveyorBeltNew.getOrientations().get(0);
        Direction directionNew = Direction.valueOf(orientationNew.toUpperCase());

        if (directionOld.isClockwise(directionNew)) {
            return "clockwise";
        }

        return "counterclockwise";
    }

    /**
     * The method checks if there is a wall
     *
     * @param game      : current Game
     * @param position  : Position of robot
     * @param direction : Direction of robot
     * @return boolean : true if there is a wall
     */
    public boolean isWall(Game game, Position position, Direction direction) {
        Effects effects = new Effects();

        ArrayList<Wall> wallList = BoardMap.getBoardElementList(game.getBoard().getMap(), new Wall("", new ArrayList<>()));

        boolean isWallNext = false;
        boolean isWallCurrent = false;

        Position nextPosition = determineNextPosition(position, direction);

        if (effects.isPlayerInTileTypePosition(position, wallList)) {
            Wall wall = effects.getElementByBoardPosition(wallList, position);
            ArrayList<String> orientations = wall.getOrientations();
            Direction direction1 = Direction.valueOf(orientations.get(0).toUpperCase());
            Direction direction2 = direction1;
            if (orientations.size() > 1) {
                direction2 = Direction.valueOf(orientations.get(0).toUpperCase());
            }


            isWallCurrent = direction.equals(direction1) || direction.equals(direction2);
        }

        if (effects.isPlayerInTileTypePosition(nextPosition, wallList)) {
            Wall wall = effects.getElementByBoardPosition(wallList, nextPosition);
            ArrayList<String> orientations = wall.getOrientations();
            Direction direction1 = Direction.valueOf(orientations.get(0).toUpperCase());
            Direction direction2 = direction1;
            if (orientations.size() > 1) {
                direction2 = Direction.valueOf(orientations.get(0).toUpperCase());
            }

            isWallNext = direction.hasOppositePart(direction1, direction2);

        }


        return isWallNext || isWallCurrent;
    }


    //RebootChecks

    /**
     * The method checks if a position is already occupied from robot
     *
     * @param players  : ArrayList<Player>
     * @param position : Position to check
     * @return boolean : true if occupied
     */
    public boolean isPositionOccupied(ArrayList<Player> players, Position position) {
        for (Player player : players) {
            if (isSamePosition(player.getRobot().getPosition(), position.getX(), position.getY())) {
                return true;
            }
        }

        return false;
    }

    /**
     * The method returns the robot who is occupying a position
     *
     * @param players  : ArrayList<Player> players
     * @param position : Position to check
     * @return Player : occupier
     */
    public Player getPositionOccupier(ArrayList<Player> players, Position position) {
        for (Player player : players) {
            if (isSamePosition(player.getRobot().getPosition(), position.getX(), position.getY())) {
                return player;
            }
        }

        return null;
    }

    /**
     * The method checks if the player has to reboot next
     * when there is a pit or off the board
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param direction : Direction of robot
     * @return boolean : true if reboot is nest
     */
    public boolean isRebootingNext(Game game, Player player, Direction direction) {

        return isPitNext(game, player, direction) || isOffBoardNext(game, player, direction);
    }

    /**
     * The method checks if there is a pit on next position
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param direction : Direction of robot
     * @return boolean : true if pit is next
     */
    public boolean isPitNext(Game game, Player player, Direction direction) {
        ArrayList<Pit> pits = BoardMap.getBoardElementList(game.getBoard().getMap(), new Pit(""));
        Position position = determineNextPosition(player.getRobot().getPosition(), direction);
        return isPlayerInTileTypePosition(position, pits);
    }

    /**
     * The method checks if there is off board on next position
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param direction : Direction of robot
     * @return boolean : true if off board is next
     */
    public boolean isOffBoardNext(Game game, Player player, Direction direction) {

        Position position = determineNextPosition(player.getRobot().getPosition(), direction);

        int columnCount = game.getBoard().getMap().size();
        int rowCount = game.getBoard().getMap().get(0).size();

        boolean isNegative = position.getX() < 0 || position.getY() < 0;
        boolean isOutOfBounds = position.getX() >= columnCount || position.getY() >= rowCount;
        boolean isNull = false;
        if (!isNegative && !isOutOfBounds) {
            isNull = game.getBoard().getMap().get(position.getX()).get(position.getY()) == null;
        }

        return isNegative || isOutOfBounds || isNull;
    }

    /**
     * The method checks if there is a collision next
     * Collision occurs if there is an antenna or a wall
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param direction : Direction of robot
     * @return boolean : true if there is collision next
     */
    public boolean isCollisionNext(Game game, Player player, Direction direction) {

        return isAntennaNext(game, player, direction) || isWall(game, player.getRobot().getPosition(), direction);
    }

    /**
     * The method checks if there is the antenna next
     *
     * @param game      : current Game
     * @param player    : current Player
     * @param direction : Direction of robot
     * @return boolean : true if there is antenna
     */
    public boolean isAntennaNext(Game game, Player player, Direction direction) {
        ArrayList<Antenna> antennas = BoardMap.getBoardElementList(game.getBoard().getMap(), new Antenna("", new ArrayList<>()));
        Position position = determineNextPosition(player.getRobot().getPosition(), direction);

        return isPlayerInTileTypePosition(position, antennas);
    }

    //Robot: LaserCheck isPlayerInTileTypePosition(...RestartPoint...)

    /**
     * The method gets an element by a certain position
     *
     * @param list     : List<T>
     * @param position : Position
     * @return T extends BoardElement : element of this position
     */
    public <T extends BoardElement> T getElementByBoardPosition(List<T> list, Position position) {

        for (T t : list) {
            if (isSamePosition(position, t.getX(), t.getY())) {
                return t;
            }
        }
        return null;
    }

    //General

    /**
     * The method compares to positions and checks if equal
     *
     * @param position : Position of robot
     * @param x        : int x coordinate
     * @param y        : int y coordinate
     * @return boolean : true if it is same position
     */
    public boolean isSamePosition(Position position, int x, int y) {
        int robotX = position.getX();
        int robotY = position.getY();
        return robotX == x && robotY == y;
    }

    /**
     * The method checks if a robot is in tile type position
     *
     * @param position         : Position
     * @param boardElementList : List<T>
     * @return booelan : true if in position
     */
    public <T extends BoardElement> boolean isPlayerInTileTypePosition(Position position, List<T> boardElementList) {

        for (BoardElement boardElement : boardElementList) {
            if (isSamePosition(position, boardElement.getX(), boardElement.getY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The map fills the ArrayLists with one type of boardElements
     *
     * @param map : List<List<List<BoardElement>>> map
     */
    public void fillTileTypeLists(List<List<List<BoardElement>>> map) {
        conveyorBeltList = BoardMap.getBoardElementList(map, new ConveyorBelt("", new ArrayList<>(), 1));
        fillConveyorLists();
        pushPanelList = BoardMap.getBoardElementList(map, new PushPanel("", new ArrayList<>(), new ArrayList<>()));
        checkPointList = BoardMap.getBoardElementList(map, new CheckPoint("", 0));
        energySpaceList = BoardMap.getBoardElementList(map, new EnergySpace("", 0));
        laserList = BoardMap.getBoardElementList(map, new Laser("", new ArrayList<>(), 1));
        gearList = BoardMap.getBoardElementList(map, new Gear("", new ArrayList<>()));

    }

    /**
     * The method determines the next position
     *
     * @param position  : current Position
     * @param direction : current Direction
     * @return Position : next Position
     */
    public Position determineNextPosition(Position position, Direction direction) {

        Position newPosition = new Position();

        switch (direction) {

            case TOP -> {
                newPosition.setY(position.getY() - 1);
                newPosition.setX(position.getX());
            }
            case BOTTOM -> {
                newPosition.setY(position.getY() + 1);
                newPosition.setX(position.getX());
            }
            case LEFT -> {
                newPosition.setX(position.getX() - 1);
                newPosition.setY(position.getY());

            }
            case RIGHT -> {
                newPosition.setX(position.getX() + 1);
                newPosition.setY(position.getY());
            }
        }

        return newPosition;
    }

    /**
     * The method sends the protocol Animation
     *
     * @param game : current Game
     * @param type : String type of animation
     */
    public void sendAnimation(Game game, String type) {
        String json = WrapperClassSerialization.serializeAnimation(type);
        game.getPlayers().get(0).getClientHandler().sendMessageToAllClients(json);
        Game.delayingTime(2000);
    }


    /**
     * The method activates the boardElements in the right order
     *
     * @param game : current Game
     */
    public void activateBoardElements(Game game) {

        sendAnimation(game, "GreenConveyorBelt");
        activateBoardElement(game, greenConveyorBelts);

        sendAnimation(game, "BlueConveyorBelt");
        activateBoardElement(game, blueConveyorBelts);

        twisterHandling(game.getBoard().getMap());

        sendAnimation(game, "PushPanel");
        activateBoardElement(game, pushPanelList);

        sendAnimation(game, "Gear");
        activateBoardElement(game, gearList);

        sendAnimation(game, "WallShooting");
        activateCompleteLasers(game);

        sendAnimation(game, "PlayerShooting");
        calculateRobotLasers(game);


        activateRobotLaser(game);

        robotLasers.clear();


        sendAnimation(game, "EnergySpace");
        activateBoardElement(game, energySpaceList);
        activateBoardElement(game, checkPointList);

    }

    public void printOutRobotLasers() {
        int laserList = 0;
        for (ArrayList<Position> robotLaser : robotLasers) {
            int finalLaserList = laserList;
            robotLaser.stream().
                    forEach(position -> logger.debug(finalLaserList + ": " + robotLaser.indexOf(position) + ": x: " + position.getX() + ", y: " + position.getY()));

            laserList++;
        }
    }

    /**
     * The method activates the boardElements
     *
     * @param game  : current Game
     * @param tList : List<T>
     */
    public <T extends BoardElement> void activateBoardElement(Game game, List<T> tList) {
        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            if (isPlayerInTileTypePosition(player.getRobot().getPosition(), tList)) {
                Position position = player.getRobot().getPosition();
                T t = getElementByBoardPosition(tList, position);

                chooseBoardTypeActivation(game, player, t);
                if (game.isGameOver()) {
                    return;
                }
            }
        }
    }

    /**
     * The method determines which boardElement is activated
     *
     * @param game   : current Game
     * @param player : current Player
     * @param t      : T tilename
     */
    public <T extends BoardElement> void chooseBoardTypeActivation(Game game, Player player, T t) {

        String tileName = t.getType().toUpperCase();

        switch (BoardElementEnum.valueOf(tileName)) {

            case CONVEYORBELT -> {
                ConveyorBelt conveyorBelt = (ConveyorBelt) t;
                activateConveyorBelt(game, player, conveyorBelt);
            }
            case PUSHPANEL -> {
                PushPanel pushPanel = (PushPanel) t;
                activatePushPanel(game, player, pushPanel);
            }
            case GEAR -> {
                Gear gear = (Gear) t;
                activateGear(player, gear);
            }
            case ENERGYSPACE -> {
                EnergySpace energySpace = (EnergySpace) t;
                activateEnergySpace(game, player, energySpace);
            }
            case CHECKPOINT -> {
                CheckPoint checkPoint = (CheckPoint) t;
                activateCheckpoint(player, checkPoint);
            }
        }
    }


    //Laser

    /**
     * The method fills the laserList
     *
     * @param map : List<List<List<BoardElement>>> map
     */
    public void fillCompleteLasers(List<List<List<BoardElement>>> map) {
        for (Laser laser : laserList) {

            ArrayList<BoardElement> completeLaser = new ArrayList<>();
            completeLaser.add(laser);

            completeLasers.add(completeLaser);
        }

        calculateLaserBeam(map);
    }

    /**
     * The method activates the lasers
     *
     * @param game : current Game
     */
    public void activateCompleteLasers(Game game) {
        for (ArrayList<BoardElement> completeLaser : completeLasers) {
            activateBoardLaser(game, completeLaser);
        }
    }

    /**
     * The method calculates the orientation of the laser beam
     *
     * @param map : List<List<List<BoardElement>>> map
     */
    public void calculateLaserBeam(List<List<List<BoardElement>>> map) {

        for (Laser laser : laserList) {
            switch (Direction.valueOf(laser.getOrientations().get(0).toUpperCase())) {

                case TOP -> followTheLaser("y", -1, TOP, map, laser);
                case BOTTOM -> followTheLaser("y", 1, BOTTOM, map, laser);
                case LEFT -> followTheLaser("x", -1, LEFT, map, laser);
                case RIGHT -> followTheLaser("x", 1, RIGHT, map, laser);
            }
        }

    }

    /**
     * The method calculates the way of the laser beam
     *
     * @param coordinate : String coordinate
     * @param way        : int way
     * @param direction  : Direction of laser
     * @param map        : ist<List<List<BoardElement>>> map
     * @param laser      : Laser boardElement
     */
    public void followTheLaser(String coordinate, int way, Direction direction, List<List<List<BoardElement>>> map, Laser laser) {

        int lastLaserPositionX;
        int lastLaserPositionY;

        int coordinateDynamic = laser.getY() + 1;
        int coordinateStatic = laser.getX();

        int endOfLine = 1;

        if (coordinate.equals("x")) {
            coordinateDynamic = laser.getX() + 1;
            coordinateStatic = laser.getY();
        }
        if (coordinate.equals("x") && way == 1) {
            endOfLine = map.size() - 1;
        }
        if (coordinate.equals("y") && way == 1) {
            endOfLine = map.get(0).size() - 1;
        }
        if (way == -1) {
            coordinateDynamic = -coordinateDynamic + 2;
            coordinateStatic = -coordinateStatic;
        }


        for (; coordinateDynamic < endOfLine; coordinateDynamic++) {
            List<BoardElement> elementsInPosition = new ArrayList<>();

            if (coordinate.equals("x")) {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateDynamic), abs(coordinateStatic));
            } else {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateStatic), abs(coordinateDynamic));
            }

            assert elementsInPosition != null;
            for (BoardElement boardElement : elementsInPosition) {

                if (coordinate.equals("x")) {
                    lastLaserPositionX = abs(coordinateDynamic);
                    lastLaserPositionY = abs(coordinateStatic);
                } else {
                    lastLaserPositionX = abs(coordinateStatic);
                    lastLaserPositionY = abs(coordinateDynamic);
                }

                assert BoardMap.getElementsInPosition(map, lastLaserPositionX, lastLaserPositionY) != null;
                String isOnBoard = BoardMap.getElementsInPosition(map, lastLaserPositionX, lastLaserPositionY).get(0).getIsOnBoard();
                Empty empty = new Empty(isOnBoard);
                empty.setX(lastLaserPositionX);
                empty.setY(lastLaserPositionY);


                if (boardElement instanceof Wall) {
                    Wall wall = (Wall) boardElement;


                    for (String orientation : wall.getOrientations()) {

                        Direction orientation1 = valueOf(orientation.toUpperCase());

                        if (direction.equals(orientation1)) {


                            laserBeamList.add(new Position(lastLaserPositionX, lastLaserPositionY));

                            for (ArrayList<BoardElement> completeLaser : completeLasers) {
                                if (completeLaser.get(0).equals(laser)) {
                                    completeLaser.add(empty);
                                    break;
                                }
                            }

                            return;

                        }
                        if (direction.isOpposite(orientation1)) {
                            return;
                        }

                    }
                }

                if (elementsInPosition.get(elementsInPosition.size() - 1).equals(boardElement)) {


                    laserBeamList.add(new Position(lastLaserPositionX, lastLaserPositionY));

                    for (ArrayList<BoardElement> completeLaser : completeLasers) {
                        if (completeLaser.get(0).equals(laser)) {
                            completeLaser.add(empty);
                            break;
                        }
                    }
                }
            }
        }
    }


    //RobotLaser

    /**
     * The method calculates the robot lasers
     *
     * @param game : current Game
     */
    public void calculateRobotLasers(Game game) {
        ArrayList<Player> playerOrder = game.getPlayerOrder();

        for (Player player : playerOrder) {

            Position position = player.getRobot().getPosition();
            ArrayList<Position> positionArrayList = new ArrayList<>();
            positionArrayList.add(position);
            robotLasers.add(positionArrayList);

            calculateRobotLaser(game.getBoard().getMap(), player.getRobot(), game);
        }
    }

    /**
     * The method calculates the direction of the robot laser
     *
     * @param map   : List<List<List<BoardElement>>> map
     * @param robot : current Robot
     * @param game  : current Game
     */
    public void calculateRobotLaser(List<List<List<BoardElement>>> map, Robot robot, Game game) {

        switch (robot.getDirection()) {

            case TOP -> followTheRobotLaser("y", -1, TOP, map, robot, game);
            case BOTTOM -> followTheRobotLaser("y", 1, BOTTOM, map, robot, game);
            case LEFT -> followTheRobotLaser("x", -1, LEFT, map, robot, game);
            case RIGHT -> followTheRobotLaser("x", 1, RIGHT, map, robot, game);
        }

    }

    /**
     * The method calculates the way of the robot laser beam
     *
     * @param coordinate : String coordinate
     * @param way        : int way
     * @param direction  : Direction of laser
     * @param map        : List<List<List<BoardElement>>> map
     * @param robot      : current Robot
     * @param game       : current Game
     */
    public void followTheRobotLaser(String coordinate, int way, Direction direction, List<List<List<BoardElement>>> map, Robot robot, Game game) {
        Position robotPosition = robot.getPosition();

        int lastLaserPositionX;
        int lastLaserPositionY;

        int coordinateDynamic = robotPosition.getY();
        int coordinateStatic = robotPosition.getX();

        int endOfLine = 1;

        if (coordinate.equals("x")) {
            coordinateDynamic = robotPosition.getX();
            coordinateStatic = robotPosition.getY();
        }
        if (coordinate.equals("x") && way == 1) {
            endOfLine = map.size() - 1;
        }
        if (coordinate.equals("y") && way == 1) {
            endOfLine = map.get(0).size();
        }
        if (way == -1) {
            coordinateDynamic = -coordinateDynamic;
            coordinateStatic = -coordinateStatic;
        }


        for (; coordinateDynamic < endOfLine; coordinateDynamic++) {
            List<BoardElement> elementsInPosition;

            if (coordinate.equals("x")) {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateDynamic), abs(coordinateStatic));
            } else {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateStatic), abs(coordinateDynamic));
            }

            assert elementsInPosition != null;
            for (BoardElement boardElement : elementsInPosition) {

                if (coordinate.equals("x")) {
                    lastLaserPositionX = abs(coordinateDynamic);
                    lastLaserPositionY = abs(coordinateStatic);
                } else {
                    lastLaserPositionX = abs(coordinateStatic);
                    lastLaserPositionY = abs(coordinateDynamic);
                }

                assert BoardMap.getElementsInPosition(map, lastLaserPositionX, lastLaserPositionY) != null;
                String isOnBoard = BoardMap.getElementsInPosition(map, lastLaserPositionX, lastLaserPositionY).get(0).getIsOnBoard();
                Empty empty = new Empty(isOnBoard);
                empty.setX(lastLaserPositionX);
                empty.setY(lastLaserPositionY);

                Position position = new Position(lastLaserPositionX, lastLaserPositionY);
                if (boardElement instanceof Antenna) {
                    return;
                }

                for (Player player : game.getPlayerOrder()) {
                    Position robotPlayer = player.getRobot().getPosition();

                    if (!robotPosition.equals(robotPlayer)) {
                        if (robotPlayer.equals(position)) {
                            for (ArrayList<Position> robotLaser : robotLasers) {
                                if (robotLaser.get(0).equals(robotPosition)) {
                                    robotLaser.add(position);
                                    break;
                                }
                            }
                            return;
                        }
                    }
                }

                if (boardElement instanceof Wall wall) {


                    for (String orientation : wall.getOrientations()) {

                        Direction orientation1 = valueOf(orientation.toUpperCase());

                        if (direction.equals(orientation1)) {

                            for (ArrayList<Position> robotLaser : robotLasers) {
                                if (robotLaser.get(0).equals(robotPosition)) {
                                    robotLaser.add(position);
                                    break;
                                }
                            }
                            return;
                        }
                        if (direction.isOpposite(orientation1) && robot.getPosition().equals(new Position(lastLaserPositionX, lastLaserPositionY))) {
                            return;
                        }
                    }
                }

                if (elementsInPosition.get(elementsInPosition.size() - 1).equals(boardElement)) {

                    for (ArrayList<Position> robotLaser : robotLasers) {
                        if (robotLaser.get(0).equals(robotPosition)) {
                            robotLaser.add(position);
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * The method moves the robot
     * before that it checks, if it is possible to move
     *
     * @param game   : current Game
     * @param player : current Player
     */
    public void move(Game game, Player player) {
        Position position = player.getRobot().getPosition();
        Direction direction = player.getRobot().getMovingDirection();
        Position nextPosition = determineNextPosition(position, direction);

        if (isRebootingNext(game, player, direction)) {
            player.rebooting(game);
            return;
        }
        if (isCollisionNext(game, player, direction)) {
            return;
        }
        if (isPositionOccupied(game.getPlayers(), nextPosition)) {
            Player occupier = getPositionOccupier(game.getPlayers(), nextPosition);
            occupier.getRobot().setMovingDirection(direction);
            move(game, occupier);
            occupier.getRobot().setMovingDirection(occupier.getRobot().getDirection());
            if (nextPosition.equals(occupier.getRobot().getPosition())) {
                return;
            }
        }


        switch (direction) {

            case TOP -> position.setY(position.getY() - 1);
            case BOTTOM -> position.setY(position.getY() + 1);
            case LEFT -> position.setX(position.getX() - 1);
            case RIGHT -> position.setX(position.getX() + 1);
        }

        sendMovement(player);
    }


    /**
     * The method rotates the robot
     *
     * @param player   : current Player
     * @param rotation : String 90° rotation
     */
    public void rotate(Player player, String rotation) {
        Robot robot = player.getRobot();
        Direction direction = robot.getDirection();

        switch (rotation) {
            case "clockwise" -> robot.setDirection(direction.getClockwise());
            case "counterclockwise" -> robot.setDirection(direction.getCounterClockwise());
        }
        robot.setMovingDirection(robot.getDirection());

        sendPlayerTurning(player, rotation);

    }


    /**
     * The method moves the robot backwards
     *
     * @param game   : current Game
     * @param player : current Player
     */
    public void backUp(Game game, Player player) {
        player.getRobot().setMovingDirection(player.getRobot().getDirection().getOpposite());

        move(game, player);

        player.getRobot().setMovingDirection(player.getRobot().getDirection());

    }


    /**
     * The method handles the special map Twister
     * In this map the checkpoints are moved by the conveyor belts
     *
     * @param map : List<List<List<BoardElement>>> map twister
     */
    public void twisterHandling(List<List<List<BoardElement>>> map) {
        ArrayList<CheckPoint> checkPointList = BoardMap.getBoardElementList(map, new CheckPoint("", 0));
        ArrayList<ConveyorBelt> conveyorBeltList = BoardMap.getBoardElementList(map, new ConveyorBelt("", new ArrayList<>(), 0));

        for (CheckPoint checkPoint : checkPointList) {

            Position checkPointPosition = new Position(checkPoint.getX(), checkPoint.getY());

            for (ConveyorBelt conveyorBelt : conveyorBeltList) {

                ConveyorBelt currentConveyorBelt = conveyorBelt;
                Direction currentConveyorBeltDirection = Direction.valueOf(currentConveyorBelt.getOrientations().get(0).toUpperCase());
                int speed = conveyorBelt.getSpeed();


                if (isSamePosition(checkPointPosition, conveyorBelt.getX(), conveyorBelt.getY())) {
                    for (int i = 0; i < speed; i++) {
                        Position nextPosition = determineNextPosition(checkPointPosition, currentConveyorBeltDirection);

                        //BoardMap.
                        checkPoint.setX(nextPosition.getX());
                        checkPoint.setY(nextPosition.getY());
                        checkPointPosition = new Position(checkPoint.getX(), checkPoint.getY());

                        currentConveyorBelt = getElementByBoardPosition(conveyorBeltList, nextPosition);
                        currentConveyorBeltDirection = Direction.valueOf(currentConveyorBelt.getOrientations().get(0).toUpperCase());

                    }
                    break;
                }
            }

        }
    }


}
