package json.wrapper_utilities;

import com.google.gson.JsonObject;

/**
 * The class DataPackage represents the structure of the json to send
 */
public class DataPackage {
    private final String messageType;
    private final JsonObject messageBody;

    /**
     * constructor of DataPackage
     *
     * @param messageType: String
     * @param messageBody: JsonObject
     */
    public DataPackage(String messageType, JsonObject messageBody) {
        this.messageType = messageType;
        this.messageBody = messageBody;
    }

    public JsonObject getMessageBody() {
        return messageBody;
    }

    public String getMessageType() {
        return messageType;
    }


}
