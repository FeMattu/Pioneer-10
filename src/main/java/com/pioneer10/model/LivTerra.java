package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pioneer10.model.PioneerEntityType.COIN;
import static com.pioneer10.model.PioneerEntityType.PLAYER;

public class LivTerra extends GameApplication {

    private final int MAX_VITE = 3;
    private Entity player;
    private Viewport viewport;
    private int vite;
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
        player = spawn("player");
        //player = (Entity) getGameWorld().getEntitiesByType(PLAYER);
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
    }

    @Override
    protected void onUpdate(double tpf) {
        //inc("levelTime", tpf);
        if (player.getY() > getAppHeight()) {
            if(vite > 0){
                getGameWorld().removeEntity(player);
                player = spawn("player", 50, 50);
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                vite--;
            }else{
                getDialogService().showMessageBox("You are died");
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
