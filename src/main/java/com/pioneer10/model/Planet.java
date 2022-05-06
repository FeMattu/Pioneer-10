package com.pioneer10.model;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Define the planets of solar System.
 * <p>
 * With:
 * <ul>
 *     <li>Mass of the planet;</li>
 *     <li>Radius of the planet;</li>
 *     <li>View of the planet, node that can be used to be visualized on a scene;</li>
 * </ul>
 * </p>
 */
public enum Planet {
    MERCURY (3.303e+23, 2.4397e6, mercury()),
    VENUS   (4.869e+24, 6.0518e6, venus()),
    EARTH   (5.976e+24, 6.37814e6, earth()),
    MARS    (6.421e+23, 3.3972e6, mars()),
    JUPITER (1.9e+27,   7.1492e7, jupiter()),
    SATURN  (5.688e+26, 6.0268e7, saturn()),
    URANUS  (8.686e+25, 2.5559e7, uranus()),
    NEPTUNE (1.024e+26, 2.4746e7, neptune()),
    PLUTO   (1.27e+22,  1.137e6, pluto());

    public static final int RAGGIO = 200;
    public static final int VELOCITY = 40;
    private final double mass;   //in kilograms
    private final double radius; //in meters
    private final Node planet;

    /**
     * Define the planet of the solar System
     *
     * @param mass Mass of the planet
     * @param radius Radius of the planet
     * @param planet View of the planet, node that can be added to be visualized
     */
    Planet(double mass, double radius, Node planet) {
        this.mass = mass;
        this.radius = radius;
        this.planet = planet;
    }

    /**
     * return the mass of planets
     *
     * @return mass of the planets
     */
    public double mass()   { return mass; }

    /**
     * return the radius of the planets
     *
     * @return radius of the planets
     */
    public double radius() { return radius; }

    public Node getNode() {return planet;}

    /**universal gravitational constant  (m3 kg-1 s-2)*/
    public static final double G = 6.67300E-11;

    /**
     * The surface gravity, g, of an astronomical object is the gravitational acceleration
     * experienced at its surface at the equator, including the effects of rotation.
     *
     * @return gravitational acceleration
     */
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    /**
     * Calucate the given mass on the correspondent planets
     * @param otherMass Mass
     * @return the mass of given mass on the planets
     */
    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }

    @NotNull
    private static Node mercury(){

        World mercury = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/mercury/mercurymap.jpg"),
                Utils.getPathFileFromResources("assets/planets/mercury/mercurybump.jpg"));
        mercury.animateRotation(VELOCITY, false);
        return mercury;
    }
    @NotNull
    private static Node venus(){
        World venus = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/venus/venusmap.jpg"),
                Utils.getPathFileFromResources("assets/planets/venus/venusbump.jpg"));
        venus.animateRotation(VELOCITY, false);

        return venus;
    }
    @NotNull
    private static Node earth(){
        World earth = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/earth/earth_map.jpg"),
                Utils.getPathFileFromResources("assets/planets/earth/earth_map_NRM.jpg"));
        earth.animateRotation(VELOCITY, false);

        return earth;
    }
    @NotNull
    private static Node mars(){
        World mars = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/mars/mars_map.jpg"),
                Utils.getPathFileFromResources("assets/planets/mars/mars_map_NRM.jpg"));
        mars.animateRotation(VELOCITY, false);

        return mars;
    }
    @NotNull
    private static Node jupiter(){
        World jupiter = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/jupiter/jupitermap.jpg"));
        jupiter.animateRotation(VELOCITY, false);

        return jupiter;
    }
    @NotNull
    private static Node saturn(){
        World saturn = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/saturn/saturnmap.jpg"));;
        saturn.animateRotation(VELOCITY, false);

        return saturn;
    }
    @NotNull
    private static Node uranus(){
        World uranus = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/uranus/uranusmap.jpg"));
        uranus.animateRotation(VELOCITY, false);

        return uranus;
    }
    @NotNull
    private static Node neptune(){
        World neptune = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/neptune/neptunemap.jpg"));
        neptune.animateRotation(VELOCITY, false);

        return neptune;
    }
    @NotNull
    private static Node pluto(){
        World pluto = new World(RAGGIO,
                Utils.getPathFileFromResources("assets/planets/pluto/plutomap.jpg"),
                Utils.getPathFileFromResources("assets/planets/pluto/plutobump.jpg"));
        pluto.animateRotation(VELOCITY, false);

        return pluto;
    }
}
