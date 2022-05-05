package com.pioneer10.model;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.pioneer10.model.PioneerEntityType.*;

//@SetEntityFactory
public class MArioAFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
       return entityBuilder(data)
               .type(PLATFORM)
               .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
               .with(new PhysicsComponent())
               .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return entityBuilder(data)
                .type(PLAYER)
                .viewWithBBox(new Rectangle(5,5, Color.RED))
                //.bbox(new HitBox(new Point2D(5,5), BoundingShape.circle(60)))
                //.bbox(new HitBox(new Point2D(10,25), BoundingShape.box(60, 17)))
                .with(physicsComponent)
                .with(new PlayerControl())
                .build();
    }

   @Spawns("coin")
    public Entity newCoin(SpawnData data){
        return entityBuilder(data)
                .type(COIN)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                //.viewWithBBox(new Circle(data.<Integer>get("width"), Color.GOLD))
                .build();
   }

}
