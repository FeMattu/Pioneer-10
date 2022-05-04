package com.pioneer10.model;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.components.IrremovableComponent;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class LivGiove extends GameApplication {

    private final int height;
    private final int width;

    public LivGiove(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Giove/Giove.tmx");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 70*16, getAppHeight());
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
