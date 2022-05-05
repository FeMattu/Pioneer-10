package com.pioneer10.model;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LivTerra extends GameApplication {


    //private final int height;
    //private final int width;

   /* public LivTerra(int width, int height){
        this.width = width;
        this.height = height;
    }

    public LivTerra(int height, int width) {
        this.height = height;
        this.width = width;
    }*/

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Terra/Terra.tmx");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 70*16/2+10, getAppHeight());
        viewport.setZoom(2);
        viewport.setLazy(true);

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

}
