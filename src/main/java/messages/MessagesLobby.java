package messages;

/**
 * The enumeration MessagesLobby contains messages sent in the lobby
 */
public enum MessagesLobby {

    OWNPLAYERADDED("You were successfully added."),
    PLAYERADDED("₦username" + " was successfully added."),
    OWNSTATUSCHANGED("You changed your status."),
    STATUSCHANGED("₦username" + " changed its status."),
    OWNSTATUSWASCHANGED("Your status was successfully changed."),
    STATUSWASCHANGED("₦username" + " status was successfully changed."),
    CHOOSEROBOT("Your robot choice was sent."),
    CONFIRMROBOT("Your robot choice was accepted."),
    SENDREADINESS("You declared your readiness."),
    REVOKEREADINESS("You revoked your readiness."),
    SENDREADINESSTOOTHERS("₦username" + " declared its readiness."),
    REVOKEREADINESSTOOTHERS("₦username" + " revoked its readiness."),
    PERMISSIONFORMAP("You have the permission to choose a map."),
    PERMISSIONFORMAPOTHERS("₦username" + " has the permission to choose a map."),
    CHOSEMAP("You chose map " + "₵map"),
    ANOTHERPLAYERCHOSEMAP("₦username" + " chose map " + "₵map"),
    YOUSETYOURPLAYERVALUES("You set your player values."),
    PLAYERVALUES("₦username" + "set its player values."),
    ;


    private final String message;

    MessagesLobby(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
