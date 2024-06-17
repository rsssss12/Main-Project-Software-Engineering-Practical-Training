package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

/**
 * The class AliveWaiter waits for the Alive protocol
 */
public class AliveWaiter extends TimerTask {

    private final Logger logger = LogManager.getLogger(AliveWaiter.class.getName());
    private final ClientHandler clientHandler;

    public AliveWaiter(ClientHandler clientHandler) {

        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {

        if (!clientHandler.isAliveReceived()) {

            clientHandler.setConnectionCount(clientHandler.getConnectionCount() + 1);
        }
        else {
            clientHandler.setConnectionCount(0);
        }
//        if(clientHandler.getConnectionCount() > 3) {
//            clientHandler.handleLostConnection();
//            this.cancel();
//        }


    }
}

