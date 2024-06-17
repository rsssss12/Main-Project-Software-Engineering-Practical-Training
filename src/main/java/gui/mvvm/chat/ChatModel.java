package gui.mvvm.chat;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import json.json_wrappers.chat.ReceivedChat;
import network.Client;

import java.util.ArrayList;

/**
 * The class ChatModel
 *
 * @author Hannah Spierer
 */
public class ChatModel {

    private static volatile ChatModel chatModel;


    private final ArrayList<String> chatHistory = new ArrayList<>();
    private final ArrayList<String> senders = new ArrayList<>();
    private final ArrayList<Boolean> privateMessages = new ArrayList<>();
    private IntegerProperty counter;

    private Client client;


    public ChatModel() {

    }

    public static ChatModel getInstance() {
        if (chatModel == null) {
            synchronized (ChatModel.class) {
                if (chatModel == null) {
                    chatModel = new ChatModel();

                }
            }
        }
        return chatModel;
    }


    /**
     * The method handles the protocol ReceivedChat
     *
     * @param receivedChat : ReceivedChat protocol
     */
    public void handleReceivedChat(ReceivedChat receivedChat) {
        String message = receivedChat.getMessage();
        int sender = receivedChat.getFrom();
        boolean isPrivateMessage = receivedChat.isPrivate();

        addMessage(isPrivateMessage, String.valueOf(sender), message);
        setCounter(getCounter() + 1);

    }

    /**
     * The method adds a message to ArrayLists
     *
     * @param privateMessage : boolean : true if private message
     * @param id             : String clientID
     * @param text           : String message text
     */
    public void addMessage(boolean privateMessage, String id, String text) {
        this.senders.add(id);
        this.chatHistory.add(text);
        this.privateMessages.add(privateMessage);
    }

    /**
     * The method contains a counter
     * It starts with 0
     * @return IntegerProperty :
     */
    public IntegerProperty counterProperty() {
        if (this.counter == null)
            this.counter = new SimpleIntegerProperty(0);
        return this.counter;
    }


    public final int getCounter() {
        if (this.counter == null)
            return 0;
        return this.counter.get();
    }

    public final void setCounter(int val) {
        this.counter.set(val);
    }


    public ArrayList<String> getChatHistory() {
        return chatHistory;
    }

    public ArrayList<String> getSenders() {
        return senders;
    }

    public ArrayList<Boolean> getPrivateMessages() {
        return privateMessages;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
