package gamelogic.maps;

import gamelogic.boardElements.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The class BoardMap provides methods to get board elements from the map
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public class BoardMap {


    public BoardMap() {
    }


    /**
     * The method fills an array list with board elements of one type from the map
     *
     * @param map          : triple linked list of the map
     * @param boardElement : certain type of board element
     * @return elements : array list with the board elements found
     */
    public static <T extends BoardElement> ArrayList<T> getBoardElementList(List<List<List<BoardElement>>> map, T boardElement) {

        ArrayList<BoardElement> boardElements = searchMapForElement(map, boardElement.getType());
        ArrayList<T> elements = new ArrayList<>();

        for (BoardElement boardElement1 : boardElements) {
            T element = (T) boardElement1;
            elements.add(element);
        }

        return elements;
    }


    /**
     * The method searches in the map for all board elements of a specific type
     *
     * @param map              : triple linked list of the map
     * @param boardElementType : String representation of a certain board element type
     * @return elements : array list with the added elements
     */
    public static ArrayList<BoardElement> searchMapForElement(List<List<List<BoardElement>>> map, String boardElementType) {
        ArrayList<BoardElement> elements = new ArrayList<>();

        int x = 0;

        for (List<List<BoardElement>> xList : map) {
            int y = 0;
            for (List<BoardElement> yList : xList) {

                for (BoardElement element : yList) {
                    if (element.getType().equals(boardElementType)) {

                        elements.add(element);
                    }
                }
                y++;
            }
            x++;
        }
        return elements;
    }


    /**
     * The method applies the x and y position to the board elements
     *
     * @param map : triple linked list of the map
     */
    public static void applyPositionToElementInMap(List<List<List<BoardElement>>> map) {
        int x = 0;

        for (List<List<BoardElement>> xList : map) {
            int y = 0;
            for (List<BoardElement> yList : xList) {

                for (BoardElement element : yList) {
                    element.setX(x);
                    element.setY(y);


                }
                y++;
            }
            x++;
        }
    }


    /**
     * The method returns all board elements at a specific position
     *
     * @param map : triple linked list of the map
     * @param x   : int x coordinate
     * @param y   : int y coordinate
     * @return List<BoardElement> : list of board elements
     */
    public static List<BoardElement> getElementsInPosition(List<List<List<BoardElement>>> map, int x, int y) {

        int xCurrent = 0;
        for (List<List<BoardElement>> xList : map) {
            int yCurrent = 0;
            for (List<BoardElement> yList : xList) {

                if (xCurrent == x && yCurrent == y) {
                    return yList;
                }
                yCurrent++;
            }
            xCurrent++;
        }
        return null;
    }
}