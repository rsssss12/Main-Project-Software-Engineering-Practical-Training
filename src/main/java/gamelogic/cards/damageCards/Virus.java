package gamelogic.cards.damageCards;

import gamelogic.Player;
import gamelogic.Position;
import gamelogic.round.Game;

import java.util.ArrayList;

/**
 * The class Virus implements the damage card effect Virus
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class Virus extends DamageCard {

    public Virus() {
    }

    /**
     * The method implements the effect of the Virus damage card
     * All other robots within a six-space radius of the virus robot
     * must take a virus card
     *
     * @param game   :  current game
     * @param player : player who plays the card
     */
    public void effect(Game game, Player player) {

        radiusOfVirus(game, player);
        effectOfDamageCards(game, player, player.getPlayerMat().getCurrentRegister());
    }

    /**
     * Method implements effect of Virus damage card
     *
     * @param game   : current game
     * @param player : corresponding player
     */
    public void radiusOfVirus(Game game, Player player) {
        ArrayList<Player> players = game.getPlayers();
        ArrayList<Player> infectedPlayers = new ArrayList<>();
        Position position = player.getRobot().getPosition();

        infectedPlayers.addAll(getRobotsInQuarterI(players, position));
        infectedPlayers.addAll(getRobotsInQuarterII(players, position));
        infectedPlayers.addAll(getRobotsInQuarterIII(players, position));
        infectedPlayers.addAll(getRobotsInQuarterIV(players, position));

        for (Player infectedPlayer : infectedPlayers) {
            game.getBoard().drawDamageCards(player, 1);
        }

    }

    /**
     * The method calculates which robot is infected by the virus
     *
     * @param players  : ArrayList of all players
     * @param position : Position of virus spreader
     * @return ArrayList<Player> : infected players
     */
    public ArrayList<Player> getRobotsInQuarterI(ArrayList<Player> players, Position position) {
        ArrayList<Player> infectedPlayers = new ArrayList<>();
        int robotX = position.getX();
        int robotY = position.getY();

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6 - i; j++) {
                Position searchedPosition = new Position(robotX + i, robotY + j);
                if (effects.isPositionOccupied(players, searchedPosition)) {
                    Player positionOccupier = effects.getPositionOccupier(players, searchedPosition);
                    infectedPlayers.add(positionOccupier);
                }

            }
        }
        return infectedPlayers;
    }

    /**
     * The method calculates which robot is infected by the virus
     *
     * @param players  : ArrayList of all players
     * @param position : Position of virus spreader
     * @return ArrayList<Player> : infected players
     */
    public ArrayList<Player> getRobotsInQuarterII(ArrayList<Player> players, Position position) {
        ArrayList<Player> infectedPlayers = new ArrayList<>();
        int robotX = position.getX();
        int robotY = position.getY();

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6 - i; j++) {
                Position searchedPosition = new Position(robotX + j, robotY - i);
                if (effects.isPositionOccupied(players, searchedPosition)) {
                    Player positionOccupier = effects.getPositionOccupier(players, searchedPosition);
                    infectedPlayers.add(positionOccupier);
                }

            }
        }
        return infectedPlayers;
    }

    /**
     * The method calculates which robot is infected by the virus
     *
     * @param players  : ArrayList of all players
     * @param position : Position of virus spreader
     * @return ArrayList<Player> : infected players
     */
    public ArrayList<Player> getRobotsInQuarterIII(ArrayList<Player> players, Position position) {
        ArrayList<Player> infectedPlayers = new ArrayList<>();
        int robotX = position.getX();
        int robotY = position.getY();

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6 - i; j++) {
                Position searchedPosition = new Position(robotX - i, robotY - j);
                if (effects.isPositionOccupied(players, searchedPosition)) {
                    Player positionOccupier = effects.getPositionOccupier(players, searchedPosition);
                    infectedPlayers.add(positionOccupier);
                }

            }
        }
        return infectedPlayers;
    }

    /**
     * The method calculates which robot is infected by the virus
     *
     * @param players  : ArrayList of all players
     * @param position : Position of virus spreader
     * @return ArrayList<Player> : infected players
     */
    public ArrayList<Player> getRobotsInQuarterIV(ArrayList<Player> players, Position position) {
        ArrayList<Player> infectedPlayers = new ArrayList<>();
        int robotX = position.getX();
        int robotY = position.getY();

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 6 - i; j++) {
                Position searchedPosition = new Position(robotX - j, robotY + i);
                if (effects.isPositionOccupied(players, searchedPosition)) {
                    Player positionOccupier = effects.getPositionOccupier(players, searchedPosition);
                    infectedPlayers.add(positionOccupier);
                }

            }
        }
        return infectedPlayers;
    }

    public String toString() {
        return "Virus";
    }
}
