package json.wrapper_utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gamelogic.boardElements.*;
import gui.mvvm.game.GameModel;
import json.json_wrappers.lobby.GameStarted;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class MapWrapping wraps the map
 *
 * @author Tim Kriegelsteiner
 */
public class MapWrapping {
    private static final Logger logger = LogManager.getLogger(MapWrapping.class.getName());

    /**
     * The method selectBoardElement serialize the boardElements from json File
     *
     * @param jsonElement: JsonElement jsonElement
     * @return: BoardElement
     */
    public static BoardElement selectBoardElement(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


        JsonObject jsonObject;

        if (jsonElement.isJsonNull()) {
            return null;
        } else {
            jsonObject = jsonElement.getAsJsonObject();
        }
        String type = jsonObject.get("type").getAsString().toUpperCase();

        switch (BoardElementEnum.valueOf(type)) {

            case ANTENNA -> {
                return gson.fromJson(jsonObject, Antenna.class);
            }
            case CHECKPOINT -> {
                return gson.fromJson(jsonObject, CheckPoint.class);
            }
            case CONVEYORBELT -> {

                return gson.fromJson(jsonObject, ConveyorBelt.class);
            }
            case EMPTY -> {
                return gson.fromJson(jsonObject, Empty.class);
            }
            case ENERGYSPACE -> {
                return gson.fromJson(jsonObject, EnergySpace.class);
            }
            case GEAR -> {
                return gson.fromJson(jsonObject, Gear.class);
            }
            case LASER -> {
                return gson.fromJson(jsonObject, Laser.class);
            }
            case PIT -> {
                return gson.fromJson(jsonObject, Pit.class);
            }
            case PUSHPANEL -> {
                return gson.fromJson(jsonObject, PushPanel.class);
            }
            case RESTARTPOINT -> {
                return gson.fromJson(jsonObject, RestartPoint.class);
            }
            case STARTPOINT -> {
                return gson.fromJson(jsonObject, StartPoint.class);
            }
            case WALL -> {
                return gson.fromJson(jsonObject, Wall.class);
            }
        }

        return null;
    }

    /**
     * The method build the map from the json file and add them into the list
     *
     * @param gameStarted: GameStarted: gameStarted
     * @return: List<List < List < BoardElement>>>
     */
    public static List<List<List<BoardElement>>> buildMapFromJson(GameStarted gameStarted) {


        List<List<List<BoardElement>>> newGameMap = new ArrayList<>();

        for (List<List<JsonElement>> x : gameStarted.getGameMap()) {

            List<List<BoardElement>> xList = new ArrayList<>();
            newGameMap.add(xList);

            for (List<JsonElement> y : x) {

                List<BoardElement> yList = new ArrayList<>();
                newGameMap.get(newGameMap.indexOf(xList)).add(yList);

                for (JsonElement jsonElement : y) {

                    yList.add(selectBoardElement(jsonElement));
                }
            }
        }

        return newGameMap;

    }

    /**
     * The method build the json from the map
     *
     * @param gameMap: List<List<List<BoardElement>>> gameMap
     * @return: List<List < List < JsonElement>>>
     */
    public static List<List<List<JsonElement>>> buildJsonFromMap(List<List<List<BoardElement>>> gameMap) {

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        List<List<List<JsonElement>>> newGameMap = new ArrayList<>();

        for (List<List<BoardElement>> x : gameMap) {

            List<List<JsonElement>> xList = new ArrayList<>();
            newGameMap.add(xList);

            for (List<BoardElement> y : x) {

                List<JsonElement> yList = new ArrayList<>();
                newGameMap.get(newGameMap.indexOf(xList)).add(yList);

                for (BoardElement boardElement : y) {

                    JsonElement jsonElement = gson.toJsonTree(boardElement);

                    yList.add(jsonElement);
                }
            }
        }

        return newGameMap;
    }

    /**
     * The method read a file and return a string representation of the frile
     *
     * @param path: String file path
     * @return: String value
     */
    public static String readFileToString(String path) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String string = bufferedReader.lines().collect(Collectors.joining("\n"));

        return string;
    }

    /**
     * The method read a file to String
     *
     * @param path:   String file path
     * @param tClass: Class<T>
     * @return: String
     */
    public static <T> String readFileToStringJAR(String path, Class<T> tClass) {

        logger.debug(tClass);

        logger.debug(tClass.getResourceAsStream(path));


        InputStream inputStream = tClass.getClassLoader().getResourceAsStream(path);

        logger.debug("inputStream: " + inputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String string = bufferedReader.lines().collect(Collectors.joining("\n"));


        return string;
    }

    /**
     * The method read File to game started
     *
     * @param path: String file path
     * @return: GameStarted
     */
    public static GameStarted readFileToGameStarted(String path) {

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        String map = readFileToStringJAR(path, GameModel.class);


        return gson.fromJson(map, GameStarted.class);
    }
}
