package com.pioneer10.model;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.entity.level.Level;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class LivMarte extends GameApplication {

    private final int height;
    private final int width;

    public LivMarte(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void initGame(){

        getGameWorld().addEntityFactory(new PioneerFactory());
        Level level = setLevelFromMap("Marte/MappaMarte.tmx");
        Viewport bella= getGameScene().getViewport();
        bella.setBounds(-1500,0,16*16,getAppHeight());
        bella.setZoom(1.5);
        bella.setLazy(true);

    }
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(16*70);
        gameSettings.setHeight(16*20);
        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    public static void main(String[] args){
        launch(args);
    }

}
