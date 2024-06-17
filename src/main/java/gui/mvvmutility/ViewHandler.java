package gui.mvvmutility;

import gui.FXApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The class ViewHandler opens the views
 * @author Hannah Spierer
 */
public class ViewHandler {
    public static void openView(Stage stage, ViewEnum view) throws IOException {
        Scene scene;
        FXMLLoader loader = new FXMLLoader();
        Parent root;

        loader.setLocation(FXApplication.class.getResource(view + ".fxml"));
        root = loader.load();

        stage.setOnCloseRequest(windowEvent -> System.exit(0));

        switch (view) {

            case ConnectionView -> stage.setTitle("Connection");

            case ChatView -> stage.setTitle("Chat");

            case LobbyView -> stage.setTitle("Lobby");


            case GameView -> stage.setTitle("Robo Rally");

        }

        scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(FXApplication.class.getResource("gameView.css")).toExternalForm());

        stage.setScene(scene);

        stage.show();

        Resizer resizer = new Resizer();
        resizer.bindSizeListenerResize(stage, scene, root);

        if(!stage.getTitle().equals("Connection")) {
            resizer.determineStartingSize(stage,scene,root);
        }
    }


}
