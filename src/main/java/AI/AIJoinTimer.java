package AI;

import java.util.TimerTask;
/**
 * @author Robert Scholz
 */
public class AIJoinTimer extends TimerTask {



    private final DumbAI dumbAI;

    public AIJoinTimer(DumbAI dumbAI) {
        this.dumbAI = dumbAI;
    }

    @Override
    public void run() {
        dumbAI.dumbAIJoinsGame(dumbAI.getClientID());
    }
}
