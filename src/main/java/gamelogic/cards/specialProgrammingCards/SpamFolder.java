package gamelogic.cards.specialProgrammingCards;

import gamelogic.Player;
import gamelogic.round.Game;

public class SpamFolder extends SpecialProgrammingCard {

    public SpamFolder(){

    }

    public void effect(Game game, Player player) {
        //discard 1 SPAM card from discard pile to SPAM damage card pile
    }

    public String toString() {
        return "SpamFolder";
    }
}
