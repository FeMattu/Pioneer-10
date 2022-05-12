package com.pioneer10.view;

import com.almasb.fxgl.dsl.FXGL;
import com.pioneer10.model.Utils;
import com.pioneer10.view.LoadingGameScene;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class music extends Scene{

    public music(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);


        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        Text MUSIC = new Text("MUSIC:");
        MUSIC.setEffect(Utils.neonEffect());
        MUSIC.setFill(Color.WHITE);
        MUSIC.setStroke(Color.BLACK);

        MUSIC.setFont(Font.font("Consolas", 100));
        root.getChildren().add(MUSIC);

        MUSIC.setTranslateX(this.getWidth()/7-MUSIC.getLayoutBounds().getWidth()/2);
        MUSIC.setTranslateY(this.getHeight()/5-MUSIC.getLayoutBounds().getHeight()/2);

        Button button5 = new Button("MUSIC: ON");
        root.getChildren().add(button5);

        Button button6 = new Button("MUSIC: OFF");
        root.getChildren().add(button6);

        button5.setTranslateX(this.getWidth()/12.8 - button5.getWidth()/2);
        button5.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 150);
        button5.setScaleX(3);
        button5.setScaleY(2);
        button5.setEffect(Utils.neonEffect());
        button5.setTextFill(Color.WHITE);

        button6.setTranslateX(this.getWidth()/12.8 - button6.getWidth()/2);
        button6.setTranslateY(this.getHeight()/3 - button6.getHeight()/2 + 230);
        button6.setScaleX(3);
        button6.setScaleY(2);
        button6.setEffect(Utils.neonEffect());
        button6.setTextFill(Color.WHITE);

        String musicFile = Utils.getPathFileFromResources("assets/music/lost_and_notfound.mp3");
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
        mediaPlayer2.setVolume(1);

        //Adding event Filter
        button5.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> mediaPlayer2.play());

        mediaPlayer2.seek(Duration.minutes(20));

        //Adding event Filter
        button6.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> mediaPlayer2.stop());


        Button button11 = new Button("BACK");
        root.getChildren().add(button11);

        button11.setTranslateX(this.getWidth()/11.2 - button11.getWidth()/2);
        button11.setTranslateY(this.getHeight()/3 - button11.getHeight()/2 + 310);
        button11.setScaleX(5.2);
        button11.setScaleY(2);
        button11.setEffect(Utils.neonEffect());
        button11.setTextFill(Color.WHITE);

        button11.setOnMouseClicked(event -> {
            stage.setScene(new OptionScene(stage,width,height));
        });

    }
}