package com.pioneer10.model;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class CoinAnimation extends Component {

    private AnimationChannel animation;
    private AnimatedTexture texture;

    public CoinAnimation() {
        animation = new AnimationChannel(
                new Image(Utils.getPathFileFromResources("assets/levels/coin-spritesheet.png")),
                9, 32, 30, Duration.seconds(1), 0, 8);

        texture = new AnimatedTexture(animation);
    }

    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animation);
    }
}
