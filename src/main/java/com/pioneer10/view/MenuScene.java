package com.pioneer10.view;

import com.almasb.fxgl.app.scene.Viewport;
import com.pioneer10.controller.ControllerMenu;
import com.pioneer10.model.Planet;
import com.pioneer10.model.Utils;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        //creo marte
        Node marte = Planet.MARS.getNode();
        marte.setTranslateX(terra.getTranslateX()- distanceFromCenterPlanet);
        marte.setTranslateY(terra.getTranslateY());
        marte.setTranslateZ(terra.getTranslateZ()+100);
        //creo giove
        Node giove = Planet.JUPITER.getNode();
        giove.setTranslateX(terra.getTranslateX()+ distanceFromCenterPlanet);
        giove.setTranslateY(terra.getTranslateY());
        giove.setTranslateZ(terra.getTranslateZ()+100);

        //aggiuno i pianeti
        root.getChildren().add(marte);
        root.getChildren().add(giove);
        root.getChildren().add(terra);

        terra.setOnMouseClicked(event ->{
            controller.changeSceneOnEarthClick();
        });

        giove.setOnMouseClicked(event ->{
            controller.changSceneOnGioveClick();
        });

        marte.setOnMouseClicked(event ->{
            controller.changSceneOnGioveClick();
        });

        //aggiungere tutti gli oggetti 2D da qui, se vengono aggiunti
        //sopra potrebbero interferire con gli oggetti 3D


        Text textForJupiter = new Text("Level 1-\n" + "Jupiter");
        textForJupiter.setEffect(neonEffect5());
        textForJupiter.setFill(Color.WHITE);
        textForJupiter.setStroke(Color.BLACK);
        textForJupiter.setFont(Font.font("Consolas", 30.5));

        //posizionamento del testo in linea con giove
        textForJupiter.setTranslateX(giove.getTranslateX()-textForJupiter.getLayoutBounds().getWidth()/2);
        textForJupiter.setTranslateY(giove.getTranslateY()+ 250 -textForJupiter.getLayoutBounds().getHeight()/2);

        root.getChildren().add(textForJupiter);

        Text textForEarth = new Text("Level 1-\n" + "Earth");
        textForEarth.setEffect(neonEffect5());
        textForEarth.setFill(Color.WHITE);
        textForEarth.setStroke(Color.BLACK);
        textForEarth.setFont(Font.font("Consolas", 30.5));

        //posizionamento del testo in linea con la terra
        textForEarth.setTranslateX(terra.getTranslateX()-textForEarth.getLayoutBounds().getWidth()/2);
        textForEarth.setTranslateY(terra.getTranslateY()+ 270 -textForEarth.getLayoutBounds().getHeight()/2);

        root.getChildren().add(textForEarth);
    }



    private Blend neonEffect5(){
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(66, 119, 254, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.rgb(0, 121, 141, 1));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.rgb(255, 255, 255, 1));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.rgb(255, 255, 255, 1));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        return blend;
    }
}
