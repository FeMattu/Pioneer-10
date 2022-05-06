package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getInput;

public class GameLevelFromMap extends GameApplication {

    private int height, width;
    public static final float MIN_GRAVITY = 0.1f;
    public static final float MAX_GRAVITY = 20;

    private Entity player;
    private double zoom;

    /**
     * Create a level from a tiled map with ".tmx" extension
     * width and height are the visual of the map.
     * The player of the level is always the same and also its input are the same
     *
     * -A to go left
     * -D to go right
     * -space to jump
     *
     * @param width Width of the map
     * @param height Height of the map
     * @param mapPath Relative path of the map
     * @param background background of the map
     * @param gravity gravity of the map, determines the
     *                gravity of the level, this must be between
     *                {@see MIN_GRAVITY} and {@see MAX_GRAVITY}
     */
    public GameLevelFromMap(int width, int height, String mapPath, String background, float gravity){
        this.width = width;
        this.height = height;
    }

    public void setMapZoom(double zoom){
        this.zoom = zoom;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(width);
        gameSettings.setHeight(height);
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
}
