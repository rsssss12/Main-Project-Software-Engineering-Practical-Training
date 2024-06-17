
package json.wrapper_utilities;


import json.json_wrappers.actions_events_effects.PickDamage;
import json.json_wrappers.actions_events_effects.RebootDirection;
import json.json_wrappers.cards.PlayCard;
import json.json_wrappers.chat.SendChat;
import json.json_wrappers.connection.Alive;
import json.json_wrappers.lobby.MapSelected;
import json.json_wrappers.lobby.PlayerValues;
import json.json_wrappers.lobby.SetStatus;
import json.json_wrappers.round.programming_phase.SelectedCard;
import json.json_wrappers.round.setup_phase.SetStartingPoint;
import network.ClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class MessageHandlerServer handles the messages sent to the server
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class MessageHandlerServer {
    private static final Logger logger = LogManager.getLogger(MessageHandlerServer.class.getName());

    /**
     * The method handle the different messages which sends via json protocol
     *
     * @param wrapperClass:  WrapperClass
     * @param clientHandler: ClientHandler
     */
    public void handleMessages(WrapperClass wrapperClass, ClientHandler clientHandler) {

        if (!wrapperClass.getClass().equals(Alive.class)) {
            logger.debug(wrapperClass);
        }

        String messageType = wrapperClass.getClass().getSimpleName().toUpperCase();

        switch (MessageTypes.valueOf(messageType)) {

            case ANIMATION -> {
            }
            case REBOOT -> {
            }
            case REBOOTDIRECTION -> {
                assert wrapperClass instanceof RebootDirection;
                RebootDirection rebootDirection = (RebootDirection) wrapperClass;

                clientHandler.getNetworkManager().getLobby().getGame().handleRebootDirection(rebootDirection);

            }
            case PLAYCARD -> {
                assert wrapperClass instanceof PlayCard;
                PlayCard playCard = (PlayCard) wrapperClass;

                String json = WrapperClassSerialization.serializeCardPlayed(playCard, clientHandler);
                clientHandler.sendMessageToAllClients(json);

            }
            case SENDCHAT -> {
                assert wrapperClass instanceof SendChat;
                SendChat sendChat = (SendChat) wrapperClass;
                String json = WrapperClassSerialization.serializeReceivedChat(sendChat, clientHandler);

                if (sendChat.getTo() == -1) {
                    clientHandler.sendMessageToAllClients(json);
                } else {
                    clientHandler.sendMessageToSpecificClient(json, sendChat.getTo());
                }
            }
            case ALIVE -> {
                clientHandler.setAliveReceived(true);
                clientHandler.setAliveReceivedAt(System.currentTimeMillis());
            }
            case HELLOSERVER -> {
            }
            case GAMESTARTED -> {
            }
            case MAPSELECTED -> {
                assert wrapperClass instanceof MapSelected;
                MapSelected mapSelected = (MapSelected) wrapperClass;

                clientHandler.sendMessageToAllClients(Serializer.serializeData(mapSelected));
                clientHandler.getNetworkManager().getLobby().handleMapSelected(mapSelected, clientHandler);
            }
            case PLAYERADDED -> {
            }
            case PLAYERVALUES -> {
                assert wrapperClass instanceof PlayerValues;
                PlayerValues playerValues = (PlayerValues) wrapperClass;

                clientHandler.getNetworkManager().getLobby().handlePlayerValues(playerValues, clientHandler);
            }
            case SETSTATUS -> {
                assert wrapperClass instanceof SetStatus;
                SetStatus setStatus = (SetStatus) wrapperClass;

                clientHandler.getNetworkManager().getLobby().handleSetStatus(setStatus, clientHandler);

            }
            case SELECTEDCARD -> {
                assert wrapperClass instanceof SelectedCard;
                SelectedCard selectedCard = (SelectedCard) wrapperClass;

                clientHandler.getNetworkManager().getLobby().getGame().getProgrammingPhase().handleSelectCard(selectedCard, clientHandler);
            }
            case SETSTARTINGPOINT -> {
                assert wrapperClass instanceof SetStartingPoint;
                SetStartingPoint setStartingPoint = (SetStartingPoint) wrapperClass;

                clientHandler.getNetworkManager().getLobby().getGame().getSetupPhase().handleSetStartingPoint(setStartingPoint, clientHandler);
            }
            case PICKDAMAGE -> {
                assert wrapperClass instanceof PickDamage;
                PickDamage pickDamage = (PickDamage) wrapperClass;

                clientHandler.getNetworkManager().getLobby().getGame().handlePickDamage(pickDamage);
            }
            case SELECTEDDAMAGE -> {
            }
        }
    }


}
