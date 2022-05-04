package com.pioneer10.view;

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

import java.nio.file.Path;

public class LoadingGameScene extends Scene {

    private Group root = new Group();

    public LoadingGameScene(Stage stage, double v, double v1) {
        super(new Group(), v, v1);
        this.setRoot(root);

        // set background
        this.setFill(new ImagePattern(
                new Image(Utils.getPathFileFromResources("assets/pioneer-10.jpg"))
        ));

        Text nameOfGame = new Text("Pioneer-10");
        nameOfGame.setEffect(neonEffect());
        nameOfGame.setFont(Font.font(null, 70));
        root.getChildren().add(nameOfGame);

        Button button = new Button("Enter in menu");
        root.getChildren().add(button);

        button.setOnMouseClicked(event -> {
            stage.setScene(new MenuScene(stage, v, v1));
        });

        nameOfGame.setTranslateX(this.getWidth()/2-nameOfGame.getLayoutBounds().getWidth()/2);
        nameOfGame.setTranslateY(this.getHeight()/4-nameOfGame.getLayoutBounds().getHeight()/2);
        button.setTranslateX(this.getWidth()/2 - button.getWidth()/2);
        button.setTranslateY(this.getHeight()/2 - button.getHeight()/2 + 200);

    }

    private Blend neonEffect(){
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.rgb(241, 58, 0));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.rgb(254, 235, 66));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.rgb(241, 58, 0));
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
