package messages;

/**
 * The enumeration MessagesActivationPhase contains messages sent in the activation phase
 */
public enum MessagesActivationPhase {

    CURRENT_CARDS("$$username has $$card in the activated register."),

    REPLACE_CARD("The card in $$username's register $$number was replaced by $$card."),

    MOVEMENT("$$username's robot moved to position x: $$x, y: $$y."),
    PLAYER_TURNING("$$username's robot turned $$rotation."),

    DRAW_DAMAGE("$$username got $$cards."),

    ANIMATION("$$boardElements are activated."),


    REBOOT("$$username's robot rebooted and is down for the rest of this Activation Phase."),

    REACHED_CHECKPOINT("$$username reached checkpoint $$number."),



    //Action

    //Programming Cards

    //Damage Cards

    ;


    private final String message;


    MessagesActivationPhase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
