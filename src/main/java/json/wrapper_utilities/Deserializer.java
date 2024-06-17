package json.wrapper_utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.json_wrappers.actions_events_effects.*;
import json.json_wrappers.chat.ReceivedChat;
import json.json_wrappers.chat.SendChat;
import json.json_wrappers.connection.*;
import json.json_wrappers.cards.*;
import json.json_wrappers.lobby.*;
import json.json_wrappers.round.*;
import json.json_wrappers.round.activation_phase.CurrentCards;
import json.json_wrappers.round.activation_phase.ReplaceCard;
import json.json_wrappers.round.programming_phase.*;
import json.json_wrappers.round.setup_phase.SetStartingPoint;
import json.json_wrappers.round.setup_phase.StartingPointTaken;
import json.json_wrappers.special_messages.*;
import json.json_wrappers.special_messages.Error;

/**
 * The class Deserializer deserializes the json sent
 */
public class Deserializer {

    /**
     * The method deserialize the data
     *
     * @param json: String: json
     * @return: Wrapper Class: deserialize message
     */
    public static WrapperClass deserializeData(String json) {

        return deserializeMessageBody(
                deserializeDataPackage(json));
    }

    /**
     * The method deserialize the data package
     *
     * @param json: String: json
     * @return: Data Package
     */
    public static DataPackage deserializeDataPackage(String json) {
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(json, DataPackage.class);
    }

    /**
     * The method deserialize the message body
     * @param dataPackage: DataPackage
     * @return: Wrapper Class
     */
    public static WrapperClass deserializeMessageBody(DataPackage dataPackage) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        String messageType = dataPackage.getMessageType();


        if (messageType != null) {

            switch (MessageTypes.valueOf(messageType.toUpperCase())) {

                case ANIMATION -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Animation.class);
                }
                case CHECKPOINTREACHED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CheckPointReached.class);
                }
                case ENERGY -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Energy.class);
                }
                case GAMEFINISHED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), GameFinished.class);
                }
                case MOVEMENT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Movement.class);
                }
                case PLAYERTURNING -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PlayerTurning.class);
                }
                case REBOOT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Reboot.class);
                }
                case REBOOTDIRECTION -> {
                    return gson.fromJson(dataPackage.getMessageBody(), RebootDirection.class);
                }
                case DRAWDAMAGE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), DrawDamage.class);
                }
                case CARDPLAYED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CardPlayed.class);
                }
                case PLAYCARD -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PlayCard.class);
                }
                case RECEIVEDCHAT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), ReceivedChat.class);
                }
                case SENDCHAT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SendChat.class);
                }
                case ALIVE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Alive.class);
                }
                case HELLOCLIENT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), HelloClient.class);
                }
                case HELLOSERVER -> {
                    return gson.fromJson(dataPackage.getMessageBody(), HelloServer.class);
                }
                case WELCOME -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Welcome.class);
                }
                case GAMESTARTED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), GameStarted.class);
                }
                case MAPSELECTED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), MapSelected.class);
                }
                case PLAYERADDED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PlayerAdded.class);
                }
                case PLAYERSTATUS -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PlayerStatus.class);
                }
                case PLAYERVALUES -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PlayerValues.class);
                }
                case SELECTMAP -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SelectMap.class);
                }
                case SETSTATUS -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SetStatus.class);
                }
                case CURRENTCARDS -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CurrentCards.class);
                }
                case REPLACECARD -> {
                    return gson.fromJson(dataPackage.getMessageBody(), ReplaceCard.class);
                }
                case CARDSELECTED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CardSelected.class);
                }
                case CARDSYOUGOTNOW -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CardsYouGotNow.class);
                }
                case NOTYOURCARDS -> {
                    return gson.fromJson(dataPackage.getMessageBody(), NotYourCards.class);
                }
                case SELECTEDCARD -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SelectedCard.class);
                }
                case SELECTIONFINISHED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SelectionFinished.class);
                }
                case SHUFFLECODING -> {
                    return gson.fromJson(dataPackage.getMessageBody(), ShuffleCoding.class);
                }
                case TIMERENDED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), TimerEnded.class);
                }
                case TIMERSTARTED -> {
                    return gson.fromJson(dataPackage.getMessageBody(), TimerStarted.class);
                }
                case YOURCARDS -> {
                    return gson.fromJson(dataPackage.getMessageBody(), YourCards.class);
                }
                case SETSTARTINGPOINT -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SetStartingPoint.class);
                }
                case STARTINGPOINTTAKEN -> {
                    return gson.fromJson(dataPackage.getMessageBody(), StartingPointTaken.class);
                }
                case ACTIVEPHASE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), ActivePhase.class);
                }
                case CURRENTPLAYER -> {
                    return gson.fromJson(dataPackage.getMessageBody(), CurrentPlayer.class);
                }
                case ERROR -> {
                    return gson.fromJson(dataPackage.getMessageBody(), Error.class);
                }
                case CONNECTIONUPDATE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), ConnectionUpdate.class);
                }
                case PICKDAMAGE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), PickDamage.class);
                }
                case SELECTEDDAMAGE -> {
                    return gson.fromJson(dataPackage.getMessageBody(), SelectedDamage.class);
                }

            }
        }
        return null;
    }

}
