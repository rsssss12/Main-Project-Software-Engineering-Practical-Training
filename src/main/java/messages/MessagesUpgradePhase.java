package messages;

/**
 * The enumeration MessagesUpgradePhase contains messages sent in the upgrade phase
 */
public enum MessagesUpgradePhase {
    OWNENERGYROUTINE("Your robot executed EnergyRoutine"),
    ENERGYROUTINE("₦username" + "s robot executed EnergyRoutine"),
    OWNSANDBOXROUTINE("Your robot executed SandboxRoutine"),
    SANDBOXROUTINE("₦username" + "s robot executed SandboxRoutine"),
    OWNWEASELROUTINE("Your robot executed WeaselRoutine"),
    WEASELROUTINE("₦username" + "s robot executed WeaselRoutine"),
    OWNSPEEDROUTINE("Your robot executed SpeedRoutine"),
    SPEEDROUTINE("₦username" + "s robot executed SpeedRoutine"),
    OWNSPAMROUTINE("Your robot executed SpamRoutine"),
    SPAMROUTINE("₦username" + "s robot executed SpamRoutine"),
    OWNREPEATROUTINE("Your robot executed RepeatRoutine"),
    REPEATROUTINE("₦username" + "s robot executed RepeatRoutine"),

    ;

    private final String message;

    MessagesUpgradePhase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
