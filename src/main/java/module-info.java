module neidische.narwale.hp {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires com.google.gson;
    requires org.json;
    requires org.apache.logging.log4j;
    requires org.junit.jupiter.api;
    requires javafx.media;



    exports gamelogic;
    exports json.wrapper_utilities;
    exports network;
    exports gui;

//
//    opens gui to javafx.fxml, com.google.gson;
//    opens gamelogic to javafx.fxml, com.google.gson;
//    opens json.json_wrappers to javafx.fxml, com.google.gson;
//    opens network to javafx.fxml, com.google.gson;
//    opens test to javafx.fxml, com.google.gson;

    opens gamelogic;
    opens json.wrapper_utilities;
    opens network;

    opens gui;


    exports json.json_wrappers.chat;
    opens json.json_wrappers.chat;

//    exports json.json_wrappers.network;
//    opens json.json_wrappers.network;

    exports json.json_wrappers.cards;
    opens json.json_wrappers.cards;

    exports json.json_wrappers.actions_events_effects;
    opens json.json_wrappers.actions_events_effects;

    exports json.json_wrappers.round;
    opens json.json_wrappers.round;

    exports json.json_wrappers.connection;
    opens json.json_wrappers.connection;

    exports json.json_wrappers.lobby;
    opens json.json_wrappers.lobby;

    exports gamelogic.boardElements;
    opens gamelogic.boardElements;


    exports gamelogic.cards;
    opens gamelogic.cards;

    exports gamelogic.lobby;
    opens gamelogic.lobby;

    exports gamelogic.cards.programmingCards;
    opens  gamelogic.cards.programmingCards;

    exports gamelogic.round;
    opens gamelogic.round;

    exports gamelogic.maps;
    opens gamelogic.maps;

    exports json.json_wrappers.special_messages;
    opens json.json_wrappers.special_messages;


    exports json.json_wrappers.round.activation_phase;
    opens json.json_wrappers.round.activation_phase;

    exports json.json_wrappers.round.setup_phase;
    opens json.json_wrappers.round.setup_phase;

    exports json.json_wrappers.round.programming_phase;
    opens json.json_wrappers.round.programming_phase;

    exports gamelogic.cards.damageCards;
    opens gamelogic.cards.damageCards;

    //    exports launcher;
//    opens launcher;

    exports json.adapters;
    opens json.adapters;

    exports gui.mvvm.connection;
    opens gui.mvvm.connection;

    exports gui.mvvm.lobby;
    opens gui.mvvm.lobby;
    exports gui.mvvm.chat;
    opens gui.mvvm.chat;
    exports gui.mvvm.game;
    opens gui.mvvm.game;
    exports gui.mvvmutility;
    opens gui.mvvmutility;
//    exports jsonfiles;
    opens jsonfiles;
    //    opens gui.fonts;
//    opens gui.Images.cards;
    opens gui.Images.damage_and_reboots;
    opens gui.Images.cards.programming_cards;
    opens gui.Images.cards.special_programming_cards;
    opens gui.Images.basic_layout;
    opens gui.Images.board_elements;
    opens gui.Images.robots;
    opens gui.Images.player_fields;
    opens gui.Images.sketches;
    opens gui.Images.maps;



}