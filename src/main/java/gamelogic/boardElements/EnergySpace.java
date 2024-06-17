package gamelogic.boardElements;


/**
 * The class EnergySpace represents the board element energySpace
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class EnergySpace extends BoardElement {

    private int count;

    /**
     * constructor of energySpace
     *
     * @param isOnBoard: String board
     * @param count      : int number of energy cubes
     */
    public EnergySpace(String isOnBoard, int count) {
        this.type = "EnergySpace";
        this.isOnBoard = isOnBoard;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

