package gamelogic.round;

import network.ClientHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The class TimerServer implements the timer and ends it
 */
public class TimerServer extends TimerTask {
    private final ClientHandler clientHandler;
    private final Timer timer;
    private final ProgrammingPhase programmingPhase;


    public TimerServer(ClientHandler clientHandler, Timer timer, ProgrammingPhase programmingPhase) {
        this.clientHandler = clientHandler;
        this.timer = timer;
        this.programmingPhase = programmingPhase;
    }

    @Override
    public void run() {
        programmingPhase.endTimer(clientHandler);
        timer.cancel();
    }
}
