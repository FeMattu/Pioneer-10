package com.pioneer10.model;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class PlayerControlComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animJump;
    private PhysicsComponent physics;
    private LocalTimer shootTimer;


    public PlayerControlComponent() {
        String spacemanWalkPath = Utils.getPathFileFromResources("assets/Sprites/Anim_Robot_Walk1_v1.1_spritesheet.png");

        animIdle = new AnimationChannel(new Image(spacemanWalkPath), 4, 32, 32,
                Duration.seconds(1), 2, 2);

        animWalk = new AnimationChannel(new Image(spacemanWalkPath), 3, 32, 32,
                Duration.seconds(0.5), 0, 3);

        animJump = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/Anim_Robot_Jump1_v1.1_spritesheet.png")),
                3, 32, 32,
                Duration.seconds(1.5), 0, 8);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else if(physics.isMovingY() && physics.getVelocityY() < 0){
            if (texture.getAnimationChannel() != animJump) {
                texture.loopAnimationChannel(animJump);
            }
        }else{
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void right() {
        physics.setVelocityX(100);
        getEntity().setScaleX(1);
    }

    public void left() {
        physics.setVelocityX(-100);
        getEntity().setScaleX(-1);
    }

    public void jump() {
        physics.setVelocityY(-200);
    }

    public void shoot() {
        double angle = getEntity().getRotation();
        System.out.println(angle);
        spawn("Bullet", new SpawnData(getEntity().getCenter())
                .put("direction", direction())
                .put("owner", entity)
        );
    }

    private Point2D direction() {

        if (entity.getScaleX() == 1) {
            return new Point2D(1, 0);
        } else {
            return new Point2D(-1, 0);
        }
    }

}