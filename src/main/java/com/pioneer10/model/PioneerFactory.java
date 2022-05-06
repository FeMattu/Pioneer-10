package com.pioneer10.model;

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
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.pioneer10.model.PioneerEntityType.*;

public class PioneerFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .rotate(data.<Float>get("rotation"))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("platformTriangolo")
    public Entity newPlatformTriangolo(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.polygon(new Point2D(50, 50), new Point2D(60, 60), new Point2D(50, 60))))
                .viewWithBBox(String.valueOf(Color.RED))
                //.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                //.rotate(data.<Float>get("rotation"))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("angles")
    public Entity newAngles(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.polygon(data.<Double>get("points"))))
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
                .bbox(new HitBox(new Point2D(17,0), BoundingShape.box(7,17))) //box di collisione per le gambe
                .bbox(new HitBox(new Point2D(17,17), BoundingShape.box(9,9))) //box collisone per la testa
                .with(physics)
                .with(new EnemyControlComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        //physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(12, 0), BoundingShape.box(6, 5)));

        // this avoids player sticking to walls
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(new HitBox(BoundingShape.box(32,32)))//box di collisione per la testa
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new PlayerControlComponent())
                .build();
    }

    @Spawns("backgroundGiove")
    public Entity newBackgroundGiove(SpawnData data) {
        return entityBuilder()
                .view(new ScrollingBackgroundView(new Image(Utils.getPathFileFromResources("assets/levels/Giove/gioveBackground.jpg")),
                        getAppWidth(), getAppHeight()))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }
    @Spawns("backgroundMarte")
    public Entity newBackgroundMarte(SpawnData data) {
        return entityBuilder()
                .view(new ScrollingBackgroundView(new Image(Utils.getPathFileFromResources("assets/levels/Giove/gioveBackground.jpg")),
                        getAppWidth(), getAppHeight()))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .view(new ScrollingBackgroundView(new Image(Utils.getPathFileFromResources("assets/levels/Terra/backgroundTerra.jpg")),
                        getAppWidth(), getAppHeight()))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }
}
