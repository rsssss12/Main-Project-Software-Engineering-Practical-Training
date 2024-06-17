package AI;

import gamelogic.Position;
import gamelogic.RobotEnum;
import gamelogic.boardElements.StartPoint;
import gamelogic.cards.Card;
import gui.mvvm.lobby.LobbyModel;
import javafx.beans.property.StringProperty;
import json.wrapper_utilities.CardWrapping;
import json.wrapper_utilities.WrapperClassSerialization;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gamelogic.RobotEnum.*;

public class DumbAI {
    private final Logger logger = LogManager.getLogger(DumbAI.class.getName());
    public int clientID;
    public Client client;
    public RobotEnum chosenRobot;
    public String randomName;
    /**
     * simple AI
     * @author Robert Scholz
     */
    public DumbAI() {
    }

    /**
     * This method lets the AI to join the game
     *
     * @param clientnumber : The ClientID of the AI
     */
    public void dumbAIJoinsGame(int clientnumber) {
        if (clientnumber == client.getClientID()) {
            randomName = Integer.toString((int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000));
            randomName = "AI " + randomName;
            LobbyModel lobbyModel = LobbyModel.getInstance();
            logger.debug("Check Time: dumbAi joinsGame");
            if (!lobbyModel.isRobotTaken(ROBOT1)) {
                chosenRobot = RobotEnum.getEnumFromName("Navy Seal");
                logger.debug("chosenRobot = " + chosenRobot);
            } else if (!lobbyModel.isRobotTaken(ROBOT2)) {
                chosenRobot = RobotEnum.getEnumFromName("Red October");
                logger.debug("chosenRobot = " + chosenRobot);
            } else if (!lobbyModel.isRobotTaken(ROBOT3)) {
                chosenRobot = RobotEnum.getEnumFromName("Whalter White");
                logger.debug("chosenRobot = " + chosenRobot);
            } else if (!lobbyModel.isRobotTaken(ROBOT4)) {
                chosenRobot = RobotEnum.getEnumFromName("Whalker, Texas Ranger");
                logger.debug("chosenRobot = " + chosenRobot);
            } else if (!lobbyModel.isRobotTaken(ROBOT5)) {
                chosenRobot = RobotEnum.getEnumFromName("Narwhal Longbottom");
                logger.debug("chosenRobot = " + chosenRobot);
            } else if (!lobbyModel.isRobotTaken(ROBOT6)) {
                chosenRobot = RobotEnum.getEnumFromName("Incredible Whalk");
                logger.debug("chosenRobot = " + chosenRobot);
            }
            client.sendMessage(WrapperClassSerialization.serializePlayerValues(randomName, chosenRobot));
        }
    }

    /**
     * This method signalizes the readiness for the AI
     */
    public void dumbAISignalizesReadiness() {
        client.sendMessage(WrapperClassSerialization.serializeSetStatus(true));
    }

    /**
     * This method selects a starting point for the AI
     *
     * @param dumbAI : The AI
     */
    public void dumbAISetsStartingPoint(DumbAI dumbAI) {
        if (dumbAI.getClientID() == client.getClientID()) {
            List<StartPoint> startPoints = dumbAI.getClient().getGameModel().getStartPoints();
            Position chosenStartingPointForAI = new Position();
            if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(0))) {
                chosenStartingPointForAI.setX(startPoints.get(0).getX());
                chosenStartingPointForAI.setY(startPoints.get(0).getY());
            } else if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(1))) {
                chosenStartingPointForAI.setX(startPoints.get(1).getX());
                chosenStartingPointForAI.setY(startPoints.get(1).getY());
            } else if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(2))) {
                chosenStartingPointForAI.setX(startPoints.get(2).getX());
                chosenStartingPointForAI.setY(startPoints.get(2).getY());
            } else if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(3))) {
                chosenStartingPointForAI.setX(startPoints.get(3).getX());
                chosenStartingPointForAI.setY(startPoints.get(3).getY());
            } else if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(4))) {
                chosenStartingPointForAI.setX(startPoints.get(4).getX());
                chosenStartingPointForAI.setY(startPoints.get(4).getY());
            } else if (isStartPointFree(dumbAI.getClient().getGameModel().getStartPointsTaken(), startPoints.get(5))) {
                chosenStartingPointForAI.setX(startPoints.get(5).getX());
                chosenStartingPointForAI.setY(startPoints.get(5).getY());
            }
            client.sendMessage(WrapperClassSerialization.serializeSetStartingPoint(chosenStartingPointForAI.getX(), chosenStartingPointForAI.getY()));
        }
    }

    /**
     * This method chooses random cards for the AI
     *
     * @param nineCardsChosen : The nine cards sent from the Server
     * @param clientnumber : The ClientID of the AI
     */
    public void dumbAIPlaysCards(ArrayList<String> nineCardsChosen, int clientnumber) {
        if (clientnumber == client.getClientID()) {
            ArrayList<Card> nineCards = CardWrapping.StringListToCardArrayList(nineCardsChosen);
            Collections.shuffle(nineCards);
            while (nineCards.get(0).toString().equals("Again")) {
                Collections.shuffle(nineCards);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            StringProperty[] registerProperties = client.getGameModel().getRegisterProperties();
            List<StringProperty> cardsInHandPropertyArrayList = client.getGameModel().getCardsInHandPropertyArrayList();


            for (int i = 0; i < 5; i++) {
                registerProperties[i].setValue(nineCards.get(i).toString());
//                cardsInHandPropertyArrayList.get(0).setValue(null);
            }

            client.sendMessage(WrapperClassSerialization.serializeSelectedCard(nineCards.get(0).toString(), 1));
            client.sendMessage(WrapperClassSerialization.serializeSelectedCard(nineCards.get(1).toString(), 2));
            client.sendMessage(WrapperClassSerialization.serializeSelectedCard(nineCards.get(2).toString(), 3));
            client.sendMessage(WrapperClassSerialization.serializeSelectedCard(nineCards.get(3).toString(), 4));
            client.sendMessage(WrapperClassSerialization.serializeSelectedCard(nineCards.get(4).toString(), 5));
        }
    }

    /**
     * This method checks if a starting point is available
     *
     * @param startPointsTaken : List of all starting points that are taken
     * @param startingPoint : A chosen starting point
     */
    public boolean isStartPointFree(List<Position> startPointsTaken, StartPoint startingPoint) {
        for (Position startPoint : startPointsTaken) {
            if ((startPoint.getX() == startingPoint.getX()) &&
                    (startPoint.getY() == startingPoint.getY())) {
                return false;
            }
        }
        return true;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}