package com.pioneer10.view;

import com.pioneer10.controller.ControllerMenu;
import com.pioneer10.model.Planet;
import com.pioneer10.model.Utils;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LevelScene extends Scene{
    private Group root = new Group();
    private Stage stage;
    private ControllerMenu controller;
    private PerspectiveCamera camera;

    public LevelScene(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        this.setRoot(root);
        controller = new ControllerMenu(stage);
        this.stage = stage;

        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/background.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        //camera per la visualizzazione di scene 3D
        camera = new PerspectiveCamera(); //camera al centro della scena
        camera.setDepthTest(DepthTest.ENABLE);
        this.setCamera(camera);
        //volume di visualizzazione
        camera.setNearClip(0);
        camera.setFarClip(1000);
        //camera.setTranslateZ(); //sposto la camera indietro per visualizzare correttamente gli oggetti


        int distanceFromCenterPlanet = 350;
        //creo la terra
        Node terra = Planet.EARTH.getNode();
        terra.setTranslateX(width/2);
        terra.setTranslateY(height/2);
        //creo nettuno
        Node nettuno = Planet.NEPTUNE.getNode();
        nettuno.setTranslateX(terra.getTranslateX()- distanceFromCenterPlanet);
        nettuno.setTranslateY(terra.getTranslateY());
        nettuno.setTranslateZ(terra.getTranslateZ()+100);
        //creo giove
        Node giove = Planet.JUPITER.getNode();
        giove.setTranslateX(terra.getTranslateX()+ distanceFromCenterPlanet);
        giove.setTranslateY(terra.getTranslateY());
        giove.setTranslateZ(terra.getTranslateZ()+100);

        //aggiuno i pianeti
        root.getChildren().add(nettuno);
        root.getChildren().add(giove);
        root.getChildren().add(terra);

        terra.setOnMouseClicked(event ->{
            controller.changeSceneOnEarthClick();
        });

        giove.setOnMouseClicked(event ->{
            controller.changSceneOnGioveClick();
        });

        nettuno.setOnMouseClicked(event ->{
            controller.changSceneOnMarteClick();
        });

        //aggiungere tutti gli oggetti 2D da qui, se vengono aggiunti
        //sopra potrebbero interferire con gli oggetti 3D

        Text text = new Text("SELECT A LEVEL TO CONTINUE:");
        text.setEffect(Utils.neonEffect());
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setFont(Font.font("Consolas", 30.5));

        text.setTranslateX(giove.getTranslateX()-text.getLayoutBounds().getWidth()/2);
        text.setTranslateY(giove.getTranslateY()+ 240 -text.getLayoutBounds().getHeight()/2);

        Text textForJupiter = new Text("Level 2-\n" + "Jupiter");
        textForJupiter.setEffect(Utils.neonEffect());
        textForJupiter.setFill(Color.WHITE);
        textForJupiter.setStroke(Color.BLACK);
        textForJupiter.setFont(Font.font("Consolas", 30.5));

        //posizionamento del testo in linea con giove
        textForJupiter.setTranslateX(giove.getTranslateX()-textForJupiter.getLayoutBounds().getWidth()/2);
        textForJupiter.setTranslateY(giove.getTranslateY()+ 250 -textForJupiter.getLayoutBounds().getHeight()/2);

        root.getChildren().add(textForJupiter);

        Text textForEarth = new Text("Level 1-\n" + " Earth");
        textForEarth.setEffect(Utils.neonEffect());
        textForEarth.setFill(Color.WHITE);
        textForEarth.setStroke(Color.BLACK);
        textForEarth.setFont(Font.font("Consolas", 30.5));

        //posizionamento del testo in linea con la terra
        textForEarth.setTranslateX(terra.getTranslateX()-textForEarth.getLayoutBounds().getWidth()/2);
        textForEarth.setTranslateY(terra.getTranslateY()+ 270 -textForEarth.getLayoutBounds().getHeight()/2);
        root.getChildren().add(textForEarth);

        Text textForNeptune = new Text("Level 3-\n" + " Neptune");
        textForNeptune.setEffect(Utils.neonEffect());
        textForNeptune.setFill(Color.WHITE);
        textForNeptune.setStroke(Color.BLACK);
        textForNeptune.setFont(Font.font("Consolas", 30.5));

        //posizionamento del testo in linea con nettuno
        textForNeptune.setTranslateX(nettuno.getTranslateX()-textForNeptune.getLayoutBounds().getWidth()/2);
        textForNeptune.setTranslateY(nettuno.getTranslateY()+ 250 -textForNeptune.getLayoutBounds().getHeight()/2);

        root.getChildren().add(textForNeptune);

        Button button = new Button("BACK");
        root.getChildren().add(button);

        button.setTranslateX(this.getWidth()/16 - button.getWidth()/2);
        button.setTranslateY(this.getHeight()/1.3 - button.getHeight()/2 + 100);
        button.setScaleX(2.8);
        button.setScaleY(2);
        button.setEffect(Utils.neonEffect());
        button.setTextFill(Color.WHITE);

        button.setOnMouseClicked(event -> {
            stage.setScene(new LoadingGameScene(stage,width,height));
        });


    }
}
