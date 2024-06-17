package gamelogic.round;

import network.ClientHandler;

/**
 * The abstract class Phase
 */
public abstract class Phase {

    protected Game game;
    protected int ID;

    /**
     * constructor of Phase
     *
     * @param game: current game
     */
    public Phase(Game game) {
        this.game = game;
    }

    public void start(ClientHandler clientHandler) {

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getID() {
        return ID;
    }


}
