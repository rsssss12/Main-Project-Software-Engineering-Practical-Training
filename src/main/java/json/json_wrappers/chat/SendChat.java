package json.json_wrappers.chat;

import json.wrapper_utilities.WrapperClass;

/**
 * The class SendChat is for json wrapping the protocol SendChat
 *
 * @author Tim Kriegelsteiner
 */
public class SendChat extends WrapperClass {

    private String message;
    private int to;

    /**
     * constructor of SendChat
     *
     * @param message : message to send
     * @param to      : int ID of receiver
     */
    public SendChat(String message, int to) {
        this.message = message;
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
