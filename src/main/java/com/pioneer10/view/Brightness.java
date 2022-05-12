package com.pioneer10.view;

import com.pioneer10.model.Utils;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Brightness extends Scene {


    public Brightness(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);

        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        Text Brightness = new Text("BRIGHTNESS:");
        Brightness.setEffect(Utils.neonEffect());
        Brightness.setFill(Color.WHITE);
        Brightness.setStroke(Color.BLACK);

        Brightness.setFont(Font.font("Consolas", 100));
        root.getChildren().add(Brightness);

        Brightness.setTranslateX(this.getWidth()/7-Brightness.getLayoutBounds().getWidth()/2);
        Brightness.setTranslateY(this.getHeight()/5-Brightness.getLayoutBounds().getHeight()/2);


    }
}