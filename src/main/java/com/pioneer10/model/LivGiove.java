package com.pioneer10.model;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class LivGiove extends GameApplication {

    private Entity player;

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        //setLevelFromMap("Giove/Giove.tmx");
        setLevelFromMap("Terra/MappaTerra.tmx");
        //setLevelFromMap("Marte/Marte.tmx");
        player = spawn("player", 50, 50);
        spawn("background");


        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 150*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);

    }
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 400);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerAnimationComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerAnimationComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerAnimationComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerAnimationComponent.class).jump();
            }
        }, KeyCode.SPACE, VirtualButton.A);

        //player.getComponent(PlayerAnimationComponent.class).stop();
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(32*150/4);
        gameSettings.setHeight(32*20);

    }
}
