package gamelogic.cards.upgradeCards;

import gamelogic.cards.Card;

public abstract class UpgradeCard extends Card {

    private boolean permanent;

    private int cost;

    public UpgradeCard(){
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
