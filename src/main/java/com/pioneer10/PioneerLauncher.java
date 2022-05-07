package com.pioneer10;

import com.pioneer10.model.Utils;
import com.pioneer10.view.LoadingGameScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PioneerLauncher extends Application {

    //Dimensioni minime finestra
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    //dimensioni massime finestra
    private static final int MAX_HEIGHT = 1080;
    private static final int MAX_WIDTH = 1920;

    @Override
    public void start(Stage stage) throws IOException {
        //aggiungo icona
        stage.getIcons().add(new Image(Utils.getPathFileFromResources("assets/icon.png")));
        stage.setTitle("Pioneer-10");

        //loading scene
        stage.setScene(new LoadingGameScene(stage, WIDTH, HEIGHT));
        stage.show();

        //dimensioni minime e massime della finestra
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMaxWidth(MAX_WIDTH);
    }

    public static void main(String[] args) {
        launch(args);
    }
}