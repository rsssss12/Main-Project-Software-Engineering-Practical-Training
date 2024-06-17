package messages;

/**
 * The enumeration MessagesSetupPhase contains messages sent in the setup phase
 */
public enum MessagesSetupPhase {
    GAME_STARTED_MESSAGE1("Good evening Ladies and Gentlemen to another exciting match of 'Robo Rally'!"),
    GAME_STARTED_MESSAGE2("My name is Robert Rally and I will guide you through this breathtaking event!"),
    GAME_STARTED_MESSAGE3("Are you ready to rummmble?!"),

    SET_STARTING_POINT_YOU("Select a Starting Point!"),
    SET_STARTING_POINT_OTHER("$$username selects a starting point."),

    STARTING_POINT_TAKEN_YOU("You chose the starting point at postion x: $$x, y: $$y successfully."),
    STARTING_POINT_TAKEN_OTHER("$$username chose the starting point at postion x: $$x, y: $$y successfully."),



    ;


    private final String message;

    MessagesSetupPhase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
