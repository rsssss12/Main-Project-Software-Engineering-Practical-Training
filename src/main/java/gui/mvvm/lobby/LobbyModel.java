package gui.mvvm.lobby;

import gamelogic.Player;
import gamelogic.RobotEnum;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import json.json_wrappers.chat.ReceivedChat;
import json.json_wrappers.lobby.*;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class LobbyModel
 */
public class LobbyModel {

    private final Logger logger = LogManager.getLogger(LobbyModel.class.getName());
    private Client client;
    private static volatile LobbyModel lobbyModel;


    private ObjectProperty<Player> player1 = new SimpleObjectProperty<>();
    private ObjectProperty<Player> player2;
    private ObjectProperty<Player> player3;
    private ObjectProperty<Player> player4;
    private ObjectProperty<Player> player5;
    private ObjectProperty<Player> player6;

    private final StringProperty namePlayer1 = new SimpleStringProperty("Player 1");
    private final StringProperty namePlayer2 = new SimpleStringProperty("Player 2");
    private final StringProperty namePlayer3 = new SimpleStringProperty("Player 3");
    private final StringProperty namePlayer4 = new SimpleStringProperty("Player 4");
    private final StringProperty namePlayer5 = new SimpleStringProperty("Player 5");
    private final StringProperty namePlayer6 = new SimpleStringProperty("Player 6");

    private final BooleanProperty isReadyPlayer1 = new SimpleBooleanProperty();
    private final BooleanProperty isReadyPlayer2 = new SimpleBooleanProperty();
    private final BooleanProperty isReadyPlayer3 = new SimpleBooleanProperty();
    private final BooleanProperty isReadyPlayer4 = new SimpleBooleanProperty();
    private final BooleanProperty isReadyPlayer5 = new SimpleBooleanProperty();
    private final BooleanProperty isReadyPlayer6 = new SimpleBooleanProperty();

    private final BooleanProperty readyBox1 = new SimpleBooleanProperty();
    private final BooleanProperty readyBox2 = new SimpleBooleanProperty();
    private final BooleanProperty readyBox3 = new SimpleBooleanProperty();
    private final BooleanProperty readyBox4 = new SimpleBooleanProperty();
    private final BooleanProperty readyBox5 = new SimpleBooleanProperty();
    private final BooleanProperty readyBox6 = new SimpleBooleanProperty();

    private final StringProperty clientIDPlayer1 = new SimpleStringProperty("");
    private final StringProperty clientIDPlayer2 = new SimpleStringProperty("");
    private final StringProperty clientIDPlayer3 = new SimpleStringProperty("");
    private final StringProperty clientIDPlayer4 = new SimpleStringProperty("");
    private final StringProperty clientIDPlayer5 = new SimpleStringProperty("");
    private final StringProperty clientIDPlayer6 = new SimpleStringProperty("");

    private final BooleanProperty isPlayer1Active = new SimpleBooleanProperty(false);
    private final BooleanProperty isPlayer2Active = new SimpleBooleanProperty(false);
    private final BooleanProperty isPlayer3Active = new SimpleBooleanProperty(false);
    private final BooleanProperty isPlayer4Active = new SimpleBooleanProperty(false);
    private final BooleanProperty isPlayer5Active = new SimpleBooleanProperty(false);
    private final BooleanProperty isPlayer6Active = new SimpleBooleanProperty(false);


    private final BooleanProperty isSelfPlayer = new SimpleBooleanProperty();
    private final BooleanProperty isChooseRobotDisabled = new SimpleBooleanProperty();
    private final BooleanProperty isConfirmationButtonDisabled = new SimpleBooleanProperty();
    private final BooleanProperty isUsernameTextFieldDisabled = new SimpleBooleanProperty();
    private final BooleanProperty isSelectMapDisabled = new SimpleBooleanProperty();
    private final BooleanProperty isSelfReady = new SimpleBooleanProperty();
    private final BooleanProperty hasGameStarted = new SimpleBooleanProperty();
    private final BooleanProperty notEnoughPlayersReady = new SimpleBooleanProperty(true);

    private final HashMap<Integer, StringProperty> playerLabelMap = new HashMap<>();

    private final HashMap<Integer, BooleanProperty> checkBoxMap = new HashMap<>();

    private final HashMap<Integer, Integer> robotNumberByClientID = new HashMap<>();

    private final HashMap<Integer, BooleanProperty> readyMap = new HashMap<>();
    private final HashMap<Integer, StringProperty> clientIDMap = new HashMap<>();
    private final HashMap<Integer, BooleanProperty> playerActiveMap = new HashMap<>();

    private final List<RobotEnum> robotsTaken = new ArrayList<>();
    private final List<Integer> clientIDs = new ArrayList<>();
    private final List<Integer> playersReady = new ArrayList<>();

    private final StringProperty selectedMap = new SimpleStringProperty();

    private final List<Player> players = new ArrayList<>();

    private ObservableList<RobotEnum> robotEnumObservableList = FXCollections.observableArrayList();


    public LobbyModel() {
        robotEnumObservableList.addAll(RobotEnum.values());

        fillPlayerLabelMap();
        fillCheckBoxMap();
        fillReadyMap();
        fillClientIDMap();
        fillPlayerActiveMap();

        isSelfReady.setValue(false);
        isSelectMapDisabled.setValue(true);
        hasGameStarted.setValue(false);


    }


    public static LobbyModel getInstance() {
        if (lobbyModel == null) {
            synchronized (LobbyModel.class) {
                if (lobbyModel == null) {
                    lobbyModel = new LobbyModel();

                }
            }
        }
        return lobbyModel;
    }


    /**
     * The method checks if a robot is already taken
     *
     * @param robotEnum : RobotEnum robot
     * @return boolean : true if taken
     */
    public boolean isRobotTaken(RobotEnum robotEnum) {

        return robotsTaken.contains(robotEnum);
    }

    /**
     * The method handles the protocol PlayerAdded
     *
     * @param playerAdded : PlayerAdded protocol
     */
    public void handlePlayerAdded(PlayerAdded playerAdded) {

        int clientID = playerAdded.getClientID();
        int robotNumber = playerAdded.getFigure();
        String username = playerAdded.getName();

        logger.debug("username: " + username);
        logger.debug("robotNumber: " + robotNumber);

        Player player = new Player();
        player.setUsername(username);
        player.setRobotEnum(RobotEnum.getEnumFromNumber(robotNumber));
        player.setClientID(clientID);

        players.add(player);


        clientIDs.add(clientID);

        if (isThisPlayerOneself(clientID)) {

            isConfirmationButtonDisabled.setValue(true);
            isUsernameTextFieldDisabled.setValue(true);
            isChooseRobotDisabled.setValue(true);
            checkBoxMap.get(robotNumber).setValue(true);

        }

        robotNumberByClientID.put(clientID, robotNumber);
        robotsTaken.add(RobotEnum.getEnumFromNumber(robotNumber));

        playerLabelMap.get(robotNumber).setValue(username);
        clientIDMap.get(robotNumber).setValue(Integer.toString(clientID));
        playerActiveMap.get(robotNumber).setValue(true);

    }

    /**
     * The method handles the protocol PlayerStatus
     *
     * @param playerStatus : PlayerStatus protocol
     */
    public void handlePlayerStatus(PlayerStatus playerStatus) {
        Integer clientID = playerStatus.getClientID();
        int robotNumber = robotNumberByClientID.get(clientID);
        boolean ready = playerStatus.isReady();
        if (ready) {
            playersReady.add(clientID);

            if (playersReady.size() > 1) {
                notEnoughPlayersReady.setValue(false);
            }

        } else {
            playersReady.remove(clientID);
            if (isThisPlayerOneself(clientID)) {
                isSelectMapDisabled.setValue(true);
            }
            if (playersReady.size() < 2) {
                notEnoughPlayersReady.setValue(true);
            }

        }

        readyMap.get(robotNumber).setValue(ready);
    }

    /**
     * The method handles the protocol SelectMap
     *
     * @param selectMap : SelectMap protocol
     */
    public void handleSelectMap(SelectMap selectMap) {
        if (isSelfReadyFirst()) {
            isSelectMapDisabled.setValue(false);
        }
    }

    /**
     * The method handles the protocol MapSelected
     *
     * @param mapSelected : MapSelected protocol
     */
    public void handleMapSelected(MapSelected mapSelected) {
        String map = mapSelected.getMap();
        selectedMap.setValue(map);
    }

    /**
     * The method handles the protocol GameStarted
     *
     * @param gameStarted : GameStarted protocol
     */
    public void handleGameStarted(GameStarted gameStarted) {

        logger.debug("GameStarted in LobbyModel");
        hasGameStartedProperty().setValue(true);
    }

    /**
     * The method checks if a player is OneSelf
     *
     * @param clientID : int clientId
     * @return boolean : true if self
     */
    public boolean isThisPlayerOneself(int clientID) {

        return clientID == client.getClientID();
    }

    /**
     * The method checks if OneSelf is first ready
     *
     * @return boolean : true if ready first
     */
    public boolean isSelfReadyFirst() {

        return playersReady.get(0) == client.getClientID();
    }


    /**
     * The method removes a player from everything
     *
     * @param clientID : int clientID of player removed
     */
    public void removePlayer(int clientID) {
        for (Player player : players) {
            if (player.getClientID() == clientID) {
                players.remove(player);
                Integer clientIDInteger = clientID;
                clientIDs.remove(clientIDInteger);
                robotsTaken.remove(player.getRobotEnum());
                Integer number = player.getRobotEnum().getNumber();
                playerActiveMap.get(number).setValue(false);
                readyMap.get(number).setValue(false);
                checkBoxMap.get(number).setValue(false);
                playerLabelMap.get(number).setValue("Player " + number);
                playersReady.remove(clientIDInteger);

                break;
            }
        }
    }

    /**
     * The method handles everything if the game is finished
     */
    public void handleGameFinished() {
        readyMap.forEach((integer, booleanProperty) -> booleanProperty.setValue(false));

        checkBoxMap.forEach((integer, booleanProperty) -> booleanProperty.setValue(false));
        clientIDMap.forEach(((integer, stringProperty) -> stringProperty.setValue("")));
        playerActiveMap.forEach((integer, booleanProperty) -> booleanProperty.setValue(false));
        playerLabelMap.forEach((integer, stringProperty) -> stringProperty.setValue("Player " + integer));

        isSelfReady.setValue(false);
        isSelectMapDisabled.setValue(true);
        hasGameStarted.setValue(false);
        isConfirmationButtonDisabled.setValue(false);
        isUsernameTextFieldDisabled.setValue(false);
        isChooseRobotDisabled.setValue(false);

        robotsTaken.clear();
        playersReady.clear();
        selectedMap.setValue("");

    }

    /**
     * The method fills the playerLabelMap
     */
    public void fillPlayerLabelMap() {
        playerLabelMap.put(1, namePlayer1);
        playerLabelMap.put(2, namePlayer2);
        playerLabelMap.put(3, namePlayer3);
        playerLabelMap.put(4, namePlayer4);
        playerLabelMap.put(5, namePlayer5);
        playerLabelMap.put(6, namePlayer6);
    }

    /**
     * The method fills the checkBoxMap
     */
    public void fillCheckBoxMap() {
        checkBoxMap.put(1, readyBox1);
        checkBoxMap.put(2, readyBox2);
        checkBoxMap.put(3, readyBox3);
        checkBoxMap.put(4, readyBox4);
        checkBoxMap.put(5, readyBox5);
        checkBoxMap.put(6, readyBox6);
    }

    /**
     * The method fills the readyMap
     */
    public void fillReadyMap() {
        readyMap.put(1, isReadyPlayer1);
        readyMap.put(2, isReadyPlayer2);
        readyMap.put(3, isReadyPlayer3);
        readyMap.put(4, isReadyPlayer4);
        readyMap.put(5, isReadyPlayer5);
        readyMap.put(6, isReadyPlayer6);
    }

    /**
     * The method fills the clientIDMap
     */
    public void fillClientIDMap() {
        clientIDMap.put(1, clientIDPlayer1);
        clientIDMap.put(2, clientIDPlayer2);
        clientIDMap.put(3, clientIDPlayer3);
        clientIDMap.put(4, clientIDPlayer4);
        clientIDMap.put(5, clientIDPlayer5);
        clientIDMap.put(6, clientIDPlayer6);
    }

    /**
     * The method fills the playerActiveMap
     */
    private void fillPlayerActiveMap() {
        playerActiveMap.put(1, isPlayer1Active);
        playerActiveMap.put(2, isPlayer2Active);
        playerActiveMap.put(3, isPlayer3Active);
        playerActiveMap.put(4, isPlayer4Active);
        playerActiveMap.put(5, isPlayer5Active);
        playerActiveMap.put(6, isPlayer6Active);
    }


    public void setPlayer1(Player player1) {
        this.player1.set(player1);
    }

    public ObjectProperty<Player> player2Property() {
        return player2;
    }

    public ObjectProperty<Player> player3Property() {
        return player3;
    }

    public Player getPlayer4() {
        return player4.get();
    }

    public StringProperty namePlayer1Property() {
        return namePlayer1;
    }

    public StringProperty namePlayer2Property() {
        return namePlayer2;
    }

    public StringProperty namePlayer3Property() {
        return namePlayer3;
    }

    public StringProperty namePlayer4Property() {
        return namePlayer4;
    }

    public StringProperty namePlayer5Property() {
        return namePlayer5;
    }

    public StringProperty namePlayer6Property() {
        return namePlayer6;
    }

    public BooleanProperty isReadyPlayer1Property() {
        return isReadyPlayer1;
    }

    public BooleanProperty isReadyPlayer2Property() {
        return isReadyPlayer2;
    }

    public BooleanProperty isReadyPlayer3Property() {
        return isReadyPlayer3;
    }

    public BooleanProperty isReadyPlayer4Property() {
        return isReadyPlayer4;
    }

    public BooleanProperty isReadyPlayer5Property() {
        return isReadyPlayer5;
    }

    public BooleanProperty isReadyPlayer6Property() {
        return isReadyPlayer6;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isIsSelfPlayer() {
        return isSelfPlayer.get();
    }

    public BooleanProperty isConfirmationButtonDisabledProperty() {
        return isConfirmationButtonDisabled;
    }

    public BooleanProperty isUsernameTextFieldDisabledProperty() {
        return isUsernameTextFieldDisabled;
    }

    public BooleanProperty isSelectMapDisabledProperty() {
        return isSelectMapDisabled;
    }

    public HashMap<Integer, StringProperty> getPlayerLabelMap() {
        return playerLabelMap;
    }

    public BooleanProperty isChooseRobotDisabledProperty() {
        return isChooseRobotDisabled;
    }

    public BooleanProperty readyBox1Property() {
        return readyBox1;
    }

    public BooleanProperty readyBox2Property() {
        return readyBox2;
    }

    public BooleanProperty readyBox3Property() {
        return readyBox3;
    }

    public BooleanProperty readyBox4Property() {
        return readyBox4;
    }

    public BooleanProperty readyBox5Property() {
        return readyBox5;
    }

    public BooleanProperty readyBox6Property() {
        return readyBox6;
    }

    public BooleanProperty hasGameStartedProperty() {
        return hasGameStarted;
    }

    public StringProperty selectedMapProperty() {
        return selectedMap;
    }

    public HashMap<Integer, Integer> getRobotNumberByClientID() {
        return robotNumberByClientID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isNotEnoughPlayersReady() {
        return notEnoughPlayersReady.get();
    }

    public String getClientIDPlayer1() {
        return clientIDPlayer1.get();
    }

    public String getClientIDPlayer2() {
        return clientIDPlayer2.get();
    }

    public String getClientIDPlayer3() {
        return clientIDPlayer3.get();
    }

    public String getClientIDPlayer4() {
        return clientIDPlayer4.get();
    }

    public String getClientIDPlayer5() {
        return clientIDPlayer5.get();
    }

    public String getClientIDPlayer6() {
        return clientIDPlayer6.get();
    }

    public HashMap<Integer, StringProperty> getClientIDMap() {
        return clientIDMap;
    }

    public HashMap<Integer, BooleanProperty> getPlayerActiveMap() {
        return playerActiveMap;
    }

}
