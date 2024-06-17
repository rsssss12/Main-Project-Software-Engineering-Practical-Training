package gamelogic;


import gamelogic.boardElements.BoardElement;
import gamelogic.cards.Card;
import gamelogic.cards.damageCards.*;
import gamelogic.cards.upgradeCards.UpgradeCard;
import gamelogic.round.Game;
import json.wrapper_utilities.WrapperClassSerialization;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Board contains all decks
 * @author Tim Kriegelsteiner
 */
public class Board {

    private final Deck<Virus> virusDeck = new Deck<>();
    private final Deck<Spam> spamDeck = new Deck<>();
    private final Deck<TrojanHorse> trojanHorseDeck = new Deck<>();
    private final Deck<Worm> wormDeck = new Deck<>();
    private final Deck<UpgradeCard> upgradeCardDeck = new Deck<>();
    private final Deck<UpgradeCard> upgradeShop = new Deck<>();
    private final Deck<UpgradeCard> removedFromGame = new Deck<>();

    private final ArrayList<PlayerMat> playerMats = new ArrayList<>();
    private int energyBank = 48;
    private List<List<List<BoardElement>>> map = new ArrayList<>();

    /**
     * constructor of board
     * creates the damage decks
     */
    public Board() {
        spamDeck.createCard(new Spam(), 38);
        virusDeck.createCard(new Virus(), 18);
        trojanHorseDeck.createCard(new TrojanHorse(), 12);
        wormDeck.createCard(new Worm(), 6);
    }

    /**
     * The method draws damage card
     *
     * @param player : Player who gets the damage card
     * @return DamageCard : card drawn
     */
    public DamageCard drawDamageCard(Player player) {
        Deck<Card> discardPile = player.getPlayerMat().getDiscardPile();

        if (!spamDeck.isDeckEmpty()) {
            return discardPile.drawCardFrom(spamDeck);
        } else if (!trojanHorseDeck.isDeckEmpty()) {
            return discardPile.drawCardFrom(trojanHorseDeck);
        } else if (!virusDeck.isDeckEmpty()) {
            return discardPile.drawCardFrom(virusDeck);
        } else if (!wormDeck.isDeckEmpty()) {
            return discardPile.drawCardFrom(wormDeck);
        }
        return null;
    }

    /**
     * The method draws a number of damage cards
     *
     * @param player : Player who gets the cards
     * @param count  : int amount of cards
     */
    public void drawDamageCards(Player player, int count) {
        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cards.add(drawDamageCard(player));
        }

        sendDrawDamage(player, cards);
    }

    /**
     * The method sends the protocol DrawDamage
     *
     * @param player : Player
     * @param cards  : ArrayList<Card> list of cards
     */
    public void sendDrawDamage(Player player, ArrayList<Card> cards) {

        String json = WrapperClassSerialization.serializeDrawDamage(player, cards);
        player.getClientHandler().sendMessageToAllClients(json);
        Game.delayingTime(3000);
    }

    /**
     * The method gets all playerMats
     *
     * @param players : ArrayList<Player> list of players
     */
    public void getMatsFromPlayers(ArrayList<Player> players) {

        for (Player player : players) {
            playerMats.add(player.getPlayerMat());
        }
    }


    public Deck<Virus> getVirusDeck() {
        return virusDeck;
    }

    public Deck<Spam> getSpamDeck() {
        return spamDeck;
    }

    public Deck<TrojanHorse> getTrojanHorseDeck() {
        return trojanHorseDeck;
    }

    public Deck<Worm> getWormDeck() {
        return wormDeck;
    }

    public Deck<UpgradeCard> getUpgradeShop() {
        return upgradeShop;
    }

    public int getEnergyBank() {
        return energyBank;
    }

    public void setEnergyBank(int energyBank) {
        this.energyBank = energyBank;
    }

    public Deck<UpgradeCard> getRemovedFromGame() {
        return removedFromGame;
    }

    public List<List<List<BoardElement>>> getMap() {
        return map;
    }

    public void setMap(List<List<List<BoardElement>>> map) {
        this.map = map;
    }


    public Deck<UpgradeCard> getUpgradeCardDeck() {
        return upgradeCardDeck;
    }
}
