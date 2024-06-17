package messages;

/**
 * The enumeration MessagesProgrammingPhase contains messages sent in the programming phase
 */
public enum MessagesProgrammingPhase {

    SHUFFLE_CODING("Your cards were shuffled."),
    SELECTED_CARD_FULL("You put $$card in register $$number"),
    SELECTED_CARD_EMPTY("You removed a card from register $$number"),

    CARD_SELECTED_FULL("$$username put a card in register $$number"),
    CARD_SELECTED_EMPTY("$$username removed a card in register $$number"),

    NOT_YOUR_CARDS("$$username got $$number cards."),

    REGISTER_FINISHED_YOU("You filled your registers and can't change them anymore."),
    REGISTER_FINISHED_OTHER("$$username filled the registers."),

    TIMER_STARTED("The timer has started!"),
    TIMER_ENDED("Time's up. "),
    TIMER_ENDED_MULTIPLE("$$usernames weren't fast enough."),
    TIMER_ENDED_SINGLE("$$username wasn't fast enough."),
    TIMER_ENDED_NONE("Everyone was fast enough!"),

    CARDS_YOU_GOT_NOW("The cards $$cards filled the remaining registers"),

    YOUR_CARDS("You got all your cards! Drag them into your registers!");


    private final String message;

    MessagesProgrammingPhase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
