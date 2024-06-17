package AI;

import java.util.TimerTask;
/**
 * @author Robert Scholz
 */
public class AIStartingPointTimer extends TimerTask {
    private final DumbAI dumbAI;

    public AIStartingPointTimer(DumbAI dumbAI) {
        this.dumbAI = dumbAI;
    }

    @Override
    public void run() {
        dumbAI.dumbAISetsStartingPoint(dumbAI);
    }
}
