package gui.mvvm.game;

import gamelogic.Direction;
import gamelogic.Position;
import gamelogic.RobotEnum;
import gamelogic.boardElements.EnergySpace;
import gamelogic.boardElements.StartPoint;
import gamelogic.cards.CardEnum;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import json.wrapper_utilities.WrapperClassSerialization;
import messages.MessagesError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.*;

/**
 * The GameViewModel
 */
public class GameViewModel implements Initializable {

    private final Logger logger = LogManager.getLogger(GameViewModel.class.getName());
    @FXML
    private Label chosenMapLabel;
    @FXML
    private ToggleButton soundButton;
    @FXML
    private Label usernamePlayer1;
    @FXML
    private Label clientIDPlayer1;
    @FXML
    private Label robotPlayer1;
    @FXML
    private HBox infoPlayer1HBox;
    @FXML
    private Label activePhaseLabel;
    @FXML
    private Text usernamePlayer1Text;
    @FXML
    private Text clientIDPlayer1Text;
    @FXML
    private Text usernamePlayer2Text;
    @FXML
    private Text clientIDPlayer2Text;
    @FXML
    private HBox infoPlayer3HBox;
    @FXML
    private Text usernamePlayer3Text;
    @FXML
    private Text clientIDPlayer3Text;
    @FXML
    private HBox infoPlayer4HBox;
    @FXML
    private Text usernamePlayer4Text;
    @FXML
    private Text clientIDPlayer4Text;
    @FXML
    private HBox infoPlayer5HBox;
    @FXML
    private Text usernamePlayer5Text;
    @FXML
    private Text clientIDPlayer5Text;
    @FXML
    private HBox infoPlayer6HBox;
    @FXML
    private Text usernamePlayer6Text;
    @FXML
    private Text clientIDPlayer6Text;
    @FXML
    private HBox infoPlayer2HBox;
    @FXML
    private Label playerClientIDLabel;
    @FXML
    private ImageView ownRobotImage;
    @FXML
    private VBox yAxis;
    @FXML
    private HBox xAxis;
    @FXML
    private Label robotNameLabel;
    @FXML
    private Label register1Text;
    @FXML
    private Label timerText;
    @FXML
    private HBox registerHBox;
    @FXML
    private ImageView cardsInHand1Image;
    @FXML
    private ImageView cardsInHand2Image;
    @FXML
    private ImageView cardsInHand3Image;
    @FXML
    private ImageView cardsInHand4Image;
    @FXML
    private ImageView cardsInHand5Image;
    @FXML
    private ImageView cardsInHand6Image;
    @FXML
    private ImageView cardsInHand7Image;
    @FXML
    private ImageView cardsInHand8Image;
    @FXML
    private ImageView cardsInHand9Image;
    @FXML
    private ImageView register1;
    @FXML
    private ImageView register2;
    @FXML
    private ImageView register3;
    @FXML
    private ImageView register4;
    @FXML
    private ImageView register5;
    @FXML
    private VBox countDisplayVBox;
    @FXML
    private ImageView checkPointIcon;
    @FXML
    private HBox checkpointHBox;
    @FXML
    private Label checkPointCount;
    @FXML
    private HBox energyReserveHBox;
    @FXML
    private ImageView energyReserveIcon;
    @FXML
    private Label energyReserveCount;
    @FXML
    private Label discardPileText;
    @FXML
    private Label programmingDeckText;
    @FXML
    private ImageView discardPileStackCardBack;
    @FXML
    private ImageView programmingDeckCardBack;

    @FXML
    private StackPane register1Stack;
    @FXML
    private StackPane register2Stack;
    @FXML
    private StackPane register3Stack;
    @FXML
    private StackPane register4Stack;
    @FXML
    private StackPane register5Stack;
    @FXML
    private HBox cardsInHandHBox;
    @FXML
    private Pane cardInHand1Pane;
    @FXML
    private Pane cardInHand2Pane;
    @FXML
    private Pane cardInHand3Pane;
    @FXML
    private Pane cardInHand4Pane;
    @FXML
    private Pane cardInHand5Pane;
    @FXML
    private Pane cardInHand6Pane;
    @FXML
    private Pane cardInHand7Pane;
    @FXML
    private Pane cardInHand8Pane;
    @FXML
    private Pane cardInHand9Pane;
    @FXML
    private VBox upgradeCardsVBox;
    @FXML
    private HBox temporaryUpgradeCardsHBox;
    @FXML
    private Pane temporaryUpgradeCard1Pane;
    @FXML
    private Pane temporaryUpgradeCard2Pane;
    @FXML
    private Pane temporaryUpgradeCard3Pane;
    @FXML
    private HBox permanentUpgradeCardHBox;
    @FXML
    private Pane permanentUpgradeCard1Pane;
    @FXML
    private Pane permanentUpgradeCard2Pane;
    @FXML
    private Pane permanentUpgradeCard3Pane;
    @FXML
    private GridPane infoButtonsGridPane;
    @FXML
    private Button chatButton;
    @FXML
    private Button upgradeButton;
    @FXML
    private ImageView speechBubble;
    @FXML
    private Label speechBubbleText;
    @FXML
    private ImageView narwhalImage;
    @FXML
    private Pane mapPane;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label timer;


    private final GridPane racingCourseGridPane = new GridPane();

    private final GridPane robotGrid = new GridPane();
    private final List<StackPane> startingPointsOnGrid = new ArrayList<>();
    /**
     * Get the right registerNumber in which the card in hand was dropped by giving the fx:id of the respective register ImageView.
     * Used to determine in which register the card in Hand was dropped.
     */
    private final Map<String, Integer> registerNumberByImageViewID = new HashMap<>();

    /**
     * Get the right StringProperty of the cards in hand by giving the fx:id of the respective cardInHand ImageView .
     * Used to determine which cards was dragged to register.
     */
    private final Map<String, StringProperty> cardStringByImageViewID = new HashMap<>();
    /**
     * Get the right cardInHand ImageView by giving the respective StringProperty of cardsInHandStringPropertyArrayList.
     * Used to show the right cards in hand.
     */
    private final Map<StringProperty, ImageView> cardInHandImageViewByStringProperty = new HashMap<>();
    private final Map<StringProperty, ImageView> registerImageViewByStringProperty = new HashMap<>();

    private final BoardMapCreator boardMapCreator = new BoardMapCreator();

    private GameModel gameModel;

    public GameViewModel() {

    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        gameModel = GameModel.getInstance();

        loadRacingCourse(racingCourseGridPane, true);
        loadRacingCourse(robotGrid, false);

        boardMapCreator.getAxis(xAxis, yAxis, racingCourseGridPane);

        boardMapCreator.addLaserBeamsToGrid(gameModel.getMap(), racingCourseGridPane);

        addListenerToListElements(gameModel.getPositionsRobots(), positionChangeListener);
        addListenerToListElements(gameModel.getDirectionsRobots(), directionChangeListener);

        addActionsToStartPoints();

        fillRegisterNumberByImage();
        fillCardStringByImage();

        fillCardInHandImageByStringProperty();
        fillRegisterImageViewByStringProperty();

        timer.textProperty().bind(gameModel.getTimerClient().secondsStringProperty());
        timer.disableProperty().bind(gameModel.isTimerRunningProperty().not());
        timerText.disableProperty().bind(gameModel.isTimerRunningProperty().not());
        gameModel.timerHasEndedProperty().addListener(bindingNewTimer);

        checkPointCount.textProperty().bind(gameModel.ownCheckpointsReachedToStringProperty());
        energyReserveCount.textProperty().bind(gameModel.ownEnergyReserveToStringProperty());
        playerNameLabel.textProperty().bind(gameModel.ownPlayerNameProperty());
        robotNameLabel.textProperty().bind(gameModel.ownRobotNameProperty());
        playerClientIDLabel.textProperty().bind(gameModel.ownClientIDProperty());

        bindRegisterFinished();

        speechBubbleText.textProperty().bind(gameModel.eventMessageProperty());

        initializeOwnRobotImage();
        bindInfo();
        gameModel.energySpacesUsedProperty().addListener(energySpaceListener);
        gameModel.activatedTwisterBeltsProperty().addListener(twisterListener);
        gameModel.gameFinishedProperty().addListener(gameFinishedListener);
        chosenMapLabel.textProperty().bind(gameModel.getLobbyModel().selectedMapProperty());

        music();
    }

    MediaPlayer mediaPlayer;

    /**
     * The method plays the music on loop
     */
    public void music() {
//        Media h = new Media(getClass().getResource("/sound/robot-city.mp3").toExternalForm());
        Media h = new Media(getClass().getResource("/sound/Overcooked-2-Soundtrack-Map.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    /**
     * The method utes and unmutes the Sound with button
     *
     * @param actionEvent :ActionEvent
     */
    public void mute(ActionEvent actionEvent) {
        if (soundButton.isSelected()) {
            mediaPlayer.play();
            mediaPlayer.setMute(false);
        } else {
            mediaPlayer.setMute(true);
        }
    }

    /**
     * The method initializes robot image of one self
     */
    public void initializeOwnRobotImage() {
        logger.debug("gameModel.ownPlayerNameProperty().getValue(): " + gameModel.ownPlayerNameProperty().getValue());
        logger.debug("ownPlayerName.equals ' ' " + gameModel.ownPlayerNameProperty().getValue().equals("Spectator"));
        if (!gameModel.ownPlayerNameProperty().getValue().equals("Spectator")) {
            String imagePath = Objects.requireNonNull(RobotEnum.getEnumFromName(gameModel.getOwnRobotName())).getImagePath();
            Image imageFromPathJAR = boardMapCreator.getImageFromPathJAR(imagePath);
            ownRobotImage.setImage(imageFromPathJAR);
            ownRobotImage.setRotationAxis(new Point3D(0, 1, 0));
            ownRobotImage.setRotate(180);
        }

    }

    public void openChat() {

    }

    /**
     * The method loads the grid pane onto the mapPane
     *
     * @param racingCourse   : GridPane map
     * @param isNotRobotGrid : boolean true if not robot grid
     */
    public void loadRacingCourse(GridPane racingCourse, boolean isNotRobotGrid) {

        mapPane.getChildren().add(racingCourse);

        boardMapCreator.setGridPaneLayout(racingCourse);
        boardMapCreator.createGrid(racingCourse, isNotRobotGrid, gameModel.getMap(), mapPane);

    }


    /**
     * The method adds a listener to a list
     *
     * @param list           : List
     * @param changeListener : ChangeListener
     */
    public static <E, T extends Property<E>> void addListenerToListElements(List<T> list, ChangeListener<E> changeListener) {
        for (T t : list) {
            t.addListener(changeListener);
        }
    }

    /**
     * Listener for Position change
     */
    ChangeListener<Position> positionChangeListener = new ChangeListener<Position>() {
        @Override
        public void changed(ObservableValue<? extends Position> observableValue, Position oldPosition, Position newPosition) {

//            logger.debug("Position of Robot has changed");

//            int robotNumber = gameModel.getRobotNumberByPositionProperty(newPosition);


            ObjectProperty<Position> positionObjectProperty = (ObjectProperty<Position>) observableValue;
            int robotNumber = gameModel.getRobotNumberByPositionProperty(positionObjectProperty);
            Integer robotNumberInteger = robotNumber;

            ObjectProperty<Direction> directionObjectProperty = gameModel.getRobotDirectionByRobotNumber().get(robotNumberInteger);

            String path = Objects.requireNonNull(RobotEnum.getEnumFromNumber(robotNumber)).getImagePath();

            Image image = boardMapCreator.getImageFromPathJAR(path);

            int oldChildrenNumber = boardMapCreator.getGridPaneIndex(oldPosition.getX(), robotGrid.getRowCount(), oldPosition.getY());
            int newChildrenNumber = boardMapCreator.getGridPaneIndex(newPosition.getX(), robotGrid.getRowCount(), newPosition.getY());


            StackPane oldStackPane = (StackPane) robotGrid.getChildren().get(oldChildrenNumber);
            StackPane newStackPane = (StackPane) robotGrid.getChildren().get(newChildrenNumber);

            ImageView oldImageView = (ImageView) oldStackPane.getChildren().get(oldStackPane.getChildren().size() - 1);
            ImageView newImageView = (ImageView) newStackPane.getChildren().get(newStackPane.getChildren().size() - 1);


            if (oldImageView.getImage() != null) {

                newImageView.setImage(oldImageView.getImage());
                newImageView.setRotationAxis(oldImageView.getRotationAxis());
                newImageView.setRotate(oldImageView.getRotate());


            } else {
                newImageView.setImage(image);
                logger.debug("Old image is null.");

                newStackPane.getChildren().remove(0);

            }

            if (!oldImageView.equals(newImageView)) {
                oldImageView.setImage(null);
            }

        }
    };

    /**
     * Listener for Direction change
     */
    ChangeListener<Direction> directionChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Direction> observableValue, Direction direction, Direction t1) {

            ObjectProperty<Direction> directionObjectProperty = (ObjectProperty<Direction>) observableValue;
            Integer robotNumber = gameModel.getRobotNumberByRobotDirection(directionObjectProperty);
            int robotNumberInt = robotNumber;

            String path = Objects.requireNonNull(RobotEnum.getEnumFromNumber(robotNumberInt)).getImagePath();

            Image image = boardMapCreator.getImageFromPathJAR(path);

            ObjectProperty<Position> positionObjectProperty = gameModel.getRobotPositionPropertyByRobotNumber().get(robotNumber);
            int childrenIndex = boardMapCreator.getGridPaneIndex(positionObjectProperty.getValue().getX(), robotGrid.getRowCount(), positionObjectProperty.getValue().getY());

            StackPane stackPane = (StackPane) robotGrid.getChildren().get(childrenIndex);

            ImageView imageView = (ImageView) stackPane.getChildren().get(0);

            imageView.setImage(image);
            boardMapCreator.rotateRobotStatic(direction, t1, imageView);

        }
    };

    /**
     * The method makes start point actionable
     */
    public void addActionsToStartPoints() {

        logger.debug(gameModel.getStartPoints().size());
        for (StartPoint startPoint : gameModel.getStartPoints()) {
            int x = startPoint.getX();
            int y = startPoint.getY();


            int childrenIndex = boardMapCreator.getGridPaneIndex(x, racingCourseGridPane.getRowCount(), y);

            Node node = robotGrid.getChildren().get(childrenIndex);
            StackPane stackPane = (StackPane) node;

            Node node1 = stackPane.getChildren().get(0);

            racingCourseGridPane.getChildren().get(childrenIndex).setOnMouseClicked(startPointClick);
            node.setOnMouseClicked(startPointClick);

            racingCourseGridPane.getChildren().get(childrenIndex).setOnMouseEntered(hoverOverStartingPoint);
            node1.setOnMouseEntered(hoverOverStartingPoint);

            racingCourseGridPane.getChildren().get(childrenIndex).setOnMouseExited(exitStartingPoint);
            node1.setOnMouseExited(exitStartingPoint);

            node1.disableProperty().bind(gameModel.areStartingPointsDisabledProperty());

            startingPointsOnGrid.add((StackPane) racingCourseGridPane.getChildren().get(childrenIndex));

        }

    }

    /**
     * The method handles click on a start point
     */
    public EventHandler<MouseEvent> startPointClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            StackPane stackPane = (StackPane) mouseEvent.getSource();

            int index = stackPane.getParent().getChildrenUnmodifiable().indexOf(stackPane);
            Position position = boardMapCreator.getPositionFromGridPaneIndex(index, racingCourseGridPane.getRowCount());
            logger.debug(position.getX());
            logger.debug(position.getY());

            if (!(gameModel.getCurrentPlayerID() == gameModel.getClient().getClientID())) {
                logger.debug(MessagesError.CURRENTPLAYER_ERROR.getMessage());

            } else if ((gameModel.getStartPointsTaken() != null) && gameModel.getStartPointsTaken().contains(position)) {

                logger.debug(MessagesError.SET_STARTING_POINT_ERROR.getMessage());
            } else if (gameModel.getActivePhaseNumber() != 0) {

                logger.debug(MessagesError.ACTIVEPHASE_ERROR.getMessage());
            } else {
                String json = WrapperClassSerialization.serializeSetStartingPoint(position.getX(), position.getY());
                gameModel.getClient().sendMessage(json);
            }


        }
    };


    //ProgrammingPhase


    /**
     * listener for Card changed
     */
    public ChangeListener<String> cardChanged = (observableValue, oldCard, newCard) -> {
        ImageView imageView = new ImageView();
        if (cardInHandImageViewByStringProperty.get(observableValue) == null) {
            imageView = registerImageViewByStringProperty.get(observableValue);
        } else {
            imageView = cardInHandImageViewByStringProperty.get(observableValue);
        }

        if (newCard != null) {
            CardEnum cardEnum = CardEnum.valueOf(newCard.toUpperCase());

            Image image = boardMapCreator.getImageFromPathJAR(cardEnum.getFilePath());

            imageView.setImage(image);

        } else {
            imageView.setImage(null);
        }

    };

    /**
     * The method removes with click card from register
     *
     * @param event : MouseEvent
     */
    public void removeFromRegister(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        int register = registerNumberByImageViewID.get(imageView.getId());

        StringProperty registerProperty = gameModel.getRegisterProperties()[register - 1];
        String card = registerProperty.getValue();

        for (StringProperty stringProperty : gameModel.getCardsInHandPropertyArrayList()) {
            if (stringProperty.getValue() == null) {

                stringProperty.setValue(card);
                registerProperty.setValue(null);


                String json = WrapperClassSerialization.serializeSelectedCard(null, register);
                gameModel.getClient().sendMessage(json);

                break;
            }
        }
    }

    /**
     * The method drags the card to the register
     *
     * @param event : MouseEvent
     */
    @FXML
    public void dragToRegister(MouseEvent event) {

        ImageView source = (ImageView) event.getSource();

        Dragboard dragboard = source.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putImage(source.getImage());

        dragboard.setContent(clipboardContent);

        event.consume();

        logger.debug(register1Stack.getChildren());

    }

    /**
     * The method accepts card in teh register
     *
     * @param event : DragEvent
     */
    @FXML
    public void acceptCardToRegister(DragEvent event) {
        //target
        //onDragOver

        ImageView target = (ImageView) event.getTarget();

        if (event.getGestureSource() != target && event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    /**
     * The method determines if the card is moved over teh right place
     *
     * @param event : DragEvent
     */
    @FXML
    public void movedOverTheRightContainer(DragEvent event) {
        //target
        //onDragEntered

        ImageView target = (ImageView) event.getTarget();


//        logger.debug("moved over right position but may not recognized it");

        if (event.getGestureSource() != target &&
                event.getDragboard().hasImage()) {
            //Background effect
            Parent parent = target.getParent();
            Node node = parent.getChildrenUnmodifiable().get(0);
            node.getStyleClass().add("highlight");

        }

        event.consume();

    }

    /**
     * The method drops card into register
     *
     * @param event : DragEvent
     */
    @FXML
    public void dropInRegister(DragEvent event) {
        //target
        //onDragDropped

        ImageView target = (ImageView) event.getTarget();

        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasImage()) {

            target.setImage(dragboard.getImage());

            sendSelectedCard(event);

            success = true;

        }
        event.setDropCompleted(success);
    }

    /**
     * The method determines if card moved away from right place
     *
     * @param event : DragEvent
     */
    @FXML
    public void movedAwayFromTheRightContainer(DragEvent event) {
        //target
        //onDragExited
        ImageView target = (ImageView) event.getTarget();

        Parent parent = target.getParent();
        Node node = parent.getChildrenUnmodifiable().get(0);
        node.getStyleClass().remove("highlight");


//        logger.debug("moved away from right position");
        //Revert Background effect
        event.consume();
    }

    @FXML
    public void evaluateRegistration(DragEvent event) {
        //source
        //onDragDone


        if (event.getTransferMode() == TransferMode.MOVE) {
//            ImageView imageView = (ImageView) event.getSource();
//            imageView.setImage(null);
//            sendSelectedCard(event);
//            logger.debug("Registration successful");
        }

        event.consume();

    }

    /**
     * The method sends teh protocol SelectedCard
     *
     * @param event : DragEvent
     */
    public void sendSelectedCard(DragEvent event) {

        ImageView imageViewTarget = (ImageView) event.getTarget();

        ImageView imageViewGestureSource = (ImageView) event.getGestureSource();

        int register = registerNumberByImageViewID.get(imageViewTarget.getId());

        StringProperty cardProperty = cardStringByImageViewID.get(imageViewGestureSource.getId());
        String card = cardProperty.getValue();

        String oldRegisterCard = gameModel.getRegisterProperties()[register - 1].getValue();

        gameModel.getRegisterProperties()[register - 1].setValue(card);

        cardProperty.setValue(oldRegisterCard);

        String json = WrapperClassSerialization.serializeSelectedCard(card, register);
        gameModel.getClient().sendMessage(json);

        gameModel.isSelectionFinished();

    }


    public void fillRegisterNumberByImage() {
        registerNumberByImageViewID.put(register1.getId(), 1);
        registerNumberByImageViewID.put(register2.getId(), 2);
        registerNumberByImageViewID.put(register3.getId(), 3);
        registerNumberByImageViewID.put(register4.getId(), 4);
        registerNumberByImageViewID.put(register5.getId(), 5);
    }

    public void fillCardStringByImage() {

        int i = 0;
        for (Node child : cardsInHandHBox.getChildren()) {
            Pane pane = (Pane) child;
            String id = pane.getChildren().get(0).getId();
            cardStringByImageViewID.put(id, gameModel.getCardsInHandPropertyArrayList().get(i));
            i++;
        }

    }


    public void fillCardInHandImageByStringProperty() {

        int index = 0;
        for (StringProperty stringProperty : gameModel.getCardsInHandPropertyArrayList()) {

            Pane pane = (Pane) cardsInHandHBox.getChildren().get(index);
            ImageView imageView = (ImageView) pane.getChildren().get(0);

            cardInHandImageViewByStringProperty.put(stringProperty, imageView);

            stringProperty.addListener(cardChanged);

            index++;
        }

    }

    public void fillRegisterImageViewByStringProperty() {
        int index = 0;
        for (StringProperty stringProperty : gameModel.getRegisterProperties()) {

            StackPane stackPane = (StackPane) registerHBox.getChildren().get(index);
            ImageView imageView = (ImageView) stackPane.getChildren().get(3);

            registerImageViewByStringProperty.put(stringProperty, imageView);

            stringProperty.addListener(cardChanged);

            index++;
        }
    }

    public void bindRegisterFinished() {
        for (Node child : registerHBox.getChildren()) {
            StackPane stackPane = (StackPane) child;
            ImageView imageView = (ImageView) stackPane.getChildren().get(3);

            imageView.disableProperty().bind(gameModel.selectionFinishedProperty());
        }
    }

    EventHandler<MouseEvent> hoverOverStartingPoint = event -> {
        Node node = (Node) event.getTarget();

        node.getStyleClass().add("highlight");
        logger.debug("StartingPointEntered");

    };

    EventHandler<MouseEvent> exitStartingPoint = event -> {
        Node node = (Node) event.getTarget();

        node.getStyleClass().remove("highlight");

        logger.debug("StartingPointExited");
    };

    ChangeListener<Boolean> bindingNewTimer = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            if (t1) {
                timer.textProperty().bind(gameModel.getTimerClient().secondsStringProperty());
            }
        }
    };

    public List<Text> getUsernameTextList() {
        List<Text> usernameTexts = new ArrayList<>();
        usernameTexts.add(usernamePlayer1Text);
        usernameTexts.add(usernamePlayer2Text);
        usernameTexts.add(usernamePlayer3Text);
        usernameTexts.add(usernamePlayer4Text);
        usernameTexts.add(usernamePlayer5Text);
        usernameTexts.add(usernamePlayer6Text);
        return usernameTexts;
    }

    public List<Text> getClientIDTextList() {
        List<Text> clientIDTexts = new ArrayList<>();
        clientIDTexts.add(clientIDPlayer1Text);
        clientIDTexts.add(clientIDPlayer2Text);
        clientIDTexts.add(clientIDPlayer3Text);
        clientIDTexts.add(clientIDPlayer4Text);
        clientIDTexts.add(clientIDPlayer5Text);
        clientIDTexts.add(clientIDPlayer6Text);

        return clientIDTexts;
    }

    public List<HBox> getInfoPlayerHBoxes() {
        List<HBox> playerHBoxes = new ArrayList<>();
        playerHBoxes.add(infoPlayer1HBox);
        playerHBoxes.add(infoPlayer2HBox);
        playerHBoxes.add(infoPlayer3HBox);
        playerHBoxes.add(infoPlayer4HBox);
        playerHBoxes.add(infoPlayer5HBox);
        playerHBoxes.add(infoPlayer6HBox);

        return playerHBoxes;

    }

    /**
     * The method binds the information to the Info
     */
    public void bindInfo() {
        int number = 1;
        for (Text text : getUsernameTextList()) {
            text.textProperty().bind(gameModel.getLobbyModel().getPlayerLabelMap().get(number));
            number++;
        }

        number = 1;
        for (Text text : getClientIDTextList()) {
            text.textProperty().bind(gameModel.getLobbyModel().getClientIDMap().get(number));
            number++;
        }

        number = 1;
        for (HBox infoPlayerHBox : getInfoPlayerHBoxes()) {
            infoPlayerHBox.disableProperty().bind(gameModel.getLobbyModel().getPlayerActiveMap().get(number).not());
            number++;
        }

        activePhaseLabel.textProperty().bind(gameModel.activePhasePropertyProperty());
    }

    /**
     * listener for energy space change
     */
    ChangeListener<List<EnergySpace>> energySpaceListener = (observableValue, energySpaces, t1) -> {
        EnergySpace energySpace = t1.get(t1.size() - 1);
        int x = energySpace.getX();
        int y = energySpace.getY();
        Node node = racingCourseGridPane.getChildren().get(boardMapCreator.getGridPaneIndex(x, racingCourseGridPane.getRowCount(), y));
        StackPane stackPane = (StackPane) node;
        ImageView imageView = (ImageView) stackPane.getChildren().get(1);
        Image image = boardMapCreator.getImageFromPathJAR("gui/Images/board_elements/energy_space.png");
        imageView.setImage(image);
    };

    /**
     * listener for twister map
     */
    ChangeListener<Boolean> twisterListener = (observableValue, aBoolean, t1) -> {
        if (t1) {
            for (Position position : gameModel.getOldCheckPointPositions()) {
                int xOld = position.getX();
                int yOld = position.getY();
                int gridPaneIndexOld = boardMapCreator.getGridPaneIndex(xOld, racingCourseGridPane.getRowCount(), yOld);

                StackPane stackPaneOld = (StackPane) racingCourseGridPane.getChildren().get(gridPaneIndexOld);
                ImageView imageViewOld = (ImageView) stackPaneOld.getChildren().get(stackPaneOld.getChildren().size() - 1);

                int index = gameModel.getOldCheckPointPositions().indexOf(position);
                Position positionNew = gameModel.getNewCheckPointPositions().get(index);
                int gridPaneIndexNew = boardMapCreator.getGridPaneIndex(positionNew.getX(), racingCourseGridPane.getRowCount(), positionNew.getY());
                StackPane stackPaneNew = (StackPane) racingCourseGridPane.getChildren().get(gridPaneIndexNew);

                ImageView imageViewNew = new ImageView(imageViewOld.getImage());
                imageViewNew.setFitHeight(imageViewOld.getFitHeight());
                imageViewNew.setFitWidth(imageViewOld.getFitWidth());

                stackPaneNew.getChildren().add(imageViewNew);

                stackPaneOld.getChildren().remove(imageViewOld);


            }


        }
    };

    /**
     * listener for game finished
     */
    ChangeListener<Boolean> gameFinishedListener = (observableValue, aBoolean, t1) -> {
        if (t1) {
            Stage stage = (Stage) mapPane.getScene().getWindow();

            System.exit(0);

        }
    };


    public void hoverOverCardsInHand(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getTarget();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        scaleTransition.setRate(2.0);
        node.setViewOrder(-1.0);
        node.getParent().setViewOrder(-1.0);
        node.getParent().getParent().setViewOrder(-1.0);
        scaleTransition.play();
    }

    public void exitCardsOnHand(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getTarget();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        scaleTransition.setRate(-2.0);
        node.setViewOrder(0.0);
        node.getParent().setViewOrder(0.0);
        node.getParent().getParent().setViewOrder(0.0);
        scaleTransition.play();
    }


}
