package network;

import json.wrapper_utilities.WrapperClassSerialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

/**
 * The class AliveSender sends the Alive protocol
 */
public class AliveSender extends TimerTask {

    private final Logger logger = LogManager.getLogger(AliveSender.class.getName());
    private final ClientHandler clientHandler;
    private long startTime = 0;

    public AliveSender(ClientHandler clientHandler) {

        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {

        String json = WrapperClassSerialization.serializeAlive();

        clientHandler.setAliveReceived(false);

        clientHandler.sendMessageToSelf(json);


        if (clientHandler.getAliveReceivedAt() - (startTime - 5000) < -5000) {

            clientHandler.setConnectionCount(clientHandler.getConnectionCount() + 1);


        } else {
            clientHandler.setConnectionCount(0);
        }

        startTime = System.currentTimeMillis();

//        if(clientHandler.getConnectionCount() > 5) {
//            clientHandler.handleLostConnection();
//        }

    }
}
