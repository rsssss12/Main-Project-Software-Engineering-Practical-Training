package gamelogic.cards;

import gamelogic.Effects;
import gamelogic.Player;
import gamelogic.round.Game;

/**
 * The abstract class Card ist superclass for all card types
 */
public abstract class Card {

    protected Effects effects = new Effects();

    public Card() {
    }

    public abstract String toString();

    public abstract void effect(Game game, Player player);

    public Effects getEffects() {
        return effects;
    }

    public void setEffects(Effects effects) {
        this.effects = effects;
    }
}
