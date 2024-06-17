package gamelogic.cards.specialProgrammingCards;

import gamelogic.Player;
import gamelogic.cards.programmingCards.Again;
import gamelogic.round.Game;

public class RepeatRoutine extends SpecialProgrammingCard {

    public RepeatRoutine() {

    }

    public void effect(Game game, Player player) {
        Again again = new Again();
        again.effect(game, player);
    }

    public String toString() {
        return "RepeatRoutine";
    }
}
