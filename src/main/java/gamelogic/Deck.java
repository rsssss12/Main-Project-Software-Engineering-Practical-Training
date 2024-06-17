package gamelogic;


import java.util.ArrayList;
import java.util.Random;

/**
 * The class Deck provides general methods for card decks
 * @author Tim Kriegelsteiner
 * @param <Card> : Deck is from type Card
 */
public class Deck<Card> extends ArrayList<Card> {


    /**
     * The method shuffles a deck of cards
     *
     * @param pile : Deck<T>
     */
    public <T extends Card> void shuffleDeck(Deck<T> pile) {
        this.addAll(pile);
        pile.clear();
    }

    /**
     * The method draws a random card from a specific deck
     *
     * @param cards : Deck<T>
     * @return Card : card drawn
     */
    public <T extends Card> T drawCardFrom(Deck<T> cards) {

        T card = cards.getRandomizedCard();

        this.add(card);
        cards.remove(card);

        return card;
    }

    /**
     * The method draws a card from top
     *
     * @return Card : card drawn
     */
    public Card drawCardFromTop() {
        Card card = this.get(0);
        this.remove(0);
        return card;
    }

    /**
     * The method returns a randomized card
     *
     * @return Card : random card
     */
    public Card getRandomizedCard() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.size());

        return this.get(randomIndex);
    }

    /**
     * The method checks, if the deck is empty
     *
     * @return boolean : true if empty
     */
    public boolean isDeckEmpty() {
        return this.size() == 0;
    }


    /**
     * The method creates a specific card in a specific amount
     *
     * @param card  : Card
     * @param times : int amount of cards
     */
    public void createCard(Card card, int times) {
        for (int start = 0; start < times; start++) {
            this.add(card);
        }
    }
}
