package com.pioneer10.view.CSS;

import com.almasb.fxgl.app.FXGLPane;
import com.almasb.fxgl.app.GameApplication;
import com.pioneer10.model.Game;
import javafx.scene.Group;
import javafx.scene.Scene;

public class MarteScene extends Scene{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private Scene scene;

    public MarteScene(){
        super(new Group());
        FXGLPane level3 = GameApplication.embeddedLaunch(new Game(WIDTH, HEIGHT));
        this.setRoot(level3);
    }

    public Scene getScene(){return this;}
}
