package com.pioneer10;

import com.pioneer10.view.MenuScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class PioneerLuncher extends Application {

    private static final int MAX_HEIGHT = 1080;
    private static final int MAX_WIDTH = 1920;

    @Override
    public void start(Stage stage) throws IOException {

        stage.getIcons().add(new Image(Paths.get("src/main/java/resource/assets/image/icon.png").toAbsolutePath().toString()));
        stage.setTitle("Hubble");

        stage.setScene(new MenuScene(stage).getScene());
        stage.show();

        //dimesioni minime e massime della finistra
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMaxWidth(MAX_WIDTH);
    }

    public static void main(String[] args) {
        launch(args);
    }
}