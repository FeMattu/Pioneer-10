package com.pioneer10.Component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.pioneer10.model.Utils;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class CoinAnimationComponent extends Component {

    private AnimationChannel animation;
    private AnimatedTexture texture;

    public CoinAnimationComponent() {
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
