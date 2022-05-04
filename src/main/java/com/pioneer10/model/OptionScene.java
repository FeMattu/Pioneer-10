package com.pioneer10.model;


import com.pioneer10.model.Utils;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class OptionScene extends Scene{


    public OptionScene(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);

        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);


        Button button5 = new Button("SET MUSIC");
        root.getChildren().add(button5);

        button5.setTranslateX(this.getWidth()/13 - button5.getWidth()/2);
        button5.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 150);
        button5.setScaleX(2.8);
        button5.setScaleY(2);
        button5.setEffect(neonEffect2());
        button5.setTextFill(Color.WHITE);

        Button button6 = new Button("SET BRIGHTNESS");
        root.getChildren().add(button6);

        button6.setTranslateX(this.getWidth()/15.7 - button5.getWidth()/2);
        button6.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 210);
        button6.setScaleX(2);
        button6.setScaleY(2);
        button6.setEffect(neonEffect2());
        button6.setTextFill(Color.WHITE);

        Button button7 = new Button("BACK");
        root.getChildren().add(button7);

        button7.setTranslateX(this.getWidth()/11.5 - button5.getWidth()/2);
        button7.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 270);
        button7.setScaleX(4.8);
        button7.setScaleY(2);
        button7.setEffect(neonEffect2());
        button7.setTextFill(Color.WHITE);



        Text Options = new Text("OPTION:");
        Options.setEffect(neonEffect2());
        Options.setFill(Color.WHITE);
        Options.setStroke(Color.BLACK);

        Options.setFont(Font.font("Consolas", 100));
        root.getChildren().add(Options);

        Options.setTranslateX(this.getWidth()/6-Options.getLayoutBounds().getWidth()/2);
        Options.setTranslateY(this.getHeight()/5-Options.getLayoutBounds().getHeight()/2);



    }


    private Blend neonEffect2(){
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


