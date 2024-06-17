package json.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import gamelogic.*;
import gamelogic.maps.MapEnum;
import gamelogic.boardElements.Antenna;
import gamelogic.boardElements.BoardElement;
import gamelogic.boardElements.ConveyorBelt;
import gamelogic.boardElements.Wall;
import gamelogic.cards.Card;
import gamelogic.cards.CardEnum;
import gamelogic.cards.damageCards.Spam;
import gamelogic.cards.damageCards.TrojanHorse;
import gamelogic.cards.programmingCards.Again;
import gamelogic.cards.programmingCards.MoveOne;
import gui.mvvm.game.BoardMapCreator;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import json.json_wrappers.actions_events_effects.DrawDamage;
import json.json_wrappers.cards.CardPlayed;
import json.json_wrappers.connection.HelloClient;
import json.json_wrappers.lobby.GameStarted;
import json.json_wrappers.round.activation_phase.CurrentCards;
import json.json_wrappers.round.programming_phase.YourCards;
import json.wrapper_utilities.*;
import network.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

import static gamelogic.Direction.*;

public class Test {
    private static Antenna antenna;
    private static Logger logger = LogManager.getLogger(Test.class.getName());

    public static void main(String[] args) {
//        testWrapperClasses();
//        lmuServerTest();
//        howAreCardsConvertedEasy();
//        singleCardTesting();
//        enumTest();

//        CardSwitch.selectCard("Again");
//        recognizingCard("Again");

//        deckPackaging();
//        YourCardsTest();

//        currentCardsTesting();
//        drawDamageTesting();
//        boardElementTest();
//        try {
//            readFile();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        try {
//            writeFile();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            fileReadIntern();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        gameMapTest();
//            deserializeLists();
//        getClassToStringStuff();

//        arePositionsInBoardElementsKillingUs();
//        playerProperty();

//        propertyInitialization();
//        doesRemoveRemoveAllObjects();
//        booleanToString();
//        probAgain();
//        toStringProp();
//        assign();
//        move(new Player());

//        assigns(new Player());
//        division();
//        pathReader();
        testingObservableLists();
    }

    public static void testingObservableLists() {


        ObservableList<String> observableList = FXCollections.observableArrayList();
        ListProperty<String> listProperty = new SimpleListProperty<>(observableList);

        ListChangeListener<String> listChangeListener = new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                change.getList();
                logger.debug(change.getList());
                logger.debug("fired");

            }
        };

        ChangeListener<List<String>> changeListener = new ChangeListener<List<String>>() {
            @Override
            public void changed(ObservableValue<? extends List<String>> observableValue, List<String> strings, List<String> t1) {
                logger.debug("ChangeListener<List> fired");
            }
        };

//        listProperty.addListener(listChangeListener);
//        observableList.addListener(listChangeListener);
        listProperty.addListener(changeListener);

//        listProperty.add("added to PropertyList");
        observableList.add("added to ObservableList");


    }

    public static <T> void ImageReader(Class<T> tClass, String path) {
        logger.debug(tClass);

        logger.debug(tClass.getResourceAsStream(path));



        InputStream inputStream = tClass.getClassLoader().getResourceAsStream(path);
//        InputStream inputStream1 = t.getClass().getClassLoader().getResourceAsStream(path);

        logger.debug("inputStream: " + inputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream));
        Image image = new Image(inputStream);
        logger.debug(": " + image.getHeight());

        BoardMapCreator boardMapCreator = new BoardMapCreator();
        Image imageFromPath = boardMapCreator.getImageFromPathJAR(CardEnum.SPAM.getFilePath());
        logger.debug(imageFromPath.getHeight());


    }

    public static void pathReader() {
//
//        String s = MapWrapping.readFileToString(MapEnum.DIZZYHIGHWAY.getPath());
//        logger.debug(s);

//        String string = MapWrapping.readFileToStringJAR(MapEnum.DIZZYHIGHWAY.getPath(), MapWrapping.class);
//        logger.debug(string);

        ImageReader(Test.class, CardEnum.SPAM.getFilePath());
    }

    public static void division() {
        double i = 5;
        double j = 7;

        logger.debug(i / j);
    }
//    public static void assigns(Player player) {
//        player.getRobot().setPosition(new Position(0, 0));
//
//        determineNextPosition(player.getRobot().getPosition(),player.getRobot().getDirection());
//
//        logger.debug(player.getRobot().getPosition().getX());
//        logger.debug(player.getRobot().getPosition().getY());
//
//    }
//    public static Position determineNextPosition(Position position, Direction direction) {
//        switch (direction) {
//
//            case TOP -> {
//                position.setY(position.getY() - 1);
//            }
//            case BOTTOM -> {
//                position.setY(position.getY() + 1);
//            }
//            case LEFT -> {
//                position.setX(position.getX() - 1);
//            }
//            case RIGHT -> {
//                position.setX(position.getX() + 1);
//            }
//        }
//
//        return position;
//    }
//    public static void move(Player player) {
//        player.getRobot().setPosition(new Position(0, 0));
//
//
//        Direction direction = player.getRobot().getDirection();
//        Position position = player.getRobot().getPosition();
//
//
//        switch (direction) {
//
//            case TOP -> {
//                position.setY(position.getY() - 1);
//            }
//            case BOTTOM -> {
//                position.setY(position.getY() + 1);
//            }
//            case LEFT -> {
//                position.setX(position.getX() - 1);
//            }
//            case RIGHT -> {
//                position.setX(position.getX() + 1);
//            }
//        }
//
//        logger.debug(position.getX());
//        logger.debug(position.getY());
//        logger.debug(player.getRobot().getPosition().getX());
//        logger.debug(player.getRobot().getPosition().getY());
//
////        String json = WrapperClassSerialization.serializeMovement(player.getClientID(), position);
////        clientHandler.sendMessageToAllClients(json);
//    }


    public static void assign() {
        int dynamic = 5;
        int stat;
        int x = dynamic;
        int y;


        dynamic = 7;

        x = 9;
        logger.debug(x);
        logger.debug(dynamic);

        Effects effects = new Effects();


    }

    public static void toStringProp() {
        IntegerProperty energyReserve = new SimpleIntegerProperty(5);
        StringProperty energyReserveToString = new SimpleStringProperty(energyReserve.getValue().toString());

        IntegerProperty ownEnergy = new SimpleIntegerProperty(0);
        ownEnergy = energyReserve;

        logger.debug(energyReserve.getValue());
        logger.debug(energyReserveToString.getValue());

        energyReserve.setValue(6);

        logger.debug(energyReserve.getValue());
        logger.debug(energyReserveToString.getValue());
        logger.debug(ownEnergy.getValue());


    }

    public static void probAgain() {
        ObjectProperty<Direction> directionObjectProperty = new SimpleObjectProperty<>(RIGHT);

        Player player = new Player();

        player.getRobot().setDirection(TOP);

        directionObjectProperty.setValue(player.getRobot().getDirection());

        ChangeListener<Direction> directionChanged = (observableValue, t0, t1) -> logger.debug("changed");

        directionObjectProperty.addListener(directionChanged);

        player.getRobot().setDirection(BOTTOM);

    }

    public static void booleanToString() {
        boolean lele = false;

//        logger.debug(lele.toString());

        ArrayList<String> orientations = new ArrayList<>();
        orientations.add("top");
        orientations.add("right");

        Wall wall = new Wall("dummy", orientations);

//        logger.debug(BoardMapCreator.getDoubleWall(wall));
    }

    public static void doesRemoveRemoveAllObjects() {
        Deck<Card> deck = new Deck<>();
        MoveOne moveOne = new MoveOne();

        deck.add(moveOne);
        deck.add(moveOne);

        logger.debug(deck);

        deck.remove(moveOne);

        logger.debug(deck);

    }

    public static void propertyInitialization() {
        StringProperty[] registerProperties = new StringProperty[4];

        StringProperty register1 = new SimpleStringProperty();

        registerProperties[0] = register1;

        String card = "Again";
        registerProperties[0].getValue();
        registerProperties[0].setValue(card);

    }

    public static void playerProperty() {

        ObjectProperty<Player> player = new SimpleObjectProperty<>();
        Player player2 = new Player();

        player.setValue(player2);
        ChangeListener<Player> playerChanged = (observableValue, player12, t1) -> logger.debug("Player changed");
        player.addListener(playerChanged);


        player.getValue().setUsername("Pepe");

        Player player1 = new Player();

        player.setValue(player2);


        MapProperty<Integer, Position> integerPositionMapProperty = new SimpleMapProperty<>();

        integerPositionMapProperty.get().put(1, new Position());

        ChangeListener<Map<Integer, Position>> changeListener = (observableValue, integerPositionMapProperty1, t1) -> {
            logger.debug("map change");
        };

        integerPositionMapProperty.addListener(changeListener);

        Position position = new Position(3, 4);

        integerPositionMapProperty.replace(1, position);


    }


    public static void arePositionsInBoardElementsKillingUs() {

        GameStarted gameStarted = MapWrapping.readFileToGameStarted(MapEnum.DIZZYHIGHWAY.getPath());

        List<List<List<BoardElement>>> mapFromJsonElement = MapWrapping.buildMapFromJson(gameStarted);

        logger.debug("mapFromJsonElement: " + mapFromJsonElement);

        String jsonFromJsonElementList = WrapperClassSerialization.serializeGameStarted(mapFromJsonElement);


        logger.debug("jsonFromJsonElementList: " + jsonFromJsonElementList);

        GameStarted gameStarted1 = (GameStarted) Deserializer.deserializeData(jsonFromJsonElementList);

        logger.debug("Map form gamstarted from Deserializer: " + gameStarted1.getGameMap());

    }

    public static void getClassToStringStuff() {
        HelloClient helloClient = new HelloClient("lele");

        logger.debug(helloClient.getClass().getSimpleName());
    }

    public static void gameMapTest() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String map = MapWrapping.readFileToString("jsonfiles/Example.json");

        GameStarted gameStarted0 = (GameStarted) Deserializer.deserializeData(map);
        logger.debug("gameStarted0 " + gameStarted0);

        GameStarted gameStarted = gson.fromJson(map, GameStarted.class);

        logger.debug("MapWrapping.buildMapFromJson(gameStarted) = " + MapWrapping.buildMapFromJson(gameStarted));


        String rejson = gson.toJson(gameStarted);

        GameStarted gameStartedReJson = gson.fromJson(rejson, GameStarted.class);

        logger.debug("buildMapFromJson: " + MapWrapping.buildMapFromJson(gameStartedReJson));

        logger.debug("buildJsonFromMap: " + MapWrapping.buildJsonFromMap(MapWrapping.buildMapFromJson(gameStartedReJson)));

        List<List<List<BoardElement>>> lists = MapWrapping.buildMapFromJson(gameStartedReJson);

        lists.get(0).get(0).toArray();

//        List<BoardElement>[][] lists1 = new ArrayList<BoardElement>[lists.size()][];

        for (List<List<BoardElement>> list : lists) {

        }
        logger.debug("list" + lists.get(0));

        ConveyorBelt conveyorBelt1 = new ConveyorBelt("23", null, 2);


        lists.get(0).get(0).add(new ConveyorBelt("23", null, 2));
        logger.debug("Zeile 112" + lists.get(0).get(0).get(1));


        ConveyorBelt conveyorBelt = (ConveyorBelt) MapWrapping.buildMapFromJson(gameStartedReJson).get(0).get(0).get(0);
        conveyorBelt.getSpeed();
//        WrapperClassSerialization.serializeGameStarted(gameStartedReJson.g);


//
//        logger.debug("rejson = " + rejson);
//
//
//
//        logger.debug("gameStartedReJson.getGameMap() = " + gameStartedReJson.getGameMap());
//
//        gameStarted.getGameMap().get(0).get(0).get(0);
//
//        List<List<List<BoardElement>>> newGameMap = MapWrapping.buildMapFromJson(gameStarted);
//
//        logger.debug("newGameMap.get(0).get(0).get(0).getClass() = " + newGameMap.get(0).get(0).get(0).getClass());

//        ConveyorBelt conveyorBelt = newGameMap.get(0).get(0).get(0);

//        JsonObject jsonObject = gameStarted.getGameMap().get(1).get(1).get(0).getAsJsonObject();

//        logger.debug("jsonObject.get(\"type\") = " + jsonObject.get("type"));


//        logger.debug("Map gleich null?: " + (gameStarted.getGameMap() == null));


//
//        logger.debug(gameStarted.getGameMap());
//        logger.debug(gameStarted.getGameMap().get(0));
//        logger.debug(gameStarted.getGameMap().get(0).get(0));
//        logger.debug(gameStarted.getGameMap().get(1).get(0).get(0));
//
//        Object object = gameStarted.getGameMap().get(1).get(0).get(0);

//        Class<?> clazz = object.getClass();
//        Field field = null; //Note, this can throw an exception if the field doesn't exist.
//        try {
//            field = clazz.getField("type");
//            Object fieldValue = field.get(object);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }


    }

    public static void cardList() {


    }

    public static void gamMapTestArray() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String map = MapWrapping.readFileToString("jsonfiles/Example.json");


        GameStartedArray gameStarted = gson.fromJson(map, GameStartedArray.class);

        logger.debug("Map gleich null?: " + (gameStarted.getGameMap() == null));

        logger.debug(Arrays.deepToString(gameStarted.getGameMap()));

        List<Object>[][] gameMap = gameStarted.getGameMap();

        logger.debug(Array.get(Array.get(gameMap, 1), 0));

        Object arrayx0y1 = Array.get(Array.get(gameMap, 1), 0);

//        List<Object>  lo = Array.get(gameMap,1);


        ArrayList<Object> arrayListNull = new ArrayList<>();
        arrayListNull.add(null);
        arrayListNull.add(null);

        logger.debug(arrayListNull.get(1));
    }

    public static void deserializeMap(String map) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        Type stringListType = new TypeToken<List<String>>() {
        }.getType();

        List<String> stringList = gson.fromJson(map, stringListType);

    }

    public static void deserializeLists() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


        //ArrayList
        ArrayList<String> arrayList = new ArrayList<>(6);

        //serialize

        String emptyStringArrayList = gson.toJson(arrayList);
        logger.debug(emptyStringArrayList);

        arrayList.add("String 0");
        String filledStringArrayList = gson.toJson(arrayList);
        logger.debug(filledStringArrayList);


//        arrayList.set(5,"String 5");
//        String gapedFilledStringArrayList = gson.toJson(arrayList);
//        logger.debug(gapedFilledStringArrayList);

        //deserialize


        String[] array = new String[5];

        String emptyArray = gson.toJson(array);
        logger.debug(emptyArray);

        Array.set(array, 4, "String 4");
        String filledArray = gson.toJson(array);
        logger.debug(filledArray);

//        GameStarted gameStarted = new GameStarted(new ArrayList<>());
//
//        String emptyTripleArrayList = gson.toJson(gameStarted.getGameMap());
//        logger.debug(emptyTripleArrayList);

        List<List<Object>> x0 = new ArrayList<>();
        List<Object> x0y0 = new ArrayList<>();
        Object x0y0z0 = "x0 y0 z0";
        Object x0y0z1 = "x0 y0 z1";

        List<Object> x0y1 = new ArrayList<>();

        List<List<Object>> x1 = new ArrayList<>();
        List<Object> x1y0 = new ArrayList<>();

//        gameStarted.getGameMap().add(x0);
//        gameStarted.getGameMap().get(0).add(x0y0);
//        gameStarted.getGameMap().get(0).get(0).add(x0y0z0);
//        gameStarted.getGameMap().get(0).get(0).add(x0y0z1);
//
//        gameStarted.getGameMap().get(0).add(x0y1);
//        gameStarted.getGameMap().add(x1);
//        gameStarted.getGameMap().get(1).add(x1y0);
//
//        String filledTripleArrayList = gson.toJson(gameStarted.getGameMap());
//        logger.debug("ObjectList: " + filledTripleArrayList);

//
//        List<Object> objectList = new ArrayList<>();
//
//        objectList.add(new Object());
//        objectList.add(new Object());
//
//        String objectListString = gson.toJson(objectList);
//        logger.debug(objectListString);


    }

    public static void fileReadIntern() throws FileNotFoundException {


//        String fileName = "jsonFiles/Antenna.json";
//        InputStream is = Test.class.getClassLoader().getResourceAsStream(fileName);
//
//        String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
//
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(path3));

        FileReader fileReader = new FileReader("jsonfiles/DizzyHighway.json");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        Stream<String> lines = bufferedReader.lines();
//
//        lines.forEach(out::println);

        String string = bufferedReader.lines().collect(Collectors.joining("\n"));
        logger.debug("String: \n" + string);

//        StringBuilder stringBuilder = new StringBuilder();
//
//        String line;
//        while (true) {
//            try {
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            String str = stringBuilder.toString();
//            logger.debug(str);
//        }


//
//        try {
//            String fileInString = bufferedReader.readLine;
//            logger.debug(fileInString);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

    public static void boardElementTest() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        ArrayList<String> orientations = new ArrayList<>();

        orientations.add("top");


        Antenna antenna = new Antenna("5A", orientations);


        String json = gson.toJson(antenna);
        logger.debug(json);


        Antenna antenna1 = gson.fromJson(json, Antenna.class);
        logger.debug(antenna1);
        logger.debug(antenna1.getIsOnBoard());


        String lele =
                "          {\n" +
                        "            \"orientations\": [\n" +
                        "              \"top\"\n" +
                        "            ],\n" +
                        "            \"type\": \"Wall\",\n" +
                        "            \"isOnBoard\": \"Start A\"\n" +
                        "          }\n";

        String lele2 = "{\"orientations\":[\"top\"],\"type\":\"Antenna\",\"isOnBoard\":\"5A\"}";

        String lele3 = "{\"type\":\"Antenna\",\"isOnBoard\":\"5A\",\"orientations\":[\"top\"]}";

        logger.debug(lele2);
        logger.debug(lele3);

        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(antenna);

        Antenna antenna5 = gson.fromJson(lele, Antenna.class);
        logger.debug(antenna5.getIsOnBoard());

        Antenna antenna2 = gson.fromJson(lele2, Antenna.class);

        logger.debug("from lele2" + antenna2.getIsOnBoard());

        Antenna antenna3 = gson.fromJson(lele3, Antenna.class);

        logger.debug("from lele3" + antenna3.getIsOnBoard());


    }

    public static void readFile() throws IOException {
        String path = "gui/Antenna.json";
        String path2 = "E:/Studium/Informatik-Studium/4. Semester Informatik/SEP/Hauptprojekt/Sideproject/Repairing5/neidische_narwale_hp/gui/Antenna.json";
        String path3 = "E:/Studium/Informatik-Studium/4. Semester Informatik/SEP/Milestones/Milestone IV/JSONFILES/Antenna.json";
        String path4 = "E:/Studium/Informatik-Studium/4. Semester Informatik/SEP/Milestones/Milestone IV/JSONFILES/Spam.json";


//       bufferedReader.readLine();
        ArrayList<String> orientations = new ArrayList<>();
        orientations.add("top");
        Antenna antenna = new Antenna("5B", orientations);
        Gson gson = new GsonBuilder().create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path3));
//     logger.debug(bufferedReader.readLine());

//        Spam spam = gson.fromJson(bufferedReader,Spam.class);
//        logger.debug(spam);

//        File file = new File();
//        String lele = ;
//
//        String fromJToJ = gson.toJson(bufferedReader);

        Antenna json = gson.fromJson(bufferedReader, Antenna.class);
        logger.debug(json);
        logger.debug(json.toString());
        logger.debug(json.getOrientations());
//        bufferedReader.readLine();
//bufferedReader.lines()
//       Object object = gson.fromJson(bufferedReader, Object.class);

//        try {
//            bufferedReader.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        logger.debug(object);

//       logger.debug(json.getIsOnBoard());
    }

    public static void writeFile() throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        ArrayList<String> orientations = new ArrayList<>();
        orientations.add("top");
        Antenna antenna1 = new Antenna("5A", orientations);

        Spam spam = new Spam();

        String path = "E:/Studium/Informatik-Studium/4. Semester Informatik/SEP/Milestones/Milestone IV/JSONFILES/Spam.json";


        Writer writer = new FileWriter(path);
        gson.toJson(spam, writer);
        writer.flush();
        writer.close();

        logger.debug(gson.toJson(spam));

    }

    public static void drawDamageTesting() {
        WrapperClassSerialization wrapperClassSerialization = new WrapperClassSerialization();


        Player player = new Player();
        player.setClientID(41);

        ArrayList<Card> damages = new ArrayList<>();
        damages.add(new Spam());
        damages.add(new TrojanHorse());

        String json = wrapperClassSerialization.serializeDrawDamage(player, damages);
        logger.debug("to Json: " + json);

        DrawDamage drawDamage = (DrawDamage) Deserializer.deserializeData(json);
        logger.debug("From Json: " + drawDamage);
        logger.debug("From Json check: " + drawDamage.getCards().get(0).getClass());

        ArrayList<Card> cards = CardWrapping.StringListToCardArrayList(drawDamage.getCards());
        logger.debug("ToCard: " + cards);
        logger.debug("To Card check: " + cards.get(1).getClass());


    }

    public static void currentCardsTesting() {
        ArrayList<Player> players = new ArrayList<>();
        Again again = new Again();


        for (int i = 0; i < 2; i++) {
            Player player = new Player();
            player.setClientID(i);
            players.add(player);
            Array.set(player.getPlayerMat().getRegister(), 0, again);
        }

        Board board = new Board();

//        Game game = new Game(players, board);

        String json = WrapperClassSerialization.serializeCurrentCards(players, 0);
        logger.debug("to json: " + json);

        CurrentCards currentCards = (CurrentCards) Deserializer.deserializeData(json);

        logger.debug("from json" + currentCards.getActiveCards());

        HashMap<Integer, Card> cardHashMap = CardWrapping.hashMapStringToCard(currentCards.getActiveCards());
        logger.debug("StringToCard: " + cardHashMap);
        logger.debug(cardHashMap.get(0).getClass());
    }

    public static String serializeYourCards(Deck<Card> cardsInHand) {

        ArrayList<String> stringCardsInHand = CardWrapping.cardListToStringArrayList(cardsInHand);

        YourCards yourCards = new YourCards(stringCardsInHand);
        return Serializer.serializeData(yourCards);
    }

    public static void deserializeYourCards(String json) {
        Deserializer.deserializeData(json);

    }

    public static void YourCardsTest() {
        Deck<Card> deck = new Deck<>();

        deck.add(new Again());
        deck.add(new Spam());

        String json = serializeYourCards(deck);
        logger.debug("serialized: " + json);

        YourCards yourCards = (YourCards) Deserializer.deserializeData(json);
        logger.debug("deserialized: " + yourCards);
        logger.debug("deserialized get Cards in Hand (String): " + yourCards.getCardsInHand());

        ArrayList<Card> cards = CardWrapping.StringListToCardArrayList(yourCards.getCardsInHand());
        logger.debug("String To Cards: " + cards);
        logger.debug("CardTest: " + cards.get(1).getClass());
    }

    public static void deckPackaging() {
        Gson gson = new GsonBuilder().create();

        Deck<Card> deck = new Deck<>();

        String deckEmpty = gson.toJson(deck);
        logger.debug("EmptyDeck to Json: " + deckEmpty);

        Type listType = new TypeToken<Deck<Card>>() {
        }.getType();
//        Deck<Card> deckEmptyBack = new Gson().fromJson(deckEmpty, listType);
//        logger.debug("EmptyDeck from Json: " + deckEmptyBack);

        Again again = new Again();
        Spam spam = new Spam();

        deck.add(again);
        deck.add(spam);

        //CardsToString
        List<String> strings = deck.stream()
                .map(object -> Objects.toString(object, null)).toList();
        logger.debug(strings);

        ArrayList<String> strinal = new ArrayList<>(strings);


        //String to Cards
        ArrayList<Card> cards = new ArrayList<>();
        for (String string : strinal) {
            cards.add(CardWrapping.selectCard(string));

        }

        logger.debug("Cards from stringList: " + cards);
        logger.debug(cards.get(0).toString());

        String deckFilled = gson.toJson(deck);
        logger.debug("FilledDeck tO Json: " + deckFilled);

//        Deck<Card> deckFilledBack = new Gson().fromJson(deckFilled, listType);
//        logger.debug("FilledDeckBack : " + deckFilledBack);


        ArrayList<Card> al = new ArrayList<>();

        String alEmpty = gson.toJson(deck);
        logger.debug("EmptyArrayList to Json: " + alEmpty);

        listType = new TypeToken<ArrayList<Card>>() {
        }.getType();
//        ArrayList<Card> ArrayListEmptyBack = new Gson().fromJson(alEmpty, listType);
//        logger.debug("EmptyArrayList from Json: " + ArrayListEmptyBack);

        Again again1 = new Again();
        Spam spam1 = new Spam();

        al.add(again1);
        al.add(spam1);

        String ArrayListFilled = gson.toJson(al);
        logger.debug("FilledArrayList tO Json: " + ArrayListFilled);

//        ArrayList<Card> ArrayListFilledBack = new Gson().fromJson(ArrayListFilled, listType);
//        logger.debug("FilledArrayListBack : " + deckFilledBack);


    }


    public static void recognizingCard(String card) {

        Deserializer.deserializeData(card);

        CardWrapping.selectCard(card);


    }

    public static void testGSON() {

    }

    public static void testWrapperClasses() {

    }


    public static void lmuServerTest() {

        try {
            Client client = new Client(new Socket("sep21.dbs.ifi.lmu.de", 52020));

//            logger.debug(client.);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String serializeCardPlayed(Player player, Card card) {

        CardPlayed cardPlayed = new CardPlayed(player.getClientID(), card.toString());
        return Serializer.serializeData(cardPlayed);
    }

    public static void deserializeCardPlayed(String json) {
        CardPlayed cardPlayed = (CardPlayed) Deserializer.deserializeData(json);


        logger.debug(cardPlayed.getCard());
        logger.debug(cardPlayed.getClientID());
    }

    public static void howAreCardsConvertedEasy() {
        deserializeCardPlayed(serializeCardPlayed(new Player(), new Again()));
    }

    public static void jsonObj() {
        JsonObject jsonObject = new JsonObject();


    }

    public static void singleCardTesting() {
        //GSON
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Card.class, new CardTypeAdapter())
//                .serializeNulls()
//                .registerTypeAdapterFactory(new CardTypeAdapterFactory())
//                .create();

//
//        //ProgrammingCard
//        Again again = new Again();
//        //DamageCard
//        Spam spam = new Spam();
//        //UpgradeCard
//        //not available
//
//        //justCard
//        String progrCardJson = gson.toJson(again);
//        logger.debug("singleCardTesting toJson: " + progrCardJson);
//
//        again = (Again) gson.fromJson(progrCardJson, Card.class);
//        logger.debug(again.toString());
//
//
//        String dmgCard = gson.toJson(spam);
//        logger.debug(dmgCard);


    }


}





