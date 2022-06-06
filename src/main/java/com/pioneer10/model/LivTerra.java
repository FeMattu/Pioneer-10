package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.pioneer10.Component.EnemyControlComponent;
import com.pioneer10.Component.PlayerControlComponent;
import com.pioneer10.Component.ReloaderComponent;
import com.pioneer10.PioneerLauncher;
import com.pioneer10.controller.PioneerUIController;
import com.pioneer10.data.PlayerData;
import com.pioneer10.view.LevelScene;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pioneer10.model.PioneerEntityType.*;

public class LivTerra extends GameApplication {

    private final int MAX_VITE = 3;
    private Entity player;
    private Viewport viewport;
    private int coinsGrabbed, vite;
    private Text textForCoinGrabbed;
    private Entity lastPlatformTouchedByPlayer;
    public static PioneerUIController controller;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(640);
        gameSettings.setTitle("Pioneer-10\nTerra");
        gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Terra/MappaTerra.tmx");
        player = getGameWorld().getSingleton(PLAYER);
        spawn("backgroundTerra");

        vite = MAX_VITE;

        viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 180*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }
    @Override
    protected void initUI(){
        controller = new PioneerUIController();
        controller.init();

        Node moneyIcon = new ImageView(
                new Image(Utils.getPathFileFromResources("assets/levels/money.png")));
        moneyIcon.setTranslateY(64);
        FXGL.addUINode(moneyIcon);

        textForCoinGrabbed = new Text();
        textForCoinGrabbed.setFont(Font.font(30));
        textForCoinGrabbed.setFill(Color.WHITE);
        textForCoinGrabbed.setX(35);
        textForCoinGrabbed.setY(92);
        getGameScene().addUINode(textForCoinGrabbed);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", "MappaTerra.tmx");
        vars.put("lives", PlayerData.MAX_PLAYER_LIFE);
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > getAppHeight()) {
            if(vite > 0){
                player.removeFromWorld();
                player = spawn("player",
                        lastPlatformTouchedByPlayer.getX()+ lastPlatformTouchedByPlayer.getWidth()/2,
                        lastPlatformTouchedByPlayer.getY()-32);
                player.getComponent(HealthIntComponent.class).damage(1);
                inc("lives", -1);
                controller.removeLife();
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                //vite--;
            }else{
                getDialogService().showMessageBox("You are dead", () ->{
                    FXGL.getPrimaryStage().setScene(new LevelScene(
                            FXGL.getPrimaryStage(),
                            PioneerLauncher.WIDTH,
                            PioneerLauncher.HEIGHT
                    ));
                });
            }
        }
        //bind con la viewport del numero di monete
        textForCoinGrabbed.setText(Integer.toString(coinsGrabbed));
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.5);
        loopBGM("Minecraft.mp3");
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 500);

        onCollisionBegin(PLAYER, PLATFORM, (player, platform)->{
           lastPlatformTouchedByPlayer = platform;
        });

        onCollisionOneTimeOnly(PLAYER, COIN, (player, coin) -> {
            getGameWorld().removeEntity(coin);
            coinsGrabbed++;
        });

        onCollisionBegin(BULLET, PLATFORM, (bullet, platform) -> {
            bullet.removeFromWorld();
        });

        onCollisionOneTimeOnly(ASTRONAVE, PLAYER, (astronave, player) -> {
            getDialogService().showMessageBox("Level finish", () ->{
                FXGL.getPrimaryStage().setScene(new LevelScene(
                        FXGL.getPrimaryStage(),
                        PioneerLauncher.WIDTH,
                        PioneerLauncher.HEIGHT
                ));
            });
        });

        onCollisionBegin(BULLET, ENEMY, (bullet, enemy) ->{
            if(enemy.getComponent(HealthIntComponent.class).getValue() != 0){
                enemy.getComponent(EnemyControlComponent.class).hit();
                bullet.removeFromWorld();
            }
        });

        onCollisionBegin(PLAYER, ENEMY, (player, enemy) -> {
            if(vite>0){
                if(enemy.getComponent(HealthIntComponent.class).getValue() != 0){
                    vite--;
                    enemy.getComponent(EnemyControlComponent.class).hit();
                }
            }else{
                getDialogService().showMessageBox("You are dead", () ->{
                    FXGL.getPrimaryStage().setScene(new LevelScene(
                            FXGL.getPrimaryStage(),
                            PioneerLauncher.WIDTH,
                            PioneerLauncher.HEIGHT
                    ));
                });
            }
        });
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControlComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerControlComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerControlComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerControlComponent.class).jump();
            }
        }, KeyCode.SPACE, VirtualButton.A);

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                player.getComponent(ReloaderComponent.class).shoot();
            }
        }, KeyCode.E);

        //tasto per controllo delle bounding box

        getInput().addAction(new UserAction("DevPane") {
            @Override
            protected void onActionBegin() {
                if(!getDevService().isDevPaneOpen()){
                    getDevService().openDevPane();
                }else{
                    getDevService().closeDevPane();
                }
            }
        }, KeyCode.P, VirtualButton.LB);
    }

    public static void main(String[] args) {launch(args);}
}
