package gamelogic.cards.specialProgrammingCards;

import gamelogic.Player;
import gamelogic.cards.programmingCards.*;
import gamelogic.round.Game;

public class WeaselRoutine extends SpecialProgrammingCard {

    public WeaselRoutine() {

    }

    public void effect(Game game, Player player) {
        // ToDO: Spieler wählt auf GUI, welchen Effekt er will: chosenEffect
        String chosenEffect = "";
        switch (chosenEffect) {
            case ("LeftTurn") -> {
                LeftTurn leftTurn = new LeftTurn();
                leftTurn.effect(game, player);
            }
            case ("RightTurn") -> {
                RightTurn rightTurn = new RightTurn();
                rightTurn.effect(game, player);
            }
            case ("UTurn") -> {
                UTurn uTurn = new UTurn();
                uTurn.effect(game, player);
            }
        }
    }

    public String toString() {
        return "WeaselRoutine";
    }
}
