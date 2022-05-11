package com.pioneer10.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.pioneer10.model.PioneerEntityType.PLAYER;

public class EnemyControlComponent extends Component {

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animDeath, animAttack;
    private PhysicsComponent physics;
    private LocalTimer timer;

    private int nrOfLife = 4; //questo scala ogni volta che viene colpito dal player
    private Entity player;

    public EnemyControlComponent() {

        animIdle = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_idle_sheet.png")),
                18, 864/18, 26,
                Duration.seconds(1), 0, 17);

        animWalk = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_walk_sheet.png")),
                20, 1120/20, 26,
                Duration.seconds(1), 0, 19);

        animAttack = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_attack_sheet.png")),
                20, 1120/20, 35,
                Duration.seconds(0.4), 0, 19);

        animDeath = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_death_sheet.png")),
                13, 936/13, 26,
                Duration.seconds(1.5), 0, 12);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
        timer = FXGL.newLocalTimer();
        timer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        player = FXGL.getGameWorld().getSingleton(PLAYER);

        if(entity.distance(player) < 200
                && entity.getY() < player.getY()+50
                && entity.getY() > player.getY()-50){
            if (entity.distance(player) > 40 || entity.distance(player) < 40){
                if(player.getX() > entity.getX()){
                    right();
                }else if( player.getX() < entity.getX()){
                    left();
                }
            }else{
                attack();
            }
        }else if(timer.elapsed(Duration.seconds(1)) ){
            if(physics.isMovingX() && physics.getVelocityX()>0){
                left();
            }else{ right(); }
            timer.capture();
        }

        if (physics.isMovingX() && texture.getAnimationChannel() != animAttack) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else{
            if (texture.getAnimationChannel() != animIdle && texture.getAnimationChannel() != animAttack) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void right() {
        physics.setVelocityX(70);
        getEntity().setScaleX(1);
    }

    public void left() {
        physics.setVelocityX(-70);
        getEntity().setScaleX(-1);
    }

    public void attack() {
        texture.loopAnimationChannel(animAttack);
    }

    public void death(){
        texture.loopAnimationChannel(animDeath);
    }

    public void hit(){
        if(nrOfLife < 0){
            death();
            return;
        }
        nrOfLife--;
    }

}
