package com.pioneer10.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.pioneer10.model.PioneerEntityType.RELOADER;

public class PlayerControlComponent extends Component {

    private List<Entity> reloader;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animJump;
    private PhysicsComponent physics;
    private LocalTimer shootTimer;
    private final int MAX_NR_OF_SHOOT;
    private int nrOfShoot, jumps;



    public PlayerControlComponent(int nrOfLife, int nrOfShootInLoader) {
        this.MAX_NR_OF_SHOOT = nrOfShoot = nrOfShootInLoader;

        jumps  = Configuration.MAX_JUMPS;

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
        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps  = Configuration.MAX_JUMPS;
            }
        });
        entity.addComponent(new HealthIntComponent(3));


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

        if(nrOfShoot < MAX_NR_OF_SHOOT){
            if(shootTimer.elapsed(Duration.seconds(3))){
                spawn("reloader",
                        reloader.get(nrOfShoot).getX(),
                        reloader.get(nrOfShoot).getY());
                nrOfShoot++;
                shootTimer.capture();
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
        if (jumps == 0)
            return;

        physics.setVelocityY(-200);

        jumps--;
    }

    public void addReloader(List<Entity> reloader){
        this.reloader = List.copyOf(reloader);
    }

    public void shoot() {
        if(shootTimer.elapsed(Duration.seconds(1)))
            return;

        if(nrOfShoot > 0){
            spawn("Bullet", new SpawnData(getEntity().getCenter())
                    .put("direction", direction())
                    .put("owner", entity)
            );
            reloader.get(nrOfShoot-1);
            FXGL.getGameWorld().removeEntity(
                    FXGL.getGameWorld().getEntitiesByType(RELOADER).get(nrOfShoot-1));
            nrOfShoot--;
        }

        shootTimer.capture();
    }

    private Point2D direction() {
        if (entity.getScaleX() == 1) {
            return new Point2D(1, 0);
        } else {
            return new Point2D(-1, 0);
        }
    }

    public int getNrOfShoot(){
        return nrOfShoot;
    }

    public void hit(){entity.getComponent(HealthIntComponent.class).damage(1);}
    public void recoverLife(){
        entity.getComponent(HealthIntComponent.class).damage(-1);
    }

    public int getLife(){
        return entity.getComponent(HealthIntComponent.class).getValue();
    }

    public int getMaxLife(){
        return entity.getComponent(HealthIntComponent.class).getMaxValue();
    }


}