package gamelogic.maps;

import java.util.ArrayList;
import java.util.List;


/**
 * The enumeration MapEnum contains all the maps with their paths
 *
 * @author Tim Kriegelsteiner
 * @author Hannah Spierer
 */
public enum MapEnum {

    DIZZYHIGHWAY("DizzyHighway", "jsonfiles/DizzyHighway.json", "gui/Images/maps/dizzyhighway.png"),
    EXTRACRISPY("ExtraCrispy", "jsonfiles/ExtraCrispy.json", "gui/Images/maps/extraCrispy.png"),
    LOSTBEARINGS("LostBearings", "jsonfiles/LostBearings.json", "gui/Images/maps/lostBearings.png"),
    DEATHTRAP("DeathTrap", "jsonfiles/DeathTrap.json", "gui/Images/maps/deathTrap.png"),
    TWISTER("Twister", "jsonfiles/Twister.json", "gui/Images/maps/twister.png"),

    ;
    private final String name;
    private final String path;
    private final String imagePath;


    /**
     * constructor of MapEnum
     *
     * @param name      : String name of map
     * @param path      : String path to json file
     * @param imagePath : String path to mini image
     */
    MapEnum(String name, String path, String imagePath) {
        this.name = name;
        this.path = path;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * The method returns a list with all map names
     *
     * @return List<String> : list of names
     */
    public static List<String> getNames() {

        List<String> list = new ArrayList<>();

        for (MapEnum value : values()) {

            list.add(value.getName());
        }
        return list;
    }


    /**
     * The method returns the enum value
     *
     * @param name : String name of map
     * @return MapEnum : enum value
     */
    public static MapEnum getEnumFromName(String name) {

        for (MapEnum value : MapEnum.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

}
