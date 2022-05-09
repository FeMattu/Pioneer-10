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
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.pioneer10.model.PioneerEntityType.PLAYER;

public class  LivMarte extends GameApplication {

    private Entity player;
    private Viewport viewport ;
    private int coinsGrabbed;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(640);
        gameSettings.setTitle("Pioneer-10\nMarte");
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 200);

        PhysicsWorld physicsWorld = getPhysicsWorld();
        physicsWorld.addCollisionHandler(new CollisionHandler(PioneerEntityType.PLAYER, PioneerEntityType.COIN) {
            @Override
            public void onCollision(Entity player, Entity coin) {
                coin.removeFromWorld();
                coinsGrabbed++;
               // getGameState().increment("score", 1);
            }
        });

    }

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Marte/Marte.tmx");
        player = getGameWorld().getEntitiesByType(PLAYER).get(0);

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 180*16, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);

    }

    @Override
    protected void onUpdate(double tpf) {
        //inc("levelTime", tpf);
        if (player.getY() > getAppHeight()) {
            getGameWorld().removeEntity(player);
            player = spawn("player", 50, 50);
           // viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
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
