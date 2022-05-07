package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pioneer10.model.PioneerEntityType.COIN;
import static com.pioneer10.model.PioneerEntityType.PLAYER;
import static com.pioneer10.model.Planet.EARTH;

public class LivTerra extends GameApplication {

    private final int MAX_VITE = 3;
    private Entity player;
    private Viewport viewport;
    private int vite, coinsGrabbed;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(150*32/4);
        gameSettings.setHeight(20*32);
        gameSettings.setTitle("Pioneer-10\nTerra");
    }

    @Override
    public void initGame(){

        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Terra/MappaTerra.tmx");
        player = getGameWorld().getEntitiesByType(PLAYER).get(0);
        spawn("backgroundTerra");

        vite = MAX_VITE;

        viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 150*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 400);

        //Collisione con monete
        onCollisionOneTimeOnly(PLAYER, COIN, (player, coin) -> {
            getGameWorld().removeEntity(coin);
            coinsGrabbed++;
            System.out.println("Coin grabbed: "+ coinsGrabbed);
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        //inc("levelTime", tpf);
        if (player.getY() > getAppHeight()) {
            if(vite > 0){
                Double posX = player.getX();
                Double posY = Double.valueOf(getAppHeight()/2);
                getGameWorld().removeEntity(player);
                player = spawn("player", posX, posY-100);
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                getGameWorld().removeEntity(getGameWorld().getEntitiesByType(EARTH).get(0));
                vite--;
            }else{
                getDialogService().showMessageBox("You are died", () ->{

                });
            }
        }
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControlComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControlComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerControlComponent.class).jump();
            }
        }, KeyCode.SPACE, VirtualButton.A);
    }

    public static void main(String[] args){launch(args);}
}
