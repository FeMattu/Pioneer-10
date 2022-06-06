package com.pioneer10.Component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.pioneer10.PioneerApp;
import com.pioneer10.model.Utils;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.pioneer10.model.PioneerEntityType.PLAYER;

public class EnemyControlComponent extends Component {

    private HealthIntComponent hp;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animDeath, animAttack;
    private PhysicsComponent physics;
    private LocalTimer timer, deathTimer;

    private Entity player;
    private boolean stationary;

    public EnemyControlComponent(boolean stationary) {
        this.stationary = stationary;

        animIdle = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_idle_sheet.png")),
                18, 864/18, 26,
                Duration.seconds(1), 0, 17);

        animWalk = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_walk_sheet.png")),
                20, 1120/20, 26,
                Duration.seconds(1), 0, 19);

        animAttack = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_attack_sheet1.png")),
                20, 1120/20, 36,
                Duration.seconds(0.6), 0, 19);

        animDeath = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_death_sheet.png")),
                13, 936/13, 26,
                Duration.seconds(2.3), 0, 12);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
        timer = FXGL.newLocalTimer();
        timer.capture();

        deathTimer = FXGL.newLocalTimer();


        hp = entity.getComponent(HealthIntComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        player = FXGL.getGameWorld().getSingleton(PLAYER);

        if(entity.getY() > FXGL.getAppHeight()){
            entity.removeFromWorld();
        }

        if(!hp.isZero()){
            if (physics.isMovingX()) {
                if (texture.getAnimationChannel() != animWalk) {
                    texture.loopAnimationChannel(animWalk);
                }
            }else{
                if(texture.getAnimationChannel() == animAttack){
                    texture.setOnCycleFinished(()->{
                        texture.loopAnimationChannel(animIdle);
                        FXGL.getGameWorld().getSingleton(PLAYER)
                                .getComponent(HealthIntComponent.class).damage(1);
                        PioneerApp.controller.removeLife();
                    });
                    return;
                } else{
                    texture.loopAnimationChannel(animIdle);
                }
            }
        }else{
            if(texture.getAnimationChannel() == animDeath){
                stop();
                return;
            }
        }

        if(stationary){ //nemici statici
            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(new FixtureDef().friction(0.9f));

            //il nemico si gira verso il player
            if(entity.distance(player) < 40
                    && entity.getY() < player.getY()+50     //aggiunta di margine di errore per le Y
                    && entity.getY() > player.getY()-50){
                if(player.getX() > entity.getX()){
                    getEntity().setScaleX(1);
                }else if( player.getX() < entity.getX()){
                    getEntity().setScaleX(-1);
                }
                attack();
                return;
            }
        }else{ //no-stationary enemy
            if(entity.distance(player) < 200
                    && entity.getY() < player.getY()+50     //aggiunta di margine di errore per le Y
                    && entity.getY() > player.getY()-50){
                if (entity.distance(player) > 40){
                    if(player.getX() > entity.getX()){
                        right();
                    }else if( player.getX() < entity.getX()){
                        left();
                    }
                }else{
                    attack();
                    return;
                }
            }else if(timer.elapsed(Duration.seconds(1)) ){
                if(physics.getVelocityX()>0){
                    left();
                }else{ right(); }
                timer.capture();
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

    //TODO: correzione della bounding box per l'attacco
    public void attack() {
        stop();
        TransformComponent t = new TransformComponent();
        t.scaleYProperty().setValue(2);
        entity.getBoundingBoxComponent().setTransform(t);
        texture.loopAnimationChannel(animAttack);
    }

    public void death(){
        texture.loopAnimationChannel(animDeath);
    }

    public void hit(){
        hp.damage(1);

        if(hp.isZero()){
            if(texture.getAnimationChannel() != animDeath){
                death();
            }
           texture.setOnCycleFinished(()->{
               entity.removeFromWorld();
           });
        }
    }



}
