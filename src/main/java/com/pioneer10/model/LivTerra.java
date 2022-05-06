package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;

public class LivTerra extends GameApplication {

    private Entity player1;

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        //setLevelFromMap("Giove/Giove.tmx");
        Level level= setLevelFromMap("Terra/MappaTerra.tmx");
        //setLevelFromMap("Marte/Marte.tmx");
        player1 = spawn("player", 50, 50);
        //spawn("enemy", 20, 20);
        //spawn("background");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 150*32, getAppHeight());
        viewport.bindToEntity(player1, getAppWidth() / 2, getAppHeight() / 2);
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
                player1.getComponent(PlayerControlComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerControlComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player1.getComponent(PlayerControlComponent.class).jump();
            }
        }, KeyCode.SPACE, VirtualButton.A);
    }

    @Override
    protected void onUpdate(double tpf) {
        //inc("levelTime", tpf);

        if (player1.getY() > getAppHeight()) {
            player1 = spawn("player", 50, 50);
            Viewport viewport = getGameScene().getViewport();
            viewport.setBounds(0, 0, 150*32, getAppHeight());
            viewport.bindToEntity(player1, getAppWidth() / 2, getAppHeight() / 2);
            viewport.setLazy(true);
        }
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(32*150/4);
        gameSettings.setHeight(32*20);

    }

    public static void main(String[] args){launch(args);}
}
