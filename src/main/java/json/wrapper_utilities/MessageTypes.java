package json.wrapper_utilities;

/**
 * The enumeration MessageTypes contains all message types from the protocol
 */
public enum MessageTypes {

    //action_events_effects

    ANIMATION,
    CHECKPOINTREACHED,
    ENERGY,
    GAMEFINISHED,
    MOVEMENT,
    PLAYERTURNING,
    REBOOT,
    REBOOTDIRECTION,

    DRAWDAMAGE,
    PICKDAMAGE,
    SELECTEDDAMAGE,

    //cards
    CARDPLAYED,
    PLAYCARD,

    //chat
    RECEIVEDCHAT,
    SENDCHAT,

    //connection
    ALIVE,
    HELLOCLIENT,
    HELLOSERVER,
    WELCOME,

    //lobby
    GAMESTARTED,
    MAPSELECTED,
    PLAYERADDED,
    PLAYERSTATUS,
    PLAYERVALUES,
    SELECTMAP,
    SETSTATUS,

    //round
    CURRENTCARDS,
    REPLACECARD,
    CARDSELECTED,
    CARDSYOUGOTNOW,
    NOTYOURCARDS,
    SELECTEDCARD,
    SELECTIONFINISHED,
    SHUFFLECODING,
    TIMERENDED,
    TIMERSTARTED,
    YOURCARDS,
    SETSTARTINGPOINT,
    STARTINGPOINTTAKEN,
    ACTIVEPHASE,
    CURRENTPLAYER,

    //special_messages
    ERROR,
    CONNECTIONUPDATE,


}
