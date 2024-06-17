package gui.mvvmutility;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class Resizer resizes the views depending on the window size
 *
 * @author Tim Kriegelsteiner
 */
public class Resizer {

    private final Logger logger = LogManager.getLogger(Resizer.class.getName());
    ChangeListener<Number> heightChanged;
    ChangeListener<Number> widthChanged;

    public void bindStageSizeListenerConsole(Stage stage, Scene scene) {

        double sceneHeight = scene.getHeight();
        double sceneWidth = scene.getWidth();

        logger.debug("sceneHeight: " + sceneHeight);
        logger.debug("sceneWidth: " + sceneWidth);

        double stageHeight = stage.getHeight();
        double stageWidth = stage.getWidth();

        double stageSceneDifferenceHeight = stageHeight - sceneHeight;
        double stageSceneDifferenceWidth = stageWidth - sceneWidth;

        logger.debug("stageHeight: " + stageHeight);
        logger.debug("stageWidth: " + stageWidth);

        heightChanged = (observableValue, numberOld, numberNew) -> {
            logger.debug("stageHeight: " + numberNew);
            double newSceneHeight = numberNew.doubleValue() - stageSceneDifferenceHeight;
            logger.debug("sceneHeight" + newSceneHeight);

        };

        widthChanged = (observableValue, numberOld, numberNew) -> {
            logger.debug("stageWidth: " + numberNew);
            double newSceneWidth = numberNew.doubleValue() - stageSceneDifferenceWidth;
            logger.debug("sceneWidth" + newSceneWidth);

        };

        stage.heightProperty().addListener(heightChanged);
        stage.widthProperty().addListener(widthChanged);
    }

    public void connectionScale(Stage stage, Scene scene, Parent root) {

        root.setScaleX(0.5);
        root.setScaleY(0.5);

        logger.debug("root.getTranslateX(): " + root.getTranslateX());
        logger.debug("root.getTranslateY(): " + root.getTranslateY());


    }

    /**
     * The method calculate the translate factor
     *
     * @param size:  double size
     * @param scale: double scale
     * @return: double translate Factor
     */
    public double calculateTranslateFactor(double size, double scale) {

        return ((size * 2 * scale) / (scale + 1) * 1 / size) - 1;
    }

    /**
     * The method calculate the scale
     *
     * @param sizeOld:   double
     * @param numberNew: Number
     * @return: double
     */
    public double calculateScale(double sizeOld, Number numberNew) {
        double sizeNew = numberNew.doubleValue();

        return sizeNew / sizeOld;
    }

    /**
     * The method bind the size and listener and resize
     *
     * @param stage: Stage stage
     * @param scene: Scene scene
     * @param root:  Parent root
     */
    public void bindSizeListenerResize(Stage stage, Scene scene, Parent root) {

        double stageHeight = stage.getHeight();
        double stageWidth = stage.getWidth();

        double sceneHeight = scene.getHeight();
        double sceneWidth = scene.getWidth();

        heightChanged = (observableValue, numberOld, numberNew) -> {

            double scale = calculateScale(sceneHeight, numberNew);
            root.setScaleY(scale);
            double translateFactor = calculateTranslateFactor(sceneHeight, root.getScaleY());
            root.setTranslateY(sceneHeight * scale * translateFactor);

        };

        widthChanged = (observableValue, numberOld, numberNew) -> {

            double scale = calculateScale(sceneWidth, numberNew);
            root.setScaleX(scale);
            double translateFactor = calculateTranslateFactor(sceneWidth, root.getScaleX());
            root.setTranslateX(sceneWidth * scale * translateFactor);

        };

        scene.heightProperty().addListener(heightChanged);
        scene.widthProperty().addListener(widthChanged);

    }

    public void resizer(Scene scene, Parent root, Number numberNew) {


        double sceneHeight = scene.getHeight();
        double sceneWidth = scene.getWidth();


        double scale = calculateScale(sceneHeight, numberNew);

        root.setScaleY(scale);
        double translateFactor = calculateTranslateFactor(sceneHeight, root.getScaleY());
        root.setTranslateY(sceneHeight * scale * translateFactor);

        root.setScaleX(scale);
        root.setTranslateX(sceneWidth * scale * translateFactor);

        logger.debug("scale: " + scale);
        logger.debug("sceneHeight" + sceneHeight);
        logger.debug("translateFactor: " + translateFactor);

    }

    /**
     * The method determine the starting size
     *
     * @param stage: Stage stage
     * @param scene: Scene scene
     * @param root:  Parent root
     */
    public void determineStartingSize(Stage stage, Scene scene, Parent root) {
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        double sceneHeight = scene.getHeight();
        double sceneWidth = scene.getWidth();


        double sceneRatio = sceneHeight / sceneWidth;

        stage.sizeToScene();

        stage.setMaximized(false);
        stage.setMaximized(true);


    }

    public void removeBind(Scene scene) {
        scene.widthProperty().removeListener(widthChanged);
        scene.heightProperty().removeListener(heightChanged);
    }


}
