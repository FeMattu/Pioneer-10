package com.pioneer10.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.pioneer10.data.PlayerData;
import com.pioneer10.model.Utils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PioneerUIController implements com.almasb.fxgl.ui.UIController {

    private List<Node> lives, reloader;
    private int nrOfShoot, playerLives;

    public PioneerUIController() {
        lives = new ArrayList<>();
        playerLives = PlayerData.MAX_PLAYER_LIFE;

        nrOfShoot = PlayerData.MAX_BULLET_TO_SHOOT;
        reloader = new ArrayList<>();
    }

    @Override
    public void init() {
        for(int i = 0; i < playerLives; i++){
            Node heart = new ImageView(new Image(
                    Utils.getPathFileFromResources("assets/levels/cuore.png")));
            heart.setTranslateX(i*32);
            lives.add(heart);

            FXGL.addUINode(heart);
        }

        for(int i = 0; i < nrOfShoot; i++){
            Node bullet = new ImageView(
                    new Image(Utils.getPathFileFromResources("assets/levels/bulletReloader.png"))
            );
            bullet.setTranslateX(i*20);
            bullet.setTranslateY(32);
            reloader.add(bullet);

            FXGL.addUINode(bullet);
        }
    }

    public void removeLife(){
        if(playerLives > 0){
            playerLives--;
            FXGL.removeUINode(lives.get(playerLives));
        }
    }

    public void removeRecharge(){
        if(nrOfShoot > 0){
            nrOfShoot--;
            FXGL.removeUINode(reloader.get(nrOfShoot));
        }
    }

    public void addRecharge(){
        if(nrOfShoot < PlayerData.MAX_BULLET_TO_SHOOT){
            FXGL.addUINode(reloader.get(nrOfShoot));
            nrOfShoot++;
        }
    }

}
