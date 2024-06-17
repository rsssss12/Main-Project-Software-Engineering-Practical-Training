package gamelogic.cards.specialProgrammingCards;


import gamelogic.Player;
import gamelogic.cards.programmingCards.PowerUp;
import gamelogic.round.Game;

public class EnergyRoutine extends SpecialProgrammingCard {

    public EnergyRoutine() {

    }

    public void effect(Game game, Player player) {
        PowerUp powerUp = new PowerUp();
        powerUp.effect(game, player);
    }

    public String toString() {
        return "EnergyRoutine";
    }
}
