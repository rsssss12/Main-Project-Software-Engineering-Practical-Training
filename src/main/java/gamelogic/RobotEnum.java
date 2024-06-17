package gamelogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;

import static java.awt.Color.*;

/**
 * The enumeration RobotEnum contains all 6 robots
 *
 * @author Tim Kriegelsteiner
 */
public enum RobotEnum {

    ROBOT1(1, BLUE, "Navy Seal", "gui/Images/robots/Navy_Seal.png"),
    ROBOT2(2, RED, "Red October", "gui/Images/robots/Red_October.png"),
    ROBOT3(3, WHITE, "Whalter White", "gui/Images/robots/Whalter_White.png"),
    ROBOT4(4, YELLOW, "Whalker, Texas Ranger", "gui/Images/robots/Whalker_Texas_Ranger.png"),
    ROBOT5(5, BLACK, "Narwhal Longbottom", "gui/Images/robots/Narwhal_Longbottom.png"),
    ROBOT6(6, GREEN, "Incredible Whalk", "gui/Images/robots/Incredible_Whalk.png"),
    ;

    private final int number;
    private final Color color;
    private final String name;
    private final String imagePath;


    /**
     * constructor of RobotEnum
     *
     * @param number    : int number of robot
     * @param color     : Color of robot
     * @param name      : String name of robot
     * @param imagePath : String path to image
     */
    RobotEnum(int number, Color color, String name, String imagePath) {
        this.number = number;
        this.color = color;
        this.name = name;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * The method returns an array list with all robot names
     *
     * @return Arraylist<String> : String array list of names
     */
    public static ArrayList<String> getNames() {

        ArrayList<String> names = new ArrayList<>();

        for (RobotEnum value : RobotEnum.values()) {
            names.add(value.getName());

        }

        return names;
    }

    /**
     * The method returns an enum map with value and name of robot
     *
     * @return EnumMap : RobotEnum value with String name
     */
    public static EnumMap<RobotEnum, String> getMap() {
        EnumMap<RobotEnum, String> robotEnumStringEnumMap = new EnumMap<>(RobotEnum.class);
        for (RobotEnum value : RobotEnum.values()) {
            robotEnumStringEnumMap.put(value, value.getName());
        }
        return robotEnumStringEnumMap;
    }

    /**
     * The method returns the enum value
     *
     * @param name : String name of robot
     * @return RobotEnum : enum value
     */
    public static RobotEnum getEnumFromName(String name) {
        for (RobotEnum value : RobotEnum.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

    /**
     * The method returns the enum value
     *
     * @param number : int number of robot
     * @return RobotEnum : enum value
     */
    public static RobotEnum getEnumFromNumber(int number) {
        for (RobotEnum value : RobotEnum.values()) {
            if (value.getNumber() == number) {
                return value;
            }
        }
        return null;
    }

}
