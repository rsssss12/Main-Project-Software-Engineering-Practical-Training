package messages;

/**
 * The enumeration MessagesGame contains messages sent in the game
 */
public enum MessagesGame {

    WELCOME_MESSAGE("Welcome to Robo Rally!"),

    CURRENT_PLAYER_OTHER("It's $$username's turn!"),
    CURRENT_PLAYER_YOU("It's your turn!"),
    ACTIVE_PHASE_GAME("$$phase" + " has started!"),


    ENERGY("$$username got $$number energy cubes."),


    GAME_FINISHED("$$username reached all checkpoints and won the game."),


    //Activation Phase
    OWNROBOTMOVEI("Your robot moved one forward."),
    ROBOTMOVEI("$$username's robot moved one forward."),
    OWNROBOTMOVEII("Your robot moved two forward."),
    ROBOTMOVEII("$$username's robot moved two forward."),
    OWNROBOTMOVEIII("Your robot moved three forward."),
    ROBOTMOVEIII("$$username's robot moved three forward."),
    OWNROBOTUTURN("Your robot u-turned."),
    ROBOTUTURN("$$username" + " robot u-turned."),
    OWNROBOTL_TURN("Your robot turned left."),
    ROBOTL_TURN("$$username's robot turned left."),
    OWNROBOTR_TURN("Your robot turned right."),
    ROBOTR_TURN("$$username's robot turned right."),
    OWNROBOTBACKUP("Your robot backed up."),
    ROBOTBACKUP("$$username's robot backed up."),
    OWN_ROBOT_POWER_UP("Your robot got a power-up."),
    ROBOT_POWER_UP("$$username's robot got a power-up."),
    OWN_ROBOT_AGAIN("Your robot repeated the last action."),
    ROBOT_AGAIN("$$username's robot repeated the last action."),

    //Damage Cards
    OWNROBOTSPAM("Your robot executed spam damage."),
    ROBOTSPAM("$$username's robot executed spam damage."),
    OWNROBOTWORM("Your robot executed worm damage."),
    ROBOTWORM("$$username's robot executed worm damage."),
    OWNROBOTVIRUS("Your robot executed virus damage."),
    ROBOTVIRUS("$$username's robot repeated the last card."),
    OWNROBOTTROJAN("Your robot moved to point "),
    ROBOTTROJAN("$$username's robot moved to point "),
    ROBOTGOTDAMAGE("Your robot was affected by a damage card!"),


    RECEIVEDAPRIVATEMESSAGE("$$username" + " sent a private message to you: " + "$$message"),
    RECEIVEDAPUBLICMESSAGE("$$username" + " sent a public message: " + "$$message"),
    YOUSELECTEDCARD("You selected card " + "$$card"),
    SOMEONESELECTEDCARD("$$username" + " selected card " + "$$card"),
    YOURCARDWASPLAYED("Card " + "$$card" + " was played."),
    SOMEONESCARDWASPLAYED("$$username's card " + "$$card" + " was played."),

    CONFIRMCARDREGISTER("Your card register is confirmed."),
    CONFIRMCARDREGISTERTOOTHERS("$$username's card register is confirmed."),
    OWNSELESCTIONFINISHED("You finished your selection! You can't change your cards anymore."),
    SELECTIONFINISHEDTOOTHERS("$$username" + " finished its selection! The player can't change its cards anymore."),


    OWNREBOOTDIRECTION("Your roboter rebooted to the " + "$$direction"),
    REBOOTDIRECTIONOTHERS("$$username's roboter rebooted to the " + "$$direction"),

    ;
    private final String message;

    public String getMessage() {
        return message;
    }



    MessagesGame(String message) {
        this.message = message;
    }

}

