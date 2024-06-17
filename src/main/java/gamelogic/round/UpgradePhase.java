package gamelogic.round;

import gamelogic.Board;
import gamelogic.Deck;
import gamelogic.Player;
import gamelogic.cards.upgradeCards.UpgradeCard;
import network.ClientHandler;

public class UpgradePhase extends Phase {


    public UpgradePhase(Game game) {
        super(game);
        super.ID = 1;
    }

    public void refreshUpgradeShop(Game game) {
        Board board = game.getBoard();
        Deck<UpgradeCard> upgradeShop = board.getUpgradeShop();
        Deck<UpgradeCard> upgradeCardDeck = board.getUpgradeCardDeck();
        Deck<UpgradeCard> removedFromGame = board.getRemovedFromGame();

        if (upgradeShop.size() == game.getPlayers().size()) {

            removedFromGame.addAll(upgradeShop);
            upgradeShop.clear();
        }
        if (upgradeShop.size() == 0) {
            for (Player player : game.getPlayers()) {
                upgradeShop.drawCardFrom(upgradeCardDeck);
            }

        } else {

            int remainingCardsToDraw = game.getPlayers().size() - upgradeShop.size();

            for (int cardsDrawn = 0; cardsDrawn < remainingCardsToDraw; cardsDrawn++) {
                upgradeShop.drawCardFrom(upgradeCardDeck);
            }

        }
    }


    public void buyCard(Player player, UpgradeCard upgradeCard, Game game) {

        Deck<UpgradeCard> upgradeShop = game.getBoard().getUpgradeShop();
        Deck<UpgradeCard> temporaryUpgradeCards = player.getPlayerMat().getTemporaryUpgradeCards();
        Deck<UpgradeCard> permanentUpgradeCards = player.getPlayerMat().getPermanentUpgradeCards();

        player.getPlayerMat().setEnergyReserve(player.getPlayerMat().getEnergyReserve() - upgradeCard.getCost()) ;


        if (!upgradeCard.isPermanent()) {
            temporaryUpgradeCards.add(upgradeCard);
        } else {
            permanentUpgradeCards.add(upgradeCard);
        }
       upgradeShop.remove(upgradeCard);
//ToDo get a clientHandler
//        game.startNewTurn();

    }
    public void discardRequest() {
        //ToDo Log / Window/ Message
        //"You already have three temporary upgrade cards. Do you want to discard an old one or skip?"
        //Window
    }

    public void processingDiscardRequestAnswer() {
        //if discard
        //flag discardProcessActive
        //"Choose the card you want to discard"
        //if skip: newTurn
    }

    public boolean isUpgradeFieldFull(Deck<UpgradeCard> field) {

        return field.size() > 3;
    }

    public boolean isEnergyPoolSufficient(Player player, UpgradeCard card) {

        return player.getPlayerMat().getEnergyReserve() >= card.getCost();
    }


    @Override
    public void start(ClientHandler clientHandler) {
        refreshUpgradeShop(game);
    }


}
