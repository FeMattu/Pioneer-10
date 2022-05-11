package com.pioneer10.view;

import com.pioneer10.model.Utils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;

public class LoadingGameScene extends Scene {

    private Group root = new Group();

    public LoadingGameScene(Stage stage, double v, double v1) {
        super(new Group(), v, v1);
        this.setRoot(root);

        // set background
        this.setFill(new ImagePattern(
                new Image(Utils.getPathFileFromResources("assets/space2.jpg"))
        ));

        Text nameOfGame = new Text("Pioneer-10");
        nameOfGame.setEffect(Utils.neonEffect());
        nameOfGame.setFill(Color.WHITE);
        nameOfGame.setStroke(Color.BLACK);


        nameOfGame.setFont(Font.font("Consolas", 100));
        root.getChildren().add(nameOfGame);

        Text info = new Text("""
                to activate background music:
                OPTIONS--> SET MUSIC--> MUSIC ON""");
        info.setEffect(Utils.neonEffect());
        info.setFill(Color.WHITE);
        info.setStroke(Color.BLACK);


        info.setFont(Font.font("Consolas", 30));
        root.getChildren().add(info);

        Button button = new Button("PLAY GAME");
        root.getChildren().add(button);

        Button button2 = new Button("OPTIONS");
        root.getChildren().add(button2);

        Button button3 = new Button("CREDITS");
        root.getChildren().add(button3);

        Button button4 = new Button("TUTORIAL");
        root.getChildren().add(button4);


        button.setOnMouseClicked(event -> {
            stage.setScene(new LevelScene(stage, v, v1));
        });

        button2.setOnMouseClicked(event -> {
            stage.setScene(new OptionScene(stage, v, v1));
        });

        button3.setOnMouseClicked(event -> {
            stage.setScene(new Credits(stage, v, v1));
        });

        button4.setOnMouseClicked(event -> {
            stage.setScene(new Info(stage, v, v1));
        });


        info.setTranslateX(this.getWidth()/4.5-info.getLayoutBounds().getWidth()/2);
        info.setTranslateY(this.getHeight()/6-info.getLayoutBounds().getHeight()/2+580);

        nameOfGame.setTranslateX(this.getWidth()/4.3-nameOfGame.getLayoutBounds().getWidth()/2);
        nameOfGame.setTranslateY(this.getHeight()/5-nameOfGame.getLayoutBounds().getHeight()/2);

        button.setTranslateX(this.getWidth()/13 - button.getWidth()/2);
        button.setTranslateY(this.getHeight()/3 - button.getHeight()/2 + 60);
        button.setScaleX(2.8);
        button.setScaleY(2);
        button.setEffect(Utils.neonEffect());
        button.setTextFill(Color.WHITE);

        button2.setTranslateX(this.getWidth()/12.3 - button2.getWidth()/2);
        button2.setTranslateY(this.getHeight()/3 - button2.getHeight()/2 + 140);
        button2.setScaleX(3.3);
        button2.setScaleY(2);
        button2.setEffect(Utils.neonEffect());
        button2.setTextFill(Color.WHITE);

        button3.setTranslateX(this.getWidth()/12.1 - button3.getWidth()/2);
        button3.setTranslateY(this.getHeight()/3 - button3.getHeight()/2 + 220);
        button3.setScaleX(3.6);
        button3.setScaleY(2);
        button3.setEffect(Utils.neonEffect());
        button3.setTextFill(Color.WHITE);

        button4.setTranslateX(this.getWidth()/11 - button4.getWidth()/2);
        button4.setTranslateY(this.getHeight()/3 - button4.getHeight()/2 + 300);
        button4.setScaleX(5.2);
        button4.setScaleY(2);
        button4.setEffect(Utils.neonEffect());
        button4.setTextFill(Color.WHITE);


    }
}
