package gui;

import gui.mvvmutility.ViewEnum;
import gui.mvvmutility.ViewHandler;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FXApplication extends javafx.application.Application {

    private static final Logger logger = LogManager.getLogger(FXApplication.class.getName());
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        ViewHandler.openView(stage, ViewEnum.ConnectionView);
    }



}
