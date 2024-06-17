package gui.mvvm.connection;

import gui.mvvmutility.ViewEnum;
import gui.mvvmutility.ViewHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class ConnectionViewModel implements the connection view
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public class ConnectionViewModel implements Initializable {

    private final Logger logger = LogManager.getLogger(ConnectionViewModel.class.getName());
    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private ImageView imageBackground;
    @FXML
    private CheckBox checkboxKI;
    @FXML
    private Button buttonConnectionToServer;
    @FXML
    private Label failureMessage;

    private ConnectionModel connectionModel;

    public boolean connectButtonPressed;


    public ConnectionViewModel() {

    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        connectionModel = ConnectionModel.getInstance();

        checkboxKI.selectedProperty().bindBidirectional(connectionModel.isAIProperty());
    }


    /**
     * The method handles the button to connect to the server
     */
    public void connectToServer() {
        connectButtonPressed = true;
        try {
            Client client = Client.start();
            client.setAI(connectionModel.isAIProperty().getValue());

            enterLobby();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method handles the checkBoxKI
     */
    public void pushAIBox() {

        logger.debug("AI state changed");
    }

    /**
     * The method enters the next view, the lobby view
     *
     * @throws IOException : exception
     */
    public void enterLobby() throws IOException {
        Stage stage = (Stage) buttonConnectionToServer.getScene().getWindow();

        ViewHandler.openView(stage, ViewEnum.LobbyView);

    }

}
