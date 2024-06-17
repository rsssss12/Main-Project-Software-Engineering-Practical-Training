package gamelogic.cards.specialProgrammingCards;

import gamelogic.Player;
import gamelogic.cards.programmingCards.*;
import gamelogic.round.Game;

public class SandboxRoutine extends SpecialProgrammingCard {

    public SandboxRoutine() {

    }

    public void effect(Game game, Player player) {
        // ToDO: Spieler wählt auf GUI, welchen Effekt er will: chosenEffect
        String chosenEffect = "";
        switch (chosenEffect) {
            case ("MoveI") -> {
                MoveOne moveOne = new MoveOne();
                moveOne.effect(game, player);
            }
            case ("MoveII") -> {
                MoveTwo moveTwo = new MoveTwo();
                moveTwo.effect(game, player);
            }
            case ("MoveIII") -> {
                MoveThree moveThree = new MoveThree();
                moveThree.effect(game, player);
            }
            case ("BackUp") -> {
                BackUp backUp = new BackUp();
                backUp.effect(game, player);
            }
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
        return "SandboxRoutine";
    }
}
