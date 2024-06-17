package json.wrapper_utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * The class Serializer serializes the json
 */
public class Serializer {
    /**
     * The method serialize the packageBody of a json File
     *
     * @param packageBody: WrapperClass
     * @return: String
     */
    public static String serializeData(WrapperClass packageBody) {

        return serializeDataPackage(
                serializeMessageType(packageBody),
                serializeMessageBody(packageBody)
        );

    }

    /**
     * The method serialize the message Body of the json file
     *
     * @param messageBody: WrapperClass
     * @return: JsonObject
     */
    public static JsonObject serializeMessageBody(WrapperClass messageBody) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(messageBody);

        return gson.fromJson(json, JsonObject.class);
    }

    /**
     * The method serialize the message Type
     *
     * @param messageType: WrapperClass
     * @return: String
     */
    public static String serializeMessageType(WrapperClass messageType) {
        Gson gson = new GsonBuilder().create();
        return messageType.getClass().getSimpleName();
    }

    /**
     * The method seriazlize the dataPackage
     *
     * @param messageType: String messageType
     * @param messageBody: JsonObject messageBody
     * @return: String
     */
    public static String serializeDataPackage(String messageType, JsonObject messageBody) {
        Gson gson = new GsonBuilder().create();
        DataPackage dataPackage = new DataPackage(messageType, messageBody);

        return gson.toJson(dataPackage);
    }

}
