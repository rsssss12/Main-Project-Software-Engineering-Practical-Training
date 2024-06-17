package gamelogic;

import gamelogic.cards.Card;
import gamelogic.cards.damageCards.*;
import gamelogic.cards.programmingCards.*;
import gamelogic.cards.upgradeCards.UpgradeCard;
import gamelogic.round.Game;

/**
 * The class PlayerMat contains the decks, energy cubes and checkpoints of one specific player
 * @author Tim Kriegelsteiner
 */
public class PlayerMat {

    private final Deck<Card> programmingCardDeck = new Deck<>();
    private int energyReserve = 5;
    private int checkpointsReached = 0;
    private int currentRegister;
    private final Card[] register = new Card[5];
    private final Deck<Card> discardPile = new Deck<>();

    private final Deck<UpgradeCard> temporaryUpgradeCards = new Deck<>();
    private final Deck<UpgradeCard> permanentUpgradeCards = new Deck<>();

    /**
     * constructor of playerMat
     * fills the deck with programming cards
     */
    public PlayerMat() {
        fillProgrammingCardDeck();
    }


    /**
     * The method gets the card of a register position
     *
     * @param position : int register number
     * @return Card : card on this register
     */
    public Card getRegisterAtPosition(int position) {

        return register[position];
    }


    /**
     * The method initializes the programming card deck
     */
    private void fillProgrammingCardDeck() {

        programmingCardDeck.createCard(new MoveOne(), 5);
        programmingCardDeck.createCard(new MoveTwo(), 3);
        programmingCardDeck.createCard(new MoveThree(), 1);
        programmingCardDeck.createCard(new RightTurn(), 3);
        programmingCardDeck.createCard(new LeftTurn(), 3);
        programmingCardDeck.createCard(new UTurn(), 1);
        programmingCardDeck.createCard(new BackUp(), 1);
        programmingCardDeck.createCard(new PowerUp(), 1);
        programmingCardDeck.createCard(new Again(), 2);

    }

    /**
     * The method determines if there is a card on the register
     *
     * @param card : Card
     * @return boolean : true if no card
     */
    public boolean isRegisterEmpty(Card card) {
        return card == null;
    }

    /**
     * The method fills the register with the card
     *
     * @param card           : Card
     * @param registerNumber : int register number
     */
    public void fillRegister(Card card, int registerNumber) {
        register[registerNumber - 1] = card;
    }

    /**
     * The method determines if the register is completely filled
     *
     * @return boolean : true if filled
     */
    public boolean isProgrammingComplete() {
        for (Card card : register) {
            if (card == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method removes a card from the register and puts it back in deck
     *
     * @param game           : current Game
     * @param registerNumber : int number of register
     */
    public void cleanSingleRegister(Game game, int registerNumber) {
        Card card = register[registerNumber];

        if (card instanceof DamageCard) {

            if (card instanceof Spam) {
                game.getBoard().getSpamDeck().add((Spam) card);
            } else if (card instanceof TrojanHorse) {
                game.getBoard().getTrojanHorseDeck().add((TrojanHorse) card);
            } else if (card instanceof Virus) {
                game.getBoard().getVirusDeck().add((Virus) card);
            } else if (card instanceof Worm) {
                game.getBoard().getWormDeck().add((Worm) card);
            }
        } else if (card != null) {
            discardPile.add(card);
        }

        register[registerNumber] = null;
    }

    /**
     * The method cleans all registers
     */
    public void cleanRegisters() {
        int registerNumber = 0;
        for (Card card : register) {
            if (card != null) {
                discardPile.add(card);
            }
            register[registerNumber] = null;

            registerNumber++;
        }

    }


    public Deck<Card> getProgrammingCardDeck() {
        return programmingCardDeck;
    }

    public int getEnergyReserve() {
        return energyReserve;
    }

    public void setEnergyReserve(int energyReserve) {
        this.energyReserve = energyReserve;
    }

    public int getCheckpointsReached() {
        return checkpointsReached;
    }

    public void setCheckpointsReached(int checkpointsReached) {
        this.checkpointsReached = checkpointsReached;
    }

    public Card[] getRegister() {
        return register;
    }

    public Deck<Card> getDiscardPile() {
        return discardPile;
    }

    public Deck<UpgradeCard> getTemporaryUpgradeCards() {
        return temporaryUpgradeCards;
    }

    public Deck<UpgradeCard> getPermanentUpgradeCards() {
        return permanentUpgradeCards;
    }

    public int getCurrentRegister() {
        return currentRegister;
    }

    public void setCurrentRegister(int currentRegister) {
        this.currentRegister = currentRegister;
    }
}
