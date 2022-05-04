package com.pioneer10.view;

import com.pioneer10.model.Credits;
import com.pioneer10.model.OptionScene;
import com.pioneer10.model.Utils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        nameOfGame.setEffect(neonEffect());
        nameOfGame.setFill(Color.WHITE);
        nameOfGame.setStroke(Color.BLACK);


        nameOfGame.setFont(Font.font("Consolas", 100));
        root.getChildren().add(nameOfGame);

        Button button = new Button("PLAY GAME");
        root.getChildren().add(button);

        Button button2 = new Button("OPTIONS");
        root.getChildren().add(button2);

        Button button3 = new Button("CREDITS");
        root.getChildren().add(button3);

        Button button4 = new Button("EXIT");
        root.getChildren().add(button4);


        button.setOnMouseClicked(event -> {
            stage.setScene(new MenuScene(stage, v, v1));
        });

        button2.setOnMouseClicked(event -> {
            stage.setScene(new OptionScene(stage, v, v1));
        });

        button3.setOnMouseClicked(event -> {
            stage.setScene(new Credits(stage, v, v1));
        });





        nameOfGame.setTranslateX(this.getWidth()/4.3-nameOfGame.getLayoutBounds().getWidth()/2);
        nameOfGame.setTranslateY(this.getHeight()/5-nameOfGame.getLayoutBounds().getHeight()/2);

        button.setTranslateX(this.getWidth()/13 - button.getWidth()/2);
        button.setTranslateY(this.getHeight()/3 - button.getHeight()/2 + 150);
        button.setScaleX(2.8);
        button.setScaleY(2);
        button.setEffect(neonEffect());
        button.setTextFill(Color.WHITE);

        button2.setTranslateX(this.getWidth()/12.3 - button2.getWidth()/2);
        button2.setTranslateY(this.getHeight()/3 - button2.getHeight()/2 + 210);
        button2.setScaleX(3.3);
        button2.setScaleY(2);
        button2.setEffect(neonEffect());
        button2.setTextFill(Color.WHITE);

        button3.setTranslateX(this.getWidth()/12.1 - button2.getWidth()/2);
        button3.setTranslateY(this.getHeight()/3 - button2.getHeight()/2 + 270);
        button3.setScaleX(3.6);
        button3.setScaleY(2);
        button3.setEffect(neonEffect());
        button3.setTextFill(Color.WHITE);

        button4.setTranslateX(this.getWidth()/11 - button2.getWidth()/2);
        button4.setTranslateY(this.getHeight()/3 - button2.getHeight()/2 + 330);
        button4.setScaleX(5.7);
        button4.setScaleY(2);
        button4.setEffect(neonEffect());
        button4.setTextFill(Color.WHITE);



    }

    private Blend neonEffect(){
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
