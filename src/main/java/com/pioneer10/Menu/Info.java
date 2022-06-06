package com.pioneer10.Menu;

import com.pioneer10.model.Utils;
import com.pioneer10.view.LoadingGameScene;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Info extends Scene{

    public Info(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);


        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        Text Credit = new Text("INFO:");
        Credit.setEffect(Utils.neonEffect());
        Credit.setFill(Color.WHITE);
        Credit.setStroke(Color.BLACK);

        Credit.setFont(Font.font("Consolas", 100));
        root.getChildren().add(Credit);

        Credit.setTranslateX(this.getWidth()/7-Credit.getLayoutBounds().getWidth()/2);
        Credit.setTranslateY(this.getHeight()/5-Credit.getLayoutBounds().getHeight()/2);

        Text We = new Text("""
                - A: MOVE LEFT
                - D: MOVE RIGHT
                - SPACE: JUMP
                - E: ATTACK """);
        We.setEffect(Utils.neonEffect());
        We.setFill(Color.WHITE);
        We.setStroke(Color.BLACK);

        We.setFont(Font.font("Consolas", 40));
        root.getChildren().add(We);

        We.setTranslateX(this.getWidth()/6-We.getLayoutBounds().getWidth()/2);
        We.setTranslateY(this.getHeight()/2-We.getLayoutBounds().getHeight()/2);


        Button button11 = new Button("BACK");
        root.getChildren().add(button11);

        button11.setTranslateX(this.getWidth()/9 - button11.getWidth()/2);
        button11.setTranslateY(this.getHeight()/3 - button11.getHeight()/2 + 330);
        button11.setScaleX(4.8);
        button11.setScaleY(2);
        button11.setEffect(Utils.neonEffect());
        button11.setTextFill(Color.WHITE);

        button11.setOnMouseClicked(event -> {
            stage.setScene(new LoadingGameScene(stage,width,height));
        });

    }
}
