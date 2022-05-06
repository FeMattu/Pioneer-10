package com.pioneer10.model;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LivTerra extends GameApplication {
    private Entity player;

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Terra/Terra.tmx");
        player = spawn("player", 50, 50);
        spawn("background");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 150*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setZoom(2);
        viewport.setLazy(true);

    }
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(150*32/2);
        gameSettings.setHeight(20*32);
        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

}
