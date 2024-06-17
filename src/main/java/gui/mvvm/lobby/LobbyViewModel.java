package gui.mvvm.lobby;

import gamelogic.RobotEnum;
import gamelogic.maps.MapEnum;
import gui.mvvm.chat.ChatModel;
import gui.mvvmutility.ViewEnum;
import gui.mvvmutility.ViewHandler;
import gui.mvvm.game.BoardMapCreator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import json.wrapper_utilities.WrapperClassSerialization;
import messages.MessagesError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * The class LobbyViewModel
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class LobbyViewModel {

    private final Logger logger = LogManager.getLogger(LobbyViewModel.class.getName());
    public AnchorPane lobbyViewPane;
    @FXML
    private ToggleButton soundButton;
    @FXML
    private Label failureMessageMap;
    @FXML
    private Button chooseRacingCourseButton;
    @FXML
    private CheckBox readyBox1;
    @FXML
    private CheckBox readyBox2;
    @FXML
    private CheckBox readyBox3;
    @FXML
    private CheckBox readyBox4;
    @FXML
    private CheckBox readyBox5;
    @FXML
    private CheckBox readyBox6;
    @FXML
    private Label failureMessage;
    @FXML
    private Button usernameButton;
    @FXML
    private ComboBox chooseRobot;
    @FXML
    private ComboBox chooseRacingCourse;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;
    @FXML
    private Label player5;
    @FXML
    private Label player6;
    @FXML
    private Label ready1Label;
    @FXML
    private Label ready2Label;
    @FXML
    private Label ready3Label;
    @FXML
    private Label ready4Label;
    @FXML
    private Label ready5Label;
    @FXML
    private Label ready6Label;
    @FXML
    private TextField usernameTextField;
    @FXML
    private ImageView robotPicture;

    private String chosenName;
    private RobotEnum chosenRobot;
    private MapEnum chosenRacingCourse;

    private BoardMapCreator boardMapCreator = new BoardMapCreator();
    private Scene scene = new Scene(new Pane());
    private Stage stage = new Stage();

    private LobbyModel lobbyModel;
    private ChatModel chatModel;

    //Chat
    @FXML
    private Button buttonSend;
    @FXML
    private TextField textFieldMessage;
    @FXML
    private ScrollPane scrollPaneChat;
    @FXML
    private VBox vBoxChat;
    @FXML
    private ComboBox<String> comboBoxUsers;
    @FXML
    private Label userID;
    @FXML
    private ImageView mapPicture;

    ObservableList<String> names = FXCollections.observableArrayList();


    public LobbyViewModel() {
        lobbyModel = LobbyModel.getInstance();
        chatModel = ChatModel.getInstance();
    }


    public void initialize() {
        lobbyModel = LobbyModel.getInstance();
        bindPlayerLabels();
        bindReadiness();

        chooseRobot.getItems().addAll(RobotEnum.getNames());

        chooseRacingCourse.getItems().addAll(MapEnum.getNames());

        chooseRacingCourse.setDisable(true);

        usernameTextField.disableProperty().bind(lobbyModel.isUsernameTextFieldDisabledProperty());
        chooseRobot.disableProperty().bind(lobbyModel.isChooseRobotDisabledProperty());
        usernameButton.disableProperty().bind(lobbyModel.isConfirmationButtonDisabledProperty());

        chooseRacingCourse.disableProperty().bind(lobbyModel.isSelectMapDisabledProperty());
        chooseRacingCourseButton.disableProperty().bind(lobbyModel.isSelectMapDisabledProperty());
        lobbyModel.hasGameStartedProperty().addListener(openNextView);


        userID.setText("Your ID: " + (lobbyModel.getClient().getClientID()));

        //Chat
        assert buttonSend != null;
        textFieldMessage.requestFocus();
        textFieldMessage.setOnAction(event -> {
            try {
                performSend(textFieldMessage.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        names.add("All clients");
        comboBoxUsers.setValue("All clients");
        comboBoxUsers.setItems(names);

        playerBind();

        textFieldMessage.textProperty().addListener((observableValue, s, t1) -> checkForValidMessage());

        vBoxChat.heightProperty().addListener((observable, oldValue, newValue) -> scrollPaneChat.setVvalue((Double) newValue));


        chatModel.counterProperty().addListener((obs, old, newV) -> {

            String sender = chatModel.getSenders().get((int) newV - 1);
            String message = chatModel.getChatHistory().get((int) newV - 1);
            Boolean privateM = chatModel.getPrivateMessages().get((int) newV - 1);

            addMessageToChat(sender, message, privateM);

        });

        music();

    }


    MediaPlayer mediaPlayer;

    /**
     * The method plays music on loop
     */
    public void music() {
//        Media h = new Media(getClass().getResource("/sound/narwhals-song.wav").toExternalForm());
        Media h = new Media(Objects.requireNonNull(getClass().getResource("/sound/Overcooked-2-Soundtrack-Map.mp3")).toExternalForm());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    /**
     * The method mutes and unmutes the sound if button is clicked
     */
    public void mute() {
        if (soundButton.isSelected()) {
            mediaPlayer.play();
            mediaPlayer.setMute(false);
        } else {
            mediaPlayer.setMute(true);
        }
    }

    /**
     * The method chooses a robot if in combobox is selected
     *
     * @param actionEvent :
     */
    public void chooseRobot(ActionEvent actionEvent) {

        chosenRobot = RobotEnum.getEnumFromName(chooseRobot.getValue().toString());
        logger.debug("chosenRobot = " + chosenRobot);
        robotPicture.setImage(boardMapCreator.getImageFromPathJAR(chosenRobot.getImagePath()));

    }

    /**
     * The method handles the button to confirm selection of username and robot
     */
    public void confirmChoice() {

        chosenName = usernameTextField.getText();

        if (chosenRobot == null) {
            failureMessage.setText("You haven't chosen a robot yet.");
        } else if (usernameTextField.getText().isBlank()) {
            failureMessage.setText("You haven't chosen a name yet");
        } else if (lobbyModel.isRobotTaken(chosenRobot)) {

            failureMessage.setText(MessagesError.PLAYERADDED_ERROR.getMessage());

        } else if (usernameTextField.getText().isBlank()) {
            logger.debug("You haven't chosen a name yet.");

            failureMessage.setText("You haven't chosen a name yet.");

        } else if (usernameTextField.getText().length() > 40) {
            failureMessage.setText("Name is too long.");

        } else {
            failureMessage.setText("");
            String json = WrapperClassSerialization.serializePlayerValues(chosenName, chosenRobot);
            lobbyModel.getClient().sendMessage(json);
        }
    }

    /**
     * The method chooses the racing course if in comboBox
     *
     * @param actionEvent :
     */
    public void chooseRacingCourse(ActionEvent actionEvent) {

        chosenRacingCourse = MapEnum.getEnumFromName(chooseRacingCourse.getValue().toString());
        logger.debug("chosenRacingCourse = " + chosenRacingCourse);
        mapPicture.setImage(boardMapCreator.getImageFromPathJAR(chosenRacingCourse.getImagePath()));
    }

    /**
     * The method sends the chosen course
     */
    public void sendChosenRacingCourse() {
        if (chosenRacingCourse == null) {
            failureMessageMap.setText("No course chosen.");

        } else if (lobbyModel.isNotEnoughPlayersReady()) {
            failureMessageMap.setText("Not enough players are ready.");
        } else {
            failureMessageMap.setText("");

            String json = WrapperClassSerialization.serializeMapSelected(chosenRacingCourse);

            lobbyModel.getClient().sendMessage(json);
        }
    }

    /**
     * The method handles the readyBox ticking
     *
     * @param event : ActionEvent
     */
    public void tickReadyBox(ActionEvent event) {

        CheckBox checkbox = (CheckBox) event.getSource();

        String json = WrapperClassSerialization.serializeSetStatus(checkbox.isSelected());

        lobbyModel.getClient().sendMessage(json);

    }

    /**
     * The method opens the next view, the game view
     */
    public void enterGame() throws IOException {
        Stage stage = (Stage) player1.getScene().getWindow();

        ViewHandler.openView(stage, ViewEnum.GameView);
    }

    /**
     * The method listens if game has started is true opens view
     */
    ChangeListener<Boolean> openNextView = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldBoolean, Boolean newBoolean) {
            try {
                if (lobbyModel.hasGameStartedProperty().getValue()) {
                    mediaPlayer.stop();
                    enterGame();
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    };

    /**
     * The method binds the playerLabels
     */
    public void bindPlayerLabels() {
        player1.textProperty().bind(lobbyModel.namePlayer1Property());
        player2.textProperty().bind(lobbyModel.namePlayer2Property());
        player3.textProperty().bind(lobbyModel.namePlayer3Property());
        player4.textProperty().bind(lobbyModel.namePlayer4Property());
        player5.textProperty().bind(lobbyModel.namePlayer5Property());
        player6.textProperty().bind(lobbyModel.namePlayer6Property());

    }

    /**
     * The method binds the readyLabels and readyBoxes
     */
    public void bindReadiness() {
        ready1Label.disableProperty().bind(lobbyModel.isReadyPlayer1Property().not());
        readyBox1.visibleProperty().bind(lobbyModel.readyBox1Property());
        readyBox1.disableProperty().bind(lobbyModel.readyBox1Property().not());

        ready2Label.disableProperty().bind(lobbyModel.isReadyPlayer2Property().not());
        readyBox2.visibleProperty().bind(lobbyModel.readyBox2Property());
        readyBox2.disableProperty().bind(lobbyModel.readyBox2Property().not());

        ready3Label.disableProperty().bind(lobbyModel.isReadyPlayer3Property().not());
        readyBox3.visibleProperty().bind(lobbyModel.readyBox3Property());
        readyBox3.disableProperty().bind(lobbyModel.readyBox3Property().not());

        ready4Label.disableProperty().bind(lobbyModel.isReadyPlayer4Property().not());
        readyBox4.visibleProperty().bind(lobbyModel.readyBox4Property());
        readyBox4.disableProperty().bind(lobbyModel.readyBox4Property().not());

        ready5Label.disableProperty().bind(lobbyModel.isReadyPlayer5Property().not());
        readyBox5.visibleProperty().bind(lobbyModel.readyBox5Property());
        readyBox5.disableProperty().bind(lobbyModel.readyBox5Property().not());

        ready6Label.disableProperty().bind(lobbyModel.isReadyPlayer6Property().not());
        readyBox6.visibleProperty().bind(lobbyModel.readyBox6Property());
        readyBox6.disableProperty().bind(lobbyModel.readyBox6Property().not());
    }


    //Chat

    /**
     * The method listens for player changes and adds to names list
     */
    private void playerBind() {
        player1.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
        player2.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
        player3.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
        player4.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
        player5.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
        player6.textProperty().addListener((observableValue, s, t1) -> names.add(s + ": " + t1));
    }


    /**
     * The method sets send button disabled till there is input
     */
    public void checkForValidMessage() {
        buttonSend.setDisable(textFieldMessage.textProperty().getValue().trim().isEmpty());
    }

    @FXML
    private void sendMessageWithButton() {
        performSend(textFieldMessage.getText());
    }


    /**
     * The method adds a new message to the chat room
     *
     * @param sender   : String sender
     * @param message  : String message
     * @param privateM : Boolean if private message
     */
    public void addMessageToChat(String sender, String message, Boolean privateM) {
        HBox hBox = new HBox();

        Scene scene = new Scene(new Group());
        scene.getStylesheets().add("chat.css");

        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text senderText = new Text(sender + ": ");
        Text messageText = new Text(message + "\n");

        TextFlow textFlow = new TextFlow(senderText, messageText);

        if (Integer.parseInt(sender) == chatModel.getClient().getClientID()) {
            hBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            hBox.setAlignment(Pos.CENTER_LEFT);
        }

        if (privateM) {
            textFlow.getStyleClass().add("private");
        } else {
            textFlow.getStyleClass().add("public");
        }

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        vBoxChat.getChildren().add(hBox);
    }


    /**
     * The method sends the SendChat protocol
     *
     * @param message : String message to send
     */
    public void performSend(String message) {
        if (!message.trim().equals("")) {
            if (comboBoxUsers.getValue().isEmpty() || comboBoxUsers.getValue().equals("All clients")) {
                String json = WrapperClassSerialization.serializeSendChat(message, -1);

                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to all clients");
            }

            if (comboBoxUsers.getValue().contains("Player 1")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer1());
                logger.debug("lobbyModel.getClientIdPlayer1" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
            if (comboBoxUsers.getValue().contains("Player 2")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer2());
                logger.debug("lobbyModel.getClientIdPlayer2" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
            if (comboBoxUsers.getValue().contains("Player 3")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer3());
                logger.debug("lobbyModel.getClientIdPlayer3" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
            if (comboBoxUsers.getValue().contains("Player 4")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer4());
                logger.debug("lobbyModel.getClientIdPlayer4" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
            if (comboBoxUsers.getValue().contains("Player 5")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer5());
                logger.debug("lobbyModel.getClientIdPlayer5" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
            if (comboBoxUsers.getValue().contains("Player 6")) {
                int receiverID = Integer.parseInt(lobbyModel.getClientIDPlayer6());
                logger.debug("lobbyModel.getClientIdPlayer6" + receiverID);
                String json = WrapperClassSerialization.serializeSendChat(message, receiverID);
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + receiverID);
            }
        }
        textFieldMessage.clear();
        textFieldMessage.requestFocus();
    }

    public void sendMessage(ActionEvent actionEvent) {
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


}
