package gamelogic.boardElements;

import java.util.ArrayList;

/**
 * The class PushPanel represents the board element pushPanel
 *
 * @author Tim Kriegelsteiner
 */
public class PushPanel extends BoardElement {

    private ArrayList<String> orientations;
    private ArrayList<Integer> registers;


    /**
     * constructor of pushPanel
     *
     * @param isOnBoard    : String board
     * @param orientations : ArrayList<String> orientations
     * @param registers    : ArrayList<Integer> registers which activate the push panel
     */
    public PushPanel(String isOnBoard, ArrayList<String> orientations, ArrayList<Integer> registers) {
        this.type = "PushPanel";
        this.isOnBoard = isOnBoard;
        this.registers = registers;
        this.orientations = orientations;
    }

    public ArrayList<String> getOrientations() {
        return orientations;
    }

    public void setOrientations(ArrayList<String> orientations) {
        this.orientations = orientations;
    }

    public ArrayList<Integer> getRegisters() {
        return registers;
    }

    public void setRegisters(ArrayList<Integer> registers) {
        this.registers = registers;
    }
}