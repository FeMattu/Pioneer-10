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
        button5.setEffect(Utils.neonEffect());
        button5.setTextFill(Color.WHITE);

        Button button6 = new Button("SET BRIGHTNESS");
        root.getChildren().add(button6);

        button6.setTranslateX(this.getWidth()/15.7 - button5.getWidth()/2);
        button6.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 230);
        button6.setScaleX(2);
        button6.setScaleY(2);
        button6.setEffect(Utils.neonEffect());
        button6.setTextFill(Color.WHITE);

        Button button7 = new Button("BACK");
        root.getChildren().add(button7);

        button7.setOnMouseClicked(event -> {
            stage.setScene(new LoadingGameScene(stage,width,height));
        });


        button7.setTranslateX(this.getWidth()/11.5 - button5.getWidth()/2);
        button7.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 310);
        button7.setScaleX(4.8);
        button7.setScaleY(2);
        button7.setEffect(Utils.neonEffect());
        button7.setTextFill(Color.WHITE);



        Text Options = new Text("OPTION:");
        Options.setEffect(Utils.neonEffect());
        Options.setFill(Color.WHITE);
        Options.setStroke(Color.BLACK);

        Options.setFont(Font.font("Consolas", 100));
        root.getChildren().add(Options);

        Options.setTranslateX(this.getWidth()/6-Options.getLayoutBounds().getWidth()/2);
        Options.setTranslateY(this.getHeight()/5-Options.getLayoutBounds().getHeight()/2);



    }
}


