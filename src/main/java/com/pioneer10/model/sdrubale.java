package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.*;

import static com.almasb.fxgl.dsl.FXGL.*;

public class sdrubale extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(10*64);
        gameSettings.setWidth(15*64);
    }

    Entity player;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControl.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerControl.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);
       /* getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).left();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
                //super.onAction();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("jump") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControl.class).jump();
            }
        }, KeyCode.W);*/
    }

    @Override
    protected void initGame() {
        // super.initGame();
        //getGameWorld().setLevelFromMap("PrimaMappadecente.json");
        getGameWorld().addEntityFactory(new MArioAFactory());
        //TextLevelParser parser = new TextLevelParser(new MArioAFactory());
        // Level level = parser.parse("levels/level1.txt");
        //getGameWorld().setLevel(level);
        Level level = setLevelFromMap("tmx/PrimaMappadecente.tmx");
        Viewport bella= getGameScene().getViewport();
        bella.setBounds(-1500,0,16*16,getAppHeight());
        bella.setZoom(1.5);
        bella.setLazy(true);

        player = null;
        // player must be spawned after call to nextLevel, otherwise player gets removed
        // before the update tick _actually_ adds the player to game world
        player = spawn("player", 50, 50);
        set("player", player);
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 250 * 16, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);





    }

    public static void main(String[] args){
        launch(args);
    }
}
