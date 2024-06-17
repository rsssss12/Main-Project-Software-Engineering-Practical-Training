package gui.mvvm.chat;

import gui.mvvm.lobby.LobbyModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import json.wrapper_utilities.WrapperClassSerialization;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class ChatViewModel implements Initializable {
    private final Logger logger = LogManager.getLogger(ChatViewModel.class.getName());

    @FXML
    private Button buttonSend;
    @FXML
    private TextField textFieldMessage;
    @FXML
    private VBox vboxMessages;
    @FXML
    private ScrollPane scrollPaneChat;
    @FXML
    private ComboBox<String> comboBoxUsers;
    private String username;

    private Client client;

    private ChatModel chatModel;
    @FXML
    public AnchorPane lobbyView;
    @FXML
    public AnchorPane LobbyBackGround;
    @FXML
    private Label userID;

    private LobbyModel lobbyModel;

    public ChatViewModel() {

    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        assert buttonSend != null;
        textFieldMessage.requestFocus();
        textFieldMessage.setOnAction(event -> {
            try {
                performSend(textFieldMessage.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        textFieldMessage.textProperty().addListener((observableValue, s, t1) -> checkForValidMessage());

        vboxMessages.heightProperty().addListener((observable, oldValue, newValue) -> scrollPaneChat.setVvalue((Double) newValue));


        chatModel.counterProperty().addListener((obs, old, newV) -> {

            String sender = chatModel.getSenders().get((int) newV - 1);
            String message = chatModel.getChatHistory().get((int) newV - 1);
            Boolean privateM = chatModel.getPrivateMessages().get((int) newV - 1);

            addMessageToChat(sender, message, privateM);

        });

    }


    public void checkForValidMessage() {
        buttonSend.setDisable(textFieldMessage.textProperty().getValue().trim().isEmpty());
    }


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

        vboxMessages.getChildren().add(hBox);
    }


    @FXML
    private void sendMessageWithButton() throws Exception {
        performSend(textFieldMessage.getText());
    }


    public void sendMessage() {
    }


    public void performSend(String message) {
        if (!message.trim().equals("")) {
            if (comboBoxUsers.getValue().isEmpty() || comboBoxUsers.getValue().equals("All clients")) {
                String json = WrapperClassSerialization.serializeSendChat(message, -1);

                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to all clients");

            } else {
                String json = WrapperClassSerialization.serializeSendChat(message, client.getClientID());
                lobbyModel.getClient().sendMessage(json);
                logger.debug("Message " + message + " send to client" + client.getClientID());
            }
        }
        textFieldMessage.clear();
        textFieldMessage.requestFocus();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
