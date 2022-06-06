package com.pioneer10.view;


import com.pioneer10.model.Utils;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Credits extends Scene{

    public Credits(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);


        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        Text Credit = new Text("CREDITS:");
        Credit.setEffect(Utils.neonEffect());
        Credit.setFill(Color.WHITE);
        Credit.setStroke(Color.BLACK);

        Credit.setFont(Font.font("Consolas", 100));
        root.getChildren().add(Credit);

        Credit.setTranslateX(this.getWidth()/5-Credit.getLayoutBounds().getWidth()/2);
        Credit.setTranslateY(this.getHeight()/5-Credit.getLayoutBounds().getHeight()/2);

        Text We = new Text("""
                - Federico Mattucci
                - Manuel Arcuti
                - Alessandro Augusto
                - Mario Popa
                - Gabriele Tarlarini""");
        We.setEffect(Utils.neonEffect());
        We.setFill(Color.WHITE);
        We.setStroke(Color.BLACK);

        We.setFont(Font.font("Consolas", 40));
        root.getChildren().add(We);

        We.setTranslateX(this.getWidth()/5-We.getLayoutBounds().getWidth()/2);
        We.setTranslateY(this.getHeight()/2-We.getLayoutBounds().getHeight()/2);


        Button button8 = new Button("BACK");
        root.getChildren().add(button8);


        button8.setTranslateX(this.getWidth()/9 - button8.getWidth()/2);
        button8.setTranslateY(this.getHeight()/3 - button8.getHeight()/2 + 330);
        button8.setScaleX(4.8);
        button8.setScaleY(2);
        button8.setEffect(Utils.neonEffect());
        button8.setTextFill(Color.WHITE);

    }
}