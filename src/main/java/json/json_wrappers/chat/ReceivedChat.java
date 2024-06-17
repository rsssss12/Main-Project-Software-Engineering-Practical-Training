package json.json_wrappers.chat;

import json.wrapper_utilities.WrapperClass;

/**
 * The class ReceivedChat is for json wrapping the protocol ReceivedChat
 */
public class ReceivedChat extends WrapperClass {

    private String message;
    private int from;
    private boolean isPrivate;

    /**
     * constructor of ReceivedChat
     *
     * @param message   : String message sent
     * @param from      : int sender of message
     * @param isPrivate : boolean if private message
     */
    public ReceivedChat(String message, int from, boolean isPrivate) {
        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
