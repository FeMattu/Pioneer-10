package com.pioneer10.model;

public record Configuration() {

    public static final int NR_OF_LEVEL = 3;
    public static final int BULLET_SPEED = 32*10;
    public static int nrOfCurrentLevel = 1;
    public static String currentLevel = "Earth";

    public static final String [] LEVELS = {
            "Mercury",
            "Venus",
            "Earth",
            "Mars",
            "Jupiter",
            "Saturn",
            "Uranus",
            "Neptune"
    };

}
