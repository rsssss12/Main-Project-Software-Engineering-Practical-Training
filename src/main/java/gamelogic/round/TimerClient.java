package gamelogic.round;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The class TimerClient implements the timer for the programming phase
 */
public class TimerClient extends TimerTask {

    private final Timer timer;

    private IntegerProperty seconds = new SimpleIntegerProperty(30);
    private StringProperty secondsString = new SimpleStringProperty("30");

    public TimerClient(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        seconds.setValue(seconds.getValue()-1);
        Platform.runLater(() -> secondsString.set(seconds.getValue().toString()));

        if (seconds.getValue() == 0) {
            timer.cancel();
        }

    }

    public StringProperty secondsStringProperty() {
        return secondsString;
    }
}
