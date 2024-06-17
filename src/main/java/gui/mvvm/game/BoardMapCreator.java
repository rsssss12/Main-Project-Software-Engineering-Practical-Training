package gui.mvvm.game;

import gamelogic.Direction;
import gamelogic.Position;
import gamelogic.boardElements.*;
import gamelogic.maps.BoardMap;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static gamelogic.Direction.*;
import static java.lang.Math.abs;

/**
 * The class BoardMapCreator creates the grid and fills it
 * with the images of the board elements of the corresponding map
 *
 * @author Tim Kriegelsteiner
 */
public class BoardMapCreator {

    private final Logger logger = LogManager.getLogger(BoardMapCreator.class.getName());

    /**
     * The method creates the grid for the map
     *
     * @param gridPane       : GridPane grid for map
     * @param isNotRobotGrid : boolean not robot grid
     * @param map            : List<List<List<BoardElement>>> map
     * @param pane           : Pane pane
     */
    public void createGrid(GridPane gridPane, boolean isNotRobotGrid, List<List<List<BoardElement>>> map, Pane pane) {
        gridPane.setGridLinesVisible(true);

        int columnCount = map.size();
        int rowCount = map.get(0).size();

        int rowNumber = 0;
        for (List<List<BoardElement>> row : map) {

            gridPane.addRow(rowNumber);

            int columnNumber = 0;
            for (List<BoardElement> column : row) {

                gridPane.addColumn(rowNumber);

                StackPane stackPane = new StackPane();

                stackPane.prefHeightProperty().bind(pane.heightProperty().divide(rowCount));
                stackPane.prefWidthProperty().bind(pane.widthProperty().divide(columnCount));

                gridPane.add(stackPane, rowNumber, columnNumber, 1, 1);

                int emptyBackGround = 0;
                for (BoardElement boardElement : column) {

                    if (isNotRobotGrid) {

                        if (emptyBackGround == 0) {
                            ImageView imageView = new ImageView();
                            imageView.setImage(getImageFromPathJAR("gui/Images/board_elements/empty_field.png"));
                            imageView.fitHeightProperty().bind(pane.heightProperty().divide(rowCount));
                            imageView.fitWidthProperty().bind(pane.widthProperty().divide(columnCount));
                            stackPane.getChildren().add(imageView);
                            emptyBackGround++;
                        }

                        ImageView imageView;

                        imageView = getBoardElementImage(boardElement);

                        imageView.fitHeightProperty().bind(pane.heightProperty().divide(rowCount));
                        imageView.fitWidthProperty().bind(pane.widthProperty().divide(columnCount));

                        stackPane.getChildren().add(imageView);


                    } else {

                        if (boardElement instanceof StartPoint) {
                            ImageView imageView;

                            imageView = getBoardElementImage(boardElement);

                            imageView.fitHeightProperty().bind(pane.heightProperty().divide(rowCount));
                            imageView.fitWidthProperty().bind(pane.widthProperty().divide(columnCount));

                            stackPane.getChildren().add(imageView);

                        }

                        ImageView imageView = new ImageView();
                        imageView.fitHeightProperty().bind(pane.heightProperty().divide(rowCount));
                        imageView.fitWidthProperty().bind(pane.widthProperty().divide(columnCount));
                        stackPane.getChildren().add(imageView);

                        break;
                    }
                }
                columnNumber++;
            }
            rowNumber++;
        }
    }

    /**
     * The method sets the rows and columns of the grid
     *
     * @param gridPane : GridPane grid
     */
    public void setGridPaneLayout(GridPane gridPane) {
        int rowCount = gridPane.getRowCount();
        int columnCount = gridPane.getColumnCount();

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100d / rowCount);

        for (int i = 0; i < rowCount; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100d / columnCount);

        for (int i = 0; i < columnCount; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    /**
     * The method gets the index of one grid pane
     *
     * @param x    : int x
     * @param yMax : int yMax
     * @param y    : int y
     * @return int : index
     */
    public int getGridPaneIndex(int x, int yMax, int y) {

        return x * yMax + y + 1;
    }

    /**
     * The method loads an image from a path
     *
     * @param path : String path to image
     * @return Image : image
     */
    public Image getImageFromPathJAR(String path) {
        InputStream inputStream = BoardMapCreator.class.getClassLoader().getResourceAsStream(path);

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream));
        Image image = new Image(inputStream);

        return image;
    }

    /**
     * The method returns the position of the index
     *
     * @param index : int index
     * @param yMax  : int yMax
     * @return Position : position
     */
    public Position getPositionFromGridPaneIndex(int index, int yMax) {
        int x = index / yMax;
        int y = index % yMax - 1;

        return new Position(x, y);
    }

    /**
     * The method sets the image of a certain boardElement
     *
     * @param boardElement : BoardElement type
     * @return ImageView : image of the element
     */
    public ImageView getBoardElementImage(BoardElement boardElement) {

        Image image = getImageFromPathJAR("gui/Images/board_elements/empty_field.png");
        ImageView imageView = new ImageView(image);


        switch (BoardElementEnum.valueOf(boardElement.getType().toUpperCase())) {

            case ANTENNA -> {
                Antenna antenna = (Antenna) boardElement;
                ArrayList<String> orientations = antenna.getOrientations();
                String orientation = orientations.get(0);

                image = getImageFromPathJAR("gui/Images/board_elements/antenna.png");
                imageView.setImage(image);
                rotateBoardElementImage(orientation, imageView);

            }
            case CHECKPOINT -> {
                CheckPoint checkPoint = (CheckPoint) boardElement;

                switch (checkPoint.getCount()) {
                    case 1 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint.png");
                    case 2 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint2.png");
                    case 3 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint3.png");
                    case 4 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint4.png");
                    case 5 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint5.png");
                    case 6 ->
                            image = getImageFromPathJAR("gui/Images/board_elements/checkpoint6.png");
                }

                imageView.setImage(image);

            }
            case CONVEYORBELT -> {
                ConveyorBelt conveyorBelt = (ConveyorBelt) boardElement;
                ArrayList<String> orientations = conveyorBelt.getOrientations();

                if (orientations.size() == 2) {
                    image = getConveyorBeltType2Way(conveyorBelt);
                } else {
                    image = getConveyorBeltType3Way(conveyorBelt);
                }

                imageView.setImage(image);
                rotateBoardElementImage(orientations.get(0), imageView);

            }
            case EMPTY -> {
                image = getImageFromPathJAR("gui/Images/board_elements/empty_field.png");
                imageView.setImage(image);
            }
            case ENERGYSPACE -> {
                image = getImageFromPathJAR("gui/Images/board_elements/energy_space_green.png");
                imageView.setImage(image);

            }
            case GEAR -> {
                Gear gear = (Gear) boardElement;
                String orientation = gear.getOrientations().get(0);

                if (orientation.equals("clockwise")) {
                    image = getImageFromPathJAR("gui/Images/board_elements/gears_clockwise.png");
                    imageView.setImage(image);
                } else {
                    image = getImageFromPathJAR("gui/Images/board_elements/gears_counterclockwise.png");
                    imageView.setImage(image);
                }

            }
            case LASER -> {
                Laser laser = (Laser) boardElement;
                int count = laser.getCount();
                ArrayList<String> orientations = laser.getOrientations();
                String orientation = laser.getOrientations().get(0);

                switch (count) {
                    case 1 -> {
                        image = getImageFromPathJAR("gui/Images/board_elements/laser_single.png");
                        imageView.setImage(image);
                    }
                    case 2 -> {
                        image = getImageFromPathJAR("gui/Images/board_elements/laser_double.png");
                        imageView.setImage(image);
                    }
                    case 3 -> {
                        image = getImageFromPathJAR("gui/Images/board_elements/laser_triple.png");
                        imageView.setImage(image);
                    }
                }
                imageView.setViewOrder(-1);
                rotateBoardElementImage(orientation, imageView);
            }
            case PIT -> {
                image = getImageFromPathJAR("gui/Images/board_elements/pit.png");
                imageView.setImage(image);
            }
            case PUSHPANEL -> {
                PushPanel pushPanel = (PushPanel) boardElement;
                ArrayList<Integer> registers = pushPanel.getRegisters();
                ArrayList<String> orientations = pushPanel.getOrientations();
                String orientation = pushPanel.getOrientations().get(0);

                if (registers.contains(1)) {
                    image = getImageFromPathJAR("gui/Images/board_elements/push_panel_uneven.png");
                    imageView.setImage(image);
                } else {
                    image = getImageFromPathJAR("gui/Images/board_elements/push_panel_even.png");
                    imageView.setImage(image);
                }
                rotateBoardElementImage(orientation, imageView);
            }

            case RESTARTPOINT -> {
                RestartPoint restartPoint = (RestartPoint) boardElement;
                String orientation = restartPoint.getOrientations().get(0);

                image = getImageFromPathJAR("gui/Images/damage_and_reboots/rebooting.png");
                imageView.setImage(image);

                rotateBoardElementImage(orientation, imageView);
            }
            case STARTPOINT -> {
                image = getImageFromPathJAR("gui/Images/board_elements/starting_point_u2w2016.png");
                imageView.setImage(image);
            }
            case WALL -> {
                Wall wall = (Wall) boardElement;
                getWallImage(wall, imageView);
            }
            default -> {
                imageView.setImage(null);
            }
        }
        return imageView;
    }

    /**
     * The method gets the right conveyor belt 3 image
     *
     * @param conveyorBelt : ConveyorBelt
     * @return Image : image
     */
    public Image getConveyorBeltType3Way(ConveyorBelt conveyorBelt) {
        List<String> orientations = conveyorBelt.getOrientations();
        Direction orientation0 = Direction.valueOf(orientations.get(0).toUpperCase());
        Direction orientation1 = Direction.valueOf(orientations.get(1).toUpperCase());
        Direction orientation2 = Direction.valueOf(orientations.get(2).toUpperCase());

        boolean hasClockwisePart = orientation0.hasClockwisePart(orientation1, orientation2);
        boolean hasCounterClockwisePart = orientation0.hasCounterClockwisePart(orientation1, orientation2);
        boolean hasOppositePart = orientation0.hasOppositePart(orientation1, orientation2);

        String path = "";

        if (hasClockwisePart && hasCounterClockwisePart) {

            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_double_180_u2w2016.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_double_180_u2w2016.png";
            }

        } else if (hasClockwisePart && hasOppositePart) {

            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_double_90_right_u2w2016.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_double_90_right_u2w2016.png";
            }

        } else if (hasCounterClockwisePart && hasOppositePart) {

            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_double_90_u2w2016.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_double_90_u2w2016.png";
            }

        }

        return getImageFromPathJAR(path);
    }

    /**
     * The method gets the right conveyor belt 2 image
     *
     * @param conveyorBelt : ConveyorBelt
     * @return Image : image
     */
    public Image getConveyorBeltType2Way(ConveyorBelt conveyorBelt) {
        List<String> orientations = conveyorBelt.getOrientations();
        Direction orientation0 = Direction.valueOf(orientations.get(0).toUpperCase());
        Direction orientation1 = Direction.valueOf(orientations.get(1).toUpperCase());


        boolean isClockwise = orientation0.isClockwise(orientation1);
        boolean isCounterClockwise = orientation0.isCounterClockwise(orientation1);
        boolean isOpposite = orientation0.isOpposite(orientation1);

        String path = "";

        if (isClockwise) {
            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_turn_right_u2w2016.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_turn_right_u2w2016.png";
            }

        } else if (isOpposite) {
            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_up.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_up.png";
            }

        } else if (isCounterClockwise) {
            if (conveyorBelt.getSpeed() == 1) {
                path = "gui/Images/board_elements/conveyor_belt_green_turn_left_u2w2016.png";
            } else {
                path = "gui/Images/board_elements/conveyor_belt_blue_turn_left_u2w2016.png";
            }
        }

        return getImageFromPathJAR(path);
    }

    /**
     * The method gets the double wall
     *
     * @param wall : Wall
     * @return String : direction of wall
     */
    public String getDoubleWall(Wall wall) {
        String wall1 = wall.getOrientations().get(0).toUpperCase();
        String wall2 = wall.getOrientations().get(1).toUpperCase();

        Direction direction1 = Direction.valueOf(wall1);
        Direction direction2 = Direction.valueOf(wall2);

        boolean left = (direction1.equals(TOP) && direction2.equals(LEFT)) || (direction2.equals(TOP) && direction1.equals(LEFT));
        boolean top = (direction1.equals(TOP) && direction2.equals(RIGHT)) || (direction2.equals(TOP) && direction1.equals(RIGHT));
        boolean right = (direction1.equals(BOTTOM) && direction2.equals(RIGHT)) || (direction2.equals(BOTTOM) && direction1.equals(RIGHT));
        boolean bottom = (direction1.equals(BOTTOM) && direction2.equals(LEFT)) || (direction2.equals(BOTTOM) && direction1.equals(LEFT));

        if (left) {
            return "left";
        } else if (right) {
            return "right";
        } else if (top) {
            return "top";
        } else if (bottom) {
            return "bottom";
        }

        return null;
    }


    /**
     * The method rotates the image of the board element
     *
     * @param orientation : String orientation
     * @param imageView   : ImageView old
     * @return ImageView : rotated image
     */
    public ImageView rotateBoardElementImage(String orientation, ImageView imageView) {
        switch (Direction.valueOf(orientation.toUpperCase())) {

            case TOP -> {
            }
            case BOTTOM -> imageView.setRotate(180);
            case LEFT -> imageView.setRotate(270);
            case RIGHT -> imageView.setRotate(90);
        }

        return imageView;
    }

    /**
     * The method rotates the robot image
     *
     * @param directionOld : old Direction
     * @param directionNew : new Direction
     * @param imageView    : old ImageView
     * @return ImageView : rotated image
     */
    public ImageView rotateRobotStatic(Direction directionOld, Direction directionNew, ImageView imageView) {
        switch (directionNew) {

            case TOP -> {
                switch (directionOld) {
                    case LEFT -> {
                        imageView.setRotationAxis(new Point3D(0, 0, 1));
                        imageView.setRotate(90);
                    }
                    case RIGHT -> {
                        imageView.setRotationAxis(new Point3D(1, 1, 0));
                        imageView.setRotate(180);
                    }
                }
            }
            case BOTTOM -> {
                switch (directionOld) {
                    case LEFT -> {
                        imageView.setRotationAxis(new Point3D(0, 0, 1));
                        imageView.setRotate(-90);
                    }
                    case RIGHT -> {
                        imageView.setRotationAxis(new Point3D(-1, 1, 0));
                        imageView.setRotate(180);
                    }
                }
            }
            case LEFT -> {
                imageView.setRotate(0);
            }
            case RIGHT -> {
                imageView.setRotationAxis(Rotate.Y_AXIS);
                imageView.setRotate(180);
            }
        }
        return imageView;
    }

    /**
     * The method determines the start board side
     *
     * @param startPoints : List<StartPoint> startPoints
     * @param columnCount : int columnCount
     * @param rowCount    : int rowCount
     * @return Direction : direction of start board
     */
    public Direction determineStartBoardSide(List<StartPoint> startPoints, int columnCount, int rowCount) {

        startPoints.sort(Comparator.comparing(StartPoint::getX));

        int highestX = startPoints.get(startPoints.size() - 1).getX();
        int lowestX = startPoints.get(0).getX();

        startPoints.sort(Comparator.comparing(StartPoint::getY));

        int highestY = startPoints.get(startPoints.size() - 1).getY();
        int lowestY = startPoints.get(0).getY();


        if (highestX < 3) {
            return LEFT;
        } else if (highestY < 3) {
            return TOP;
        } else if (lowestX > columnCount - 3) {
            return RIGHT;
        } else if (lowestY > rowCount - 3) {
            return BOTTOM;
        }
        return null;

    }

    /**
     * The method adds the lasers to the grid
     *
     * @param map      : List<List<List<BoardElement>>> map
     * @param gridPane : GridPane grid
     */
    public void addLaserBeamsToGrid(List<List<List<BoardElement>>> map, GridPane gridPane) {

        List<Laser> lasers = BoardMap.getBoardElementList(map, new Laser("", new ArrayList<>(), 1));

        Position laserRange = new Position();

        for (Laser laser : lasers) {
            switch (Direction.valueOf(laser.getOrientations().get(0).toUpperCase())) {

                case TOP -> followTheLaser("y", -1, TOP, map, laser, gridPane);

                case BOTTOM -> followTheLaser("y", 1, BOTTOM, map, laser, gridPane);

                case LEFT -> followTheLaser("x", -1, LEFT, map, laser, gridPane);

                case RIGHT -> followTheLaser("x", 1, RIGHT, map, laser, gridPane);

            }
        }
    }

    /**
     * The method determines where the laser goes
     *
     * @param coordinate : String coordinate
     * @param way        : int way
     * @param direction  : Direction of laser
     * @param map        : List<List<List<BoardElement>>> map
     * @param laser      : Laser
     * @param gridPane   : GridPane grid
     */
    public void followTheLaser(String coordinate, int way, Direction direction, List<List<List<BoardElement>>> map, Laser laser, GridPane gridPane) {

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

        logger.debug("laser.getX(): " + laser.getX());
        logger.debug("laser.getY(): " + laser.getY());

        logger.debug("coord dyn: " + coordinateDynamic);
        logger.debug("coord stat: " + coordinateStatic);

        logger.debug("endOfLine: " + endOfLine);

        for (; coordinateDynamic < endOfLine; coordinateDynamic++) {
            List<BoardElement> elementsInPosition;

            if (coordinate.equals("x")) {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateDynamic), abs(coordinateStatic));
            } else {
                elementsInPosition = BoardMap.getElementsInPosition(map, abs(coordinateStatic), abs(coordinateDynamic));
            }

            assert elementsInPosition != null;
            for (BoardElement boardElement : elementsInPosition) {

                logger.debug("boardElement instanceof Wall: " + (boardElement instanceof Wall));

                if (boardElement instanceof Wall wall) {

                    for (String orientation : wall.getOrientations()) {

                        Direction orientation1 = valueOf(orientation.toUpperCase());

                        logger.debug("direction Laser:" + direction);
                        logger.debug("direction of Wall orientation: " + orientation1);

                        logger.debug("direction.equals(orientation1): " + direction.equals(orientation1));

                        if (direction.equals(orientation1)) {

                            if (coordinate.equals("x")) {
                                lastLaserPositionX = abs(coordinateDynamic);
                                lastLaserPositionY = abs(coordinateStatic);
                            } else {
                                lastLaserPositionX = abs(coordinateStatic);
                                lastLaserPositionY = abs(coordinateDynamic);
                            }

                            fillingTileWithLaserBeam(laser, new Position(lastLaserPositionX, lastLaserPositionY), gridPane, true, wall);

                            return;

                        }
                        if (direction.isOpposite(orientation1)) {
                            return;
                        }
                    }
                }

                if (elementsInPosition.get(elementsInPosition.size() - 1).equals(boardElement)) {
                    if (coordinate.equals("x")) {
                        lastLaserPositionX = abs(coordinateDynamic);
                        lastLaserPositionY = abs(coordinateStatic);
                    } else {
                        lastLaserPositionX = abs(coordinateStatic);
                        lastLaserPositionY = abs(coordinateDynamic);
                    }

                    fillingTileWithLaserBeam(laser, new Position(lastLaserPositionX, lastLaserPositionY), gridPane, false, new Wall("", new ArrayList<>()));
                }
            }
        }
    }

    /**
     * The method fills the grid pane with the laser beam
     *
     * @param laser      : Laser
     * @param laserRange : Position of laser
     * @param gridPane   : GridPane grid
     * @param plusWall   : boolean with wall
     * @param wall       : Wall
     */
    public void fillingTileWithLaserBeam(Laser laser, Position laserRange, GridPane gridPane, boolean plusWall, Wall wall) {
        int count = laser.getCount();
        String orientation = laser.getOrientations().get(0);

        int gridPaneIndex = getGridPaneIndex(laserRange.getX(), gridPane.getRowCount(), laserRange.getY());

        Pane pane = (Pane) gridPane.getParent();

        StackPane stackPane = (StackPane) gridPane.getChildren().get(gridPaneIndex);
        ImageView imageView = new ImageView();

        switch (count) {
            case 1 ->
                    imageView.setImage(getImageFromPathJAR("gui/Images/board_elements/laser_beam_single.png"));
            case 2 ->
                    imageView.setImage(getImageFromPathJAR("gui/Images/board_elements/laser_beam_double.png"));
            case 3 ->
                    imageView.setImage(getImageFromPathJAR("gui/Images/board_elements/laser_beam_triple.png"));
        }

        rotateBoardElementImage(orientation, imageView);

        imageView.fitHeightProperty().bind(pane.heightProperty().divide(gridPane.getRowCount()));
        imageView.fitWidthProperty().bind(pane.widthProperty().divide(gridPane.getColumnCount()));
        stackPane.getChildren().add(imageView);

        if (plusWall) {
            ImageView imageViewWall = new ImageView();
            getWallImage(wall, imageViewWall);

            imageViewWall.fitHeightProperty().bind(pane.heightProperty().divide(gridPane.getRowCount()));
            imageViewWall.fitWidthProperty().bind(pane.widthProperty().divide(gridPane.getColumnCount()));
            stackPane.getChildren().add(imageViewWall);
        }
    }

    /**
     * The method gets wall image
     *
     * @param wall      : Wall
     * @param imageView : ImageView image
     */
    public void getWallImage(Wall wall, ImageView imageView) {
        ArrayList<String> orientations = wall.getOrientations();
        Image image;

        if (orientations.size() == 1) {
            image = getImageFromPathJAR("gui/Images/board_elements/wall_single_up.png");
            imageView.setImage(image);
            rotateBoardElementImage(orientations.get(0), imageView);

        } else {

            image = getImageFromPathJAR("gui/Images/board_elements/wall_double_up_right_alpha.png");
            imageView.setImage(image);
            rotateBoardElementImage(getDoubleWall(wall), imageView);
        }
    }

    /**
     * The method shows the coordinates of the grid
     *
     * @param hBox     : HBox
     * @param vBox     : VBox
     * @param gridPane : GridPane grid
     */
    public void getAxis(HBox hBox, VBox vBox, GridPane gridPane) {
        int columnCount = gridPane.getColumnCount();
        int rowCount = gridPane.getRowCount();

        for (int i = 0; i < columnCount; i++) {
            Label label = new Label();
            label.setText(Integer.toString(i));

            label.prefHeightProperty().bind(hBox.heightProperty());
            label.prefWidthProperty().bind(hBox.widthProperty().divide(columnCount));

            label.getStyleClass().add("axis");

            hBox.getChildren().add(label);

        }

        for (int i = 0; i < rowCount; i++) {
            Label label = new Label();
            label.setText(Integer.toString(i));

            label.prefWidthProperty().bind(vBox.widthProperty());
            label.prefHeightProperty().bind(vBox.heightProperty().divide(rowCount));

            label.getStyleClass().add("axis");

            vBox.getChildren().add(label);
        }

    }

    public void getAxisTile(TilePane tileX, TilePane tileY, GridPane gridPane) {
        int columnCount = gridPane.getColumnCount();
        int rowCount = gridPane.getRowCount();

        tileX.setPrefColumns(columnCount);
        tileY.setPrefRows(rowCount);

        tileX.setPrefTileWidth(tileX.getWidth() / columnCount);
        tileY.setPrefTileHeight(tileY.getHeight() / rowCount);

        for (int i = 0; i < columnCount; i++) {

            Label label = new Label();
            label.setText(Integer.toString(i));
            tileX.getChildren().add(label);
        }

        for (int i = 0; i < rowCount; i++) {
            Label label = new Label();
            label.setText(Integer.toString(i));
            tileY.getChildren().add(label);
        }
    }

}

