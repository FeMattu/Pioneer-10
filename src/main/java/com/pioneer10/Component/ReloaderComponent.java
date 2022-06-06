package com.pioneer10.Component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.pioneer10.PioneerApp;
import com.pioneer10.data.PlayerData;
import com.pioneer10.model.LivTerra;
import com.pioneer10.model.Utils;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class ReloaderComponent extends Component {

    private ArrayList<Node> reloader;
    private int nrOfShoot;
    private LocalTimer reloaderTimer, shootTimer;

    public ReloaderComponent(int nrOfShoot){
        this.nrOfShoot = nrOfShoot;
    }

    @Override
    public void onAdded() {
        reloaderTimer = FXGL.newLocalTimer();
        reloaderTimer.capture();
        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        //TODO: correzione tempo di ricarica
        if(nrOfShoot < PlayerData.MAX_BULLET_TO_SHOOT){
            if(reloaderTimer.elapsed(Duration.seconds(1.5))){
                PioneerApp.controller.addRecharge();
                nrOfShoot++;
                reloaderTimer.capture();
            }
        }
    }

    public void shoot() {
        if(!shootTimer.elapsed(Duration.millis(200)))
            return;

        if(nrOfShoot > 0){
            spawn("Bullet", new SpawnData(getEntity().getCenter())
                    .put("direction", direction())
                    .put("owner", entity)
            );
            PioneerApp.controller.removeRecharge();
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
}
