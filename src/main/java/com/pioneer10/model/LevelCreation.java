package com.pioneer10.model;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.pioneer10.Component.EnemyControlComponent;
import com.pioneer10.Component.PlayerControlComponent;
import com.pioneer10.PioneerLauncher;
import com.pioneer10.view.LevelScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.pioneer10.data.PlayerData.MAX_PLAYER_LIFE;
import static com.pioneer10.model.PioneerEntityType.*;

public class LevelCreation extends GameApplication {

    private Entity player, lastPlatformTouched;
    private Viewport viewport;
    private int vite, coinsGrabbed, nrOfLevel, gravity;
    private List<Entity> cuori, reloader;
    private Text textForCoinGrabbed;
    private String level, levelMapPath, music;

    public LevelCreation(String level, int nrOfLevel, String levelMapPath, int gravity){
        this.level = level;
        this.levelMapPath = levelMapPath;
        this.nrOfLevel = nrOfLevel;
        this.gravity = gravity;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(640);
        gameSettings.setTitle(level);
        //to activate the developer menu to see bboxs
        //gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    public void initGame(){
        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap(levelMapPath);
        player = getGameWorld().getEntitiesByType(PLAYER).get(0);
        SpawnData backgroundData = new SpawnData();
        backgroundData.put("background", "assets/levels/Terra/background"+level);
        spawn("background", backgroundData);


        reloader = getGameWorld().getEntitiesByType(RELOADER);
        player.getComponent(PlayerControlComponent.class).addReloader(reloader);
        cuori = getGameWorld().getEntitiesByType(HEART);

        vite = MAX_PLAYER_LIFE;

        viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 180*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    public void addBackground(String backgroundPath){
        spawn("background");
    }

    /**
     * add music to level
     * @param assetName
     */
    public void addMusic(String assetName){
        music = assetName;
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        loopBGM(music);
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > getAppHeight()) {
            if(vite > 0){
                getGameWorld().removeEntity(player);
                player = spawn("player",
                        lastPlatformTouched.getX()+ lastPlatformTouched.getWidth()/2,
                        lastPlatformTouched.getY()-16);
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                getGameWorld().removeEntity(cuori.get(vite-1));
                player.getComponent(PlayerControlComponent.class).hit();
                vite = player.getComponent(HealthIntComponent.class).getValue();
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

        //bind dei cuori e dei proiettili
        for(int i = 0; i < cuori.size(); i++){
            cuori.get(i).xProperty().set(viewport.xProperty().doubleValue()+i*32);
        }
        reloader = getGameWorld().getEntitiesByType(RELOADER);
        for( int i = 0; i < reloader.size(); i++){
            reloader.get(i).xProperty().setValue(
                    viewport.xProperty().doubleValue()+i*20
            );
        }
        getGameWorld().getEntitiesByType(MONEY).get(0).xProperty().bind(viewport.xProperty());
        textForCoinGrabbed.setText(Integer.toString(coinsGrabbed));
    }
    @Override
    protected void initUI(){
        textForCoinGrabbed = new Text();
        textForCoinGrabbed.setFont(Font.font(30));
        textForCoinGrabbed.setFill(Color.WHITE);
        textForCoinGrabbed.setX(35);
        textForCoinGrabbed.setY(92);
        getGameScene().addUINode(textForCoinGrabbed);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(gravity/100, gravity);

        onCollisionBegin(PLAYER, PLATFORM, (player, platform)->{
            lastPlatformTouched = platform;
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
                    getGameWorld().removeEntity(cuori.get(vite-1));
                    player.getComponent(PlayerControlComponent.class).hit();
                    vite = player.getComponent(HealthIntComponent.class).getValue();

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
                player.getComponent(PlayerControlComponent.class).shoot();
            }
        }, KeyCode.E);

        //tasto per controllo delle bounding box
        /*
        getInput().addAction(new UserAction("DevPane") {
            @Override
            protected void onActionBegin() {
                if(!getDevService().isDevPaneOpen()){
                    getDevService().openDevPane();
                }else{
                    getDevService().closeDevPane();
                }
            }
        }, KeyCode.P, VirtualButton.LB);*/
    }
}
