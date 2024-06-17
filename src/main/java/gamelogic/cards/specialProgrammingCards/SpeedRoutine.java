package gamelogic.cards.specialProgrammingCards;

import gamelogic.Player;
import gamelogic.cards.programmingCards.MoveThree;
import gamelogic.round.Game;


public class SpeedRoutine extends SpecialProgrammingCard {

    public SpeedRoutine() {

    }

    public void effect(Game game, Player player) {
        MoveThree moveThree = new MoveThree();
        moveThree.effect(game, player);
    }

    public String toString() {
        return "SpeedRoutine";
    }
}
