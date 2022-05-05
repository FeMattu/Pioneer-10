package com.pioneer10.model;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.nio.file.Path;

public class AnimationComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animJump;

    public AnimationComponent() {
        String spacemanWalkPath = Utils.getPathFileFromResources("assets/Sprites/Anim_Robot_Walk1_v1.1_spritesheet.png");

        animIdle = new AnimationChannel(new Image(spacemanWalkPath), 3, 32, 32,
                Duration.seconds(0.5), 0, 0);

        animWalk = new AnimationChannel(new Image(spacemanWalkPath), 3, 32, 32,
                Duration.seconds(1), 0, 5);

        animWalk = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/Anim_Robot_Jump1_v1.1_spritesheet.png")),
                3, 32, 32,
                Duration.seconds(1), 0, 8);



        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(0.5, 0.5));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed/5);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }

    }

    public void moveRight() {
        speed = 20;

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -20;
        getEntity().setScaleX(-1);
    }

    public void jump() {
        entity.translateY(200);
    }

}