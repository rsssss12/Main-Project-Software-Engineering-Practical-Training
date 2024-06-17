package gamelogic.cards.damageCards;

import gamelogic.Player;
import gamelogic.cards.Card;
import gamelogic.round.Game;
import json.wrapper_utilities.WrapperClassSerialization;

/**
 * The abstract class DamageCard represents all four damage cards
 *
 * @author Hannah Spierer
 * @author Tim Kriegelsteiner
 */
public abstract class DamageCard extends Card {

    /**
     * constructor of damageCard
     */
    public DamageCard() {
    }

    /**
     * The method creates th effect of the damage cards
     *
     * @param game           : current game
     * @param player         : Player who is affected
     * @param registerNumber : int number of th current register
     */
    public void effectOfDamageCards(Game game, Player player, int registerNumber) {

        player.getCardsInHand().drawCardFrom(player.getPlayerMat().getProgrammingCardDeck());
        Card card = player.getCardsInHand().get(0);
        player.getPlayerMat().cleanSingleRegister(game, player.getPlayerMat().getCurrentRegister());
        player.getPlayerMat().getRegister()[registerNumber] = card;
        player.getCardsInHand().remove(card);

        sendReplaceCard(player, registerNumber);

        card.effect(game, player);
    }

    /**
     * The method sends the protocol ReplaceCard to everyone
     *
     * @param player         : Player who is affected
     * @param registerNumber : int number of the current register
     */
    public void sendReplaceCard(Player player, int registerNumber) {
        String json = WrapperClassSerialization.serializeReplaceCard(player, registerNumber);
        player.getClientHandler().sendMessageToAllClients(json);
    }
}
