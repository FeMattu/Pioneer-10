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
        camera = new PerspectiveCamera(true); //camera al centro della scena
        camera.setDepthTest(DepthTest.ENABLE);
        this.setCamera(camera);
        //volume di visualizzazione
        camera.setNearClip(0);
        camera.setFarClip(1000);
        camera.setTranslateZ(-900); //sposto la camera indietro per visualizzare correttamente gli oggetti

        //creo la terra
        Node terra = Planet.EARTH.getNode();
        Node urano = Planet.URANUS.getNode();
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

        giove.setOnMouseClicked(event ->{
            controller.changSceneOnGioveClick();
        });

        marte.setOnMouseClicked(event ->{
            controller.changSceneOnGioveClick();
        });

        Text level1 = new Text("Level 1-\n" + "Jupiter");
        level1.setEffect(neonEffect5());
        level1.setFill(Color.WHITE);
        level1.setStroke(Color.BLACK);


        level1.setFont(Font.font("Consolas", 30.5));
        root.getChildren().add(level1);

        level1.setTranslateX(this.getWidth()/6.5-level1.getLayoutBounds().getWidth()/2);
        level1.setTranslateY(this.getHeight()/4.5-level1.getLayoutBounds().getHeight()/2);


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
