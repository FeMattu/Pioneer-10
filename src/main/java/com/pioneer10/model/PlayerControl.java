package com.pioneer10.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerControl extends Component {

    private PhysicsComponent physics;

    private int jumps = 2;



    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(170);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        //if (jumps == 0)
        // return;

        physics.setVelocityY(-300);

        jumps--;
    }



}

