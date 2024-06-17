package messages;

/**
 * The enumeration MessagesError contains various error messages
 */
public enum MessagesError {

    ERROR_ERROR("Whoops. That did not work. Try to adjust something."),

    PLAYERADDED_ERROR("Robot has already been taken."),

    SET_STARTING_POINT_ERROR("This Starting Point has already been taken."),
    CHOOSEMAP_ERROR("You are not allowed to choose a Map!"),
    MAP_ERROR("This map is not available!"),
    CONNECTION_ERROR("We are unable to connect you! Please check your connection and settings."),
    DISCONNECT_ERROR("₦username" + " lost the connection! The figure is being removed from the map."),
    OWNDISCONNECT_ERROR("You lost the connection to the game! Your figure is now being removed from the map."),
    DISCONNECTLOBBY_ERROR(" lost the connection!"),
    SENDPRIVATEMESSAGE_ERROR("There is no Player with such ClientID!"),
    OWNDISCONNECTLOBBY_ERROR("You lost the connection!"),
    TURN_ERROR("It is not your turn!"),
    TIME_ERROR("Your time is up!"),
    CHECKPOINT_ERROR("You have missed a previous checkpoint. Go there and come back later."),


    CLIENTID_ERROR("ClientID has already been taken."),//Actually I don't think it's necessary, because a client gets it from the server

    ACTIVEPHASE_ERROR("You are not in the right Phase to do this."),

    CURRENTPLAYER_ERROR("It's not your turn!"),

    ;

    private final String message;

    /**
     * constructor of MessagesError
     *
     * @param message: String error message
     */
    MessagesError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static String returnError(MessagesError messageserror) {
        return messageserror.toString();
    }

    public static String returnErrorWithUsername(MessagesError messageserror, String username) {
        return messageserror.toString();
    }
}
