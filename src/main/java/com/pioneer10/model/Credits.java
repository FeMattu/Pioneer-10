package com.pioneer10.model;


import com.pioneer10.view.LoadingGameScene;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
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
        Credit.setEffect(neonEffect3());
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
        We.setEffect(neonEffect3());
        We.setFill(Color.WHITE);
        We.setStroke(Color.BLACK);

        We.setFont(Font.font("Consolas", 40));
        root.getChildren().add(We);

        We.setTranslateX(this.getWidth()/5-We.getLayoutBounds().getWidth()/2);
        We.setTranslateY(this.getHeight()/2-We.getLayoutBounds().getHeight()/2);


        Button button8 = new Button("BACK");
        root.getChildren().add(button8);

        button8.setOnMouseClicked(event -> {
            stage.setScene(new LoadingGameScene(stage,width,height));
        });

        button8.setTranslateX(this.getWidth()/9 - button8.getWidth()/2);
        button8.setTranslateY(this.getHeight()/3 - button8.getHeight()/2 + 330);
        button8.setScaleX(4.8);
        button8.setScaleY(2);
        button8.setEffect(neonEffect3());
        button8.setTextFill(Color.WHITE);

    }


    private Blend neonEffect3(){
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