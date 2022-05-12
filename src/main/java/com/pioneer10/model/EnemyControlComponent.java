package com.pioneer10.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static com.pioneer10.model.PioneerEntityType.PLAYER;

public class EnemyControlComponent extends Component {

    private final double DEATH_TIME = 1.5;
    private final ProgressBar healtBar;
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

        animAttack = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_attack_sheet.png")),
                20, 856/20, 26,
                Duration.seconds(0.4), 0, 19);

        animDeath = new AnimationChannel(new Image(Utils.getPathFileFromResources("assets/Sprites/undead_death_sheet.png")),
                13, 936/13, 26,
                Duration.seconds(DEATH_TIME), 0, 12);

        healtBar = HealtBar();
        FXGL.addUINode(healtBar);
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
        healtBar.setMaxValue(hp.getMaxValue());
        healtBar.setCurrentValue(hp.getValue());
    }

    @Override
    public void onUpdate(double tpf) {
        player = FXGL.getGameWorld().getSingleton(PLAYER);
        physics.applyBodyForce(Vec2.fromAngle(90), Vec2.fromAngle(90));
        healtBar.setTranslateX(entity.getX() - entity.getWidth()/2);
        healtBar.setTranslateY(entity.getY()-entity.getHeight()/2-5);

        if(stationary){ //nemici statici

            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(new FixtureDef().friction(0.9f));

            if(entity.distance(player) < 50
                    && entity.getY() < player.getY()+50     //aggiunta di margine di errore per le Y
                    && entity.getY() > player.getY()-50){
                if(player.getX() > entity.getX()){
                    getEntity().setScaleX(1);
                }else if( player.getX() < entity.getX()){
                    getEntity().setScaleX(-1);
                }
                attack();
            }

        }else{ //no-stationary enemy
            if(entity.distance(player) < 200
                    && entity.getY() < player.getY()+50     //aggiunta di margine di errore per le Y
                    && entity.getY() > player.getY()-50){
                if (entity.distance(player) > 50){
                    if(player.getX() > entity.getX()){
                        right();
                    }else if( player.getX() < entity.getX()){
                        left();
                    }
                }else{
                    attack();
                }
            }else if(timer.elapsed(Duration.seconds(1)) ){
                if(physics.getVelocityX()>0){
                    left();
                }else{ right(); }
                timer.capture();
            }
        }

        if(!hp.isZero()){
            if (physics.isMovingX()) {
                if (texture.getAnimationChannel() != animWalk) {
                    texture.loopAnimationChannel(animWalk);
                }
            } else if (!physics.isMovingX()){
                if(texture.getAnimationChannel() == animAttack){
                    texture.loopAnimationChannel(animAttack);
                }else {
                    texture.loopAnimationChannel(animIdle);
                }
            }
        }else{
            if(texture.getAnimationChannel() != animDeath){
                texture.loopAnimationChannel(animDeath);
            }

            timer.capture();
            if(deathTimer.elapsed(Duration.seconds(DEATH_TIME))){
                entity.removeFromWorld();
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
        stop();
        texture.loopAnimationChannel(animDeath);
    }

    public void hit(){
        hp.damage(1);
        healtBar.setCurrentValue(hp.getValue());

        if(hp.isZero()){
            if(!deathTimer.elapsed(Duration.seconds(20))){
                death();
            }
            timer.capture();
        }
    }

    @NotNull
    private static ProgressBar HealtBar() {
        ProgressBar bar = new ProgressBar(true);
        bar.getInnerBar().arcWidthProperty().unbind();
        bar.getInnerBar().arcHeightProperty().unbind();
        bar.getInnerBar().arcWidthProperty().setValue(0);
        bar.getInnerBar().arcHeightProperty().setValue(0);
        bar.getInnerBar().heightProperty().unbind();
        bar.getBackgroundBar().setFill(null);
        bar.getBackgroundBar().setEffect(null);
        bar.getInnerBar().setEffect(null);

        bar.setWidth(50);
        bar.setFill(Color.AQUAMARINE);
        return bar;
    }

}
