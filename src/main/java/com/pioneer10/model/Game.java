package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    public static int WIDTH;
    public static int HEIGHT;

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.D, () -> {
            player.translateX(5); // move right 5 pixels
            player.getComponent(AnimationComponent.class).moveRight();
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.A, () -> {
            player.translateX(-5); // move left 5 pixels
            player.getComponent(AnimationComponent.class).moveLeft();
            inc("pixelsMoved", -5);
        });

        onKey(KeyCode.W, () -> {
            player.translateY(-5); // move up 5 pixels
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.S, () -> {
            player.translateY(5); // move down 5 pixels
            inc("pixelsMoved", +5);
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    private Entity player;

    @Override
    protected void initGame() {
        player = entityBuilder()
                .at(300, 300)
                .with(new AnimationComponent())
                .buildAndAttach();
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph

    }
}
