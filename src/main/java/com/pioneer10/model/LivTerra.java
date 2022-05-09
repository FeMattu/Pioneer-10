package com.pioneer10.model;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pioneer10.model.PioneerEntityType.*;
import static com.pioneer10.model.Planet.EARTH;

public class LivTerra extends GameApplication {

    private final int MAX_VITE = 3;
    private Entity player;
    private Viewport viewport;
    private int vite, coinsGrabbed;
    private List<Entity> cuori;
    private Text textForCoinGrabbed;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(640);
        gameSettings.setTitle("Pioneer-10\nTerra");
    }

    @Override
    public void initGame(){

        getGameWorld().addEntityFactory(new PioneerFactory());
        setLevelFromMap("Terra/MappaTerra.tmx");
        player = getGameWorld().getEntitiesByType(PLAYER).get(0);
        spawn("backgroundTerra");
        cuori = getGameWorld().getEntitiesByType(HEART);

        vite = MAX_VITE;

        viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 150*32, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        loopBGM("Minecraft.mp3");
    }

    @Override
    protected void initUI(){
        textForCoinGrabbed = new Text();
        textForCoinGrabbed.setFont(Font.font(30));
        textForCoinGrabbed.setFill(Color.WHITE);
        textForCoinGrabbed.setX(34);
        textForCoinGrabbed.setY(61);
        getGameScene().addUINode(textForCoinGrabbed);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 400);

        //Collisione con monete
        onCollisionOneTimeOnly(PLAYER, COIN, (player, coin) -> {
            getGameWorld().removeEntity(coin);
            coinsGrabbed++;
        });

        onCollisionOneTimeOnly(PLAYER, ENEMY, (player, enemy) -> {
            if(vite<0){
                getDialogService().showMessageBox("You are died", () ->{
                });
            }
            getGameWorld().removeEntity(cuori.get(vite-1));
            vite--;
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > getAppHeight()) {
            if(vite > 0){
                Double posX = player.getX();
                Double posY = Double.valueOf(getAppHeight()/2);
                getGameWorld().removeEntity(player);
                player = spawn("player", posX, posY-100);
                viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
                getGameWorld().removeEntity(cuori.get(vite-1));
                vite--;
            }else{
                getDialogService().showMessageBox("You are died", () ->{
                    //codice per tornare al menu dei livelli
                });
            }
        }

        //bind dei cuori
        for(int i = 0; i < cuori.size(); i++){
            cuori.get(i).xProperty().set(viewport.xProperty().doubleValue()+i*32);
        }
        getGameWorld().getEntitiesByType(MONEY).get(0).xProperty().bind(viewport.xProperty());
        textForCoinGrabbed.setText(Integer.toString(coinsGrabbed));
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
    }

    public static void main(String[] args){launch(args);}
}
