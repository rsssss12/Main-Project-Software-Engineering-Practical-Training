package json.wrapper_utilities;

import gamelogic.cards.Card;
import gamelogic.cards.CardEnum;
import gamelogic.cards.damageCards.Spam;
import gamelogic.cards.damageCards.TrojanHorse;
import gamelogic.cards.damageCards.Virus;
import gamelogic.cards.damageCards.Worm;
import gamelogic.cards.programmingCards.*;
import gamelogic.cards.specialProgrammingCards.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * The class CardWrapping wraps the card
 *
 * @author Tim Kriegelsteiner
 */
public class CardWrapping {

    /**
     * The method returns the class of the String of the card
     *
     * @param card : String card
     * @return Card : Card
     */
    public static Card selectCard(String card) {

        if (card != null) {
            switch (CardEnum.valueOf(card.toUpperCase())) {

                case SPAM -> {
                    return new Spam();
                }
                case TROJAN -> {
                    return new TrojanHorse();
                }
                case VIRUS -> {
                    return new Virus();
                }
                case WORM -> {
                    return new Worm();
                }
                case AGAIN -> {
                    return new Again();
                }
                case BACKUP -> {
                    return new BackUp();
                }
                case TURNLEFT -> {
                    return new LeftTurn();
                }
                case MOVEI -> {
                    return new MoveOne();
                }
                case MOVEIII -> {
                    return new MoveThree();
                }
                case MOVEII -> {
                    return new MoveTwo();
                }
                case POWERUP -> {
                    return new PowerUp();
                }
                case TURNRIGHT -> {
                    return new RightTurn();
                }
                case UTURN -> {
                    return new UTurn();
                }
                case ENERGYROUTINE -> {
                    return new EnergyRoutine();
                }
                case REPEATROUTINE -> {
                    return new RepeatRoutine();
                }
                case SANDBOXROUTINE -> {
                    return new SandboxRoutine();
                }
                case SPAMFOLDER -> {
                    return new SpamFolder();
                }
                case SPEEDROUTINE -> {
                    return new SpeedRoutine();
                }
                case WEASELROUTINE -> {
                    return new WeaselRoutine();
                }
            }
        }


        return null;
    }

    /**
     * The method takes a String list and returns list of type Card
     *
     * @param strings : T strings
     * @return ArrayList<Card>: list of cards
     */
    public static <T extends List<String>> ArrayList<Card> StringListToCardArrayList(T strings) {
        ArrayList<Card> cards = new ArrayList<>();

        for (String string : strings) {
            cards.add(selectCard(string));
        }

        return cards;
    }

    /**
     * The method creates a HashMap from Strings to card
     *
     * @param currentCards: String
     * @return HashMap<Integer, Card>
     */
    public static HashMap<Integer, Card> hashMapStringToCard(HashMap<Integer, String> currentCards) {
        HashMap<Integer, Card> cardHashMap = new HashMap<>();

        for (int entry : currentCards.keySet()) {

            Card card = selectCard(currentCards.get(entry));

            cardHashMap.put(entry, card);
        }

        return cardHashMap;
    }

    /**
     * The method list to a String Array List
     *
     * @param cards: T cards
     * @return T extends List<Card>> ArrayList<String>
     */
    public static <T extends List<Card>> ArrayList<String> cardListToStringArrayList(T cards) {

        List<String> strings = cards.stream()
                .map(object -> Objects.toString(object, null)).toList();

        return new ArrayList<>(strings);
    }
}
