package com.pioneer10.view;

import com.pioneer10.controller.ControllerMondo;
import com.pioneer10.model.Planet;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class MenuScene {
    //Dimensioni finestra
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private Scene scene;
    private Stage stage;
    private ControllerMondo controller;
    private PerspectiveCamera camera;

    public MenuScene(Stage stage) {
        controller = new ControllerMondo(stage);
        this.stage = stage;

        //gruppo dei mondi
        Group root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);

        //background
        Image background = new Image(Paths.get("src/main/java/resource/assets/image/background.jpg").toAbsolutePath().toString());
        ImagePattern patternBackground = new ImagePattern(background);
        scene.setFill(patternBackground);

        //camera per la visualizzazione di scene 3D
        camera = new PerspectiveCamera(true); //camera al centro della scena
        camera.setDepthTest(DepthTest.ENABLE);
        scene.setCamera(camera);
        //volume di visualizzazione
        camera.setNearClip(0);
        camera.setFarClip(1000);
        camera.setTranslateZ(-900); //sposto la camera indietro per visualizzare correttamente gli oggetti

        //creo la terra
        Node terra = Planet.EARTH.getNode();
        //creo marte
        Node marte = Planet.MARS.getNode();
        marte.setTranslateX(-200);
        marte.setTranslateZ(100);
        //creo giove
        Node giove = Planet.JUPITER.getNode();
        giove.setTranslateX(200);
        giove.setTranslateZ(100);

        //aggiuno i pianeti
        root.getChildren().add(marte);
        root.getChildren().add(giove);
        root.getChildren().add(terra);

        terra.setOnMouseClicked(event ->{
            controller.changeSceneOnEarthClick();
        });

        initKeyboardControl(stage, terra);
        initMouseControl(root, terra);
    }

    /**
     * return the menu scene
     *
     * @return menu scene
     */
    public Scene getScene() {return scene;}

    //inizializzo input della tastiera
    private void initKeyboardControl(Stage stage, Node terra) {
        stage.addEventHandler(KeyEvent.ANY, event -> {
            String tasto = event.getCharacter();

            switch (tasto){
                case "w", "W":
                    terra.setTranslateZ(terra.getTranslateZ()+10);
                    break;
                case "s", "S":
                    terra.setTranslateZ(terra.getTranslateZ()-10);
                    break;
                case "a", "A":
                    terra.setTranslateX(terra.getTranslateX()-10);
                    break;
                case "d", "D":
                    terra.setTranslateX(terra.getTranslateX()+10);
                    break;
            }
        });
    }
    //inizializzo input dal mouse
    private void initMouseControl(Group root, Node planet){
        //zoom
        stage.addEventHandler(ScrollEvent.SCROLL, event ->{
            double direction = event.getDeltaY();
            System.out.println(direction);
            camera.setTranslateZ(camera.translateZProperty().getValue()+direction);
            System.out.println("Camera Z: "+camera.getTranslateZ());
        });
    }

}
