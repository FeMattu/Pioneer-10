package com.pioneer10.model;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.pioneer10.Component.*;
import com.pioneer10.data.EnemyData;
import com.pioneer10.data.PlayerData;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.pioneer10.model.Configuration.BULLET_SPEED;
import static com.pioneer10.model.PioneerEntityType.*;

public class PioneerFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("angle")
    public Entity newAngle(SpawnData data) {

        List<Double> pp = data.<Polygon>get("polygon").getPoints().stream().toList();

        double [] points = new double[pp.size()];
        for(int i = 0; i < pp.size(); i++){
            points[i] = pp.get(i).doubleValue();
        }

        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.polygon(points)))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(ENEMY)
                .bbox(new HitBox(new Point2D(26,0), BoundingShape.box(8,24))) //box di collisione
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(EnemyData.MAX_ENEMY_LIFE))
                .with(new EnemyHealthViewComponent())
                .with(new EnemyControlComponent(data.get("stationary")))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(13, 17), BoundingShape.box(8, 14)));

        // this avoids player sticking to walls
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(new HitBox(new Point2D(13,17), BoundingShape.box(8,14)))//box di collisione per le gambe
                .bbox(new HitBox(new Point2D(7,7), BoundingShape.box(21,16)))//box di collisione per la testa
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(PlayerData.MAX_PLAYER_LIFE))
                .with(new ReloaderComponent(PlayerData.MAX_BULLET_TO_SHOOT))
                .with(new PlayerControlComponent())
                .build();
    }

    @Spawns("Bullet")
    public Entity newBullet(SpawnData data) {

        return entityBuilder(data)
                .at(data.getX(), data.getY())
                .type(BULLET)
                .viewWithBBox(new ImageView(
                        new Image(Utils.getPathFileFromResources("assets/Sprites/bullet.png"))
                ))
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent())
                .with(new ProjectileComponent(data.get("direction"), BULLET_SPEED))
                .build();
    }

    @Spawns("astronave")
    public Entity newAstronave(SpawnData data) {
        return entityBuilder(data)
                .type(ASTRONAVE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view(new ImageView(
                        new Image(Utils.getPathFileFromResources("assets/levels/astronave.png"))
                ))
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return entityBuilder(data)
                .type(COIN)
                .with( new CoinAnimationComponent())
                .bbox(BoundingShape.circle(15))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .view(new ScrollingBackgroundView(new Image(Utils.getPathFileFromResources(data.get("background"))),
                        getAppWidth(), getAppHeight()))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }


}
