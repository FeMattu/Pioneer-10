package com.pioneer10.view;

import com.pioneer10.controller.ControllerMenu;
import com.pioneer10.model.Planet;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class MenuScene extends Scene{


    private Group root = new Group();
    private Stage stage;
    private ControllerMenu controller;
    private PerspectiveCamera camera;

    public MenuScene(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        this.setRoot(root);
        controller = new ControllerMenu(stage);
        this.stage = stage;

        //background
        Image background = new Image(Paths.get("src/main/resources/assets/background.jpg").toAbsolutePath().toString());
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        //camera per la visualizzazione di scene 3D
        camera = new PerspectiveCamera(true); //camera al centro della scena
        camera.setDepthTest(DepthTest.ENABLE);
        this.setCamera(camera);
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

    //inizializzo input della tastiera
    private void initKeyboardControl(Stage stage, Node terra) {
        stage.addEventHandler(KeyEvent.ANY, event -> {
            switch (event.getCode()){
                case W, UP:
                    terra.setTranslateZ(terra.getTranslateZ()+10);
                    break;
                case S, DOWN:
                    terra.setTranslateZ(terra.getTranslateZ()-10);
                    break;
                case A, LEFT:
                    terra.setTranslateX(terra.getTranslateX()-10);
                    break;
                case D, RIGHT:
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
