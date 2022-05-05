package com.pioneer10.view;

import com.almasb.fxgl.app.FXGLPane;
import com.almasb.fxgl.app.GameApplication;
import com.pioneer10.model.LivGiove;
import com.pioneer10.model.LivTerra;
import javafx.scene.Group;
import javafx.scene.Scene;

public class TerraScene extends Scene{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private Scene scene;

    public TerraScene(){
        super(new Group());
        FXGLPane level1 = GameApplication.embeddedLaunch(new LivTerra());
        this.setRoot(level1);
    }

    public Scene getScene(){return this;}
}
