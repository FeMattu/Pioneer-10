package com.pioneer10.view;

import com.almasb.fxgl.app.FXGLPane;
import com.almasb.fxgl.app.GameApplication;
import com.pioneer10.model.Game;
import com.pioneer10.model.Planet;
import com.pioneer10.model.PlanetLevel;
import javafx.scene.Group;
import javafx.scene.Scene;

public class GioveScene extends Scene{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private Scene scene;

    public GioveScene(){
        super(new Group());
        FXGLPane level1 = GameApplication.embeddedLaunch(new Game(WIDTH, HEIGHT));
        this.setRoot(level1);
    }

    public Scene getScene(){return this;}
}
