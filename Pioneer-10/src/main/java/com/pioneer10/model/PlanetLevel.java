package com.pioneer10.model;

import javafx.scene.image.Image;

public class PlanetLevel {

    //constants
    public final float PREF_PLAYER_WEIGHT = 20; //express in kilos
    public final float PREF_ENEMY_WEIGHT = 20; //express in kilos

    private Planet planet;
    private String nameOfLevel;
    private int nrLevel;
    private String pathOfTheMapLevel;
    private Image background;
    private float weightOfPlayer = PREF_PLAYER_WEIGHT;
    private float weightOfEnemies = PREF_ENEMY_WEIGHT;

    /**
     * Create a level with given map.
     * The background of the level as default is white.
     *
     * @param nameOfLevel Name of the level
     * @param planet Planet of the level, this is important to specify the gravitational
     *               acceleration of the planet that determinate the physics part of the level;
     *               important for the physics simulation of the level.
     * @param nrLevel Number of the level
     * @param pathOfTheMapLevel Path where is the .tmx file for the map of the level
     */
    public PlanetLevel(String nameOfLevel, Planet planet, int nrLevel, String pathOfTheMapLevel){
        this.planet = planet;
        this.nameOfLevel = nameOfLevel;
        this.nrLevel = nrLevel;
        this.pathOfTheMapLevel = pathOfTheMapLevel;
    }

    /**
     * Create a level with given map.
     *
     * @param nameOfLevel Name of the level
     * @param planet Planet of the level, this is important to specify the gravitational
     *               acceleration of the planet that determinate the physics part of the level;
     *               important for the physics simulation of the level.
     * @param nrLevel Number of the level
     * @param pathOfTheMapLevel Path where is the .tmx file for the map of the level
     * @param background Background of map, this as default is white
     */
    public PlanetLevel(String nameOfLevel, Planet planet, int nrLevel, String pathOfTheMapLevel, Image background){
        this.planet = planet;
        this.nameOfLevel = nameOfLevel;
        this.nrLevel = nrLevel;
        this.pathOfTheMapLevel = pathOfTheMapLevel;
        this.background = background;
    }

    /**
     * Set the weight of the player, this as default is {@see PREF_PLAYER_WEIGHT}.
     * This component is important to set the physics simulation of the jump and fall of
     * player.
     *
     * @param weight Weight of the player
     */
    public void setPlayerWeight(float weight){
        this.weightOfPlayer = weight;
    }

    /**
     * Set the weight of the enemies, this as default is {@see PREF_ENEMY_WEIGHT}.
     * This component is important to set the physics simulation of the fall of
     * enemies.
     *
     * @param weight Weight of the enemies
     */
    public void setEnemyWeight(float weight){
        this.weightOfEnemies = weight;
    }




}
