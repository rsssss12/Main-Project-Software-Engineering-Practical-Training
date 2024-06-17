package gamelogic.cards;

/**
 * The enumeration CardEnum contains all cards
 */
public enum CardEnum {

    //damageCards
    SPAM("gui/Images/damage_and_reboots/spam.png"),
    TROJAN("gui/Images/damage_and_reboots/trojan horse.png"),
    VIRUS("gui/Images/damage_and_reboots/virus.png"),
    WORM("gui/Images/damage_and_reboots/worm.png"),

    //programmingCards
    AGAIN("gui/Images/cards/programming_cards/again_blue.png"),
    BACKUP("gui/Images/cards/programming_cards/move_back_blue.png"),
    TURNLEFT("gui/Images/cards/programming_cards/left_turn_blue.png"),
    MOVEI("gui/Images/cards/programming_cards/move_1_blue.png"),
    MOVEIII("gui/Images/cards/programming_cards/move_3_blue.png"),
    MOVEII("gui/Images/cards/programming_cards/move_2_blue.png"),
    POWERUP("gui/Images/cards/programming_cards/power_up_blue.png"),
    TURNRIGHT("gui/Images/cards/programming_cards/right_turn_blue.png"),
    UTURN("gui/Images/cards/programming_cards/u-turn_blue.png"),

    //specialProgrammingCards
    ENERGYROUTINE(""), REPEATROUTINE(""), SANDBOXROUTINE(""), SPAMFOLDER(""), SPEEDROUTINE(""), WEASELROUTINE(""),
    ;

    //temporaryUpgradeCards


    //permanentUpgradeCards


    private final String filePath;

    CardEnum(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
