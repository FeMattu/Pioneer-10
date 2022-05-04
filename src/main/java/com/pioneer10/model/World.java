package com.pioneer10.model;

import com.pioneer10.model.exception.InvalidAxisException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class World extends Sphere{

    private PhongMaterial textureMondo; //texture da applicare alla sfera

    /**
     * Create a sphere representing a world, with given texture
     *
     * @param raggio Grandezza del world, raggio della sfera
     * @param texturePath Path della texture da applicare al world;
     *               il path deve essere assoluto
     */
    public World(double raggio, String texturePath){
        this.setRadius(raggio);

        //creo la texture da applicare alla sfera
        textureMondo = new PhongMaterial();
        textureMondo.setDiffuseMap(new Image(texturePath));
        //applico la texture
        this.setMaterial(textureMondo);

        //attivo il depth buffer
        this.setDepthTest(DepthTest.ENABLE);

        //punto di rotazione predefinito per il mondo
        this.setRotationAxis(new Point3D(0.0,1,0.0)); //punto di rotazione, asse Y
    }

    /**
     * Create a sphere representing a world, with given texture and bumbpmap to have a better 3D effect
     *
     * @param raggio Grandezza del world, raggio della sfera
     * @param texturePath Path della texture da applicare al world;
     *               il path deve essere assoluto
     * @param bumpMapPath Path della normal map della texture per effetto bump;
     *                    il path deve essere assoluto
     */
    public World(double raggio, String texturePath, String bumpMapPath){
        this(raggio, texturePath);
        textureMondo.setBumpMap(new Image(bumpMapPath));
    }

    /**
     * Create a sphere representing a world,
     * with given texture and bumbpmap to have a better 3D effect; selfillumination map,
     * represent points of auto illumination of the world, like the lights visible on the night
     * from the earth
     *
     * @param raggio Grandezza del world, raggio della sfera
     * @param texturePath Path della texture da applicare al world;
     *               il path deve essere assoluto
     * @param bumpMapPath Path della normal map della texture per effetto bump;
     *                    il path deve essere assoluto
     * @param selfIlluminationMapPath Path della mappa per effetto di auto illuminazione;
     *                                il path deve essere assoluto
     */
    public World(double raggio, String texturePath, String bumpMapPath, String selfIlluminationMapPath){
        this(raggio, texturePath, bumpMapPath);
        textureMondo.setSelfIlluminationMap(new Image(selfIlluminationMapPath));
    }

    /**
     * Create a sphere representing a world,
     * with given texture and bumbpmap to have a better 3D effect; selfillumination map,
     * represent points of auto illumination of the world, like the lights visible on the night
     * from the earth; specularmap, points specifying the amount of light reflected.
     *
     * @param raggio Grandezza del world, raggio della sfera
     * @param texturePath Path della texture da applicare al world;
     *               il path deve essere assoluto
     * @param bumpMapPath Path della normal map della texture per effetto bump;
     *                    il path deve essere assoluto
     * @param selfIlluminationMapPath Path della mappa per effetto di auto illuminazione;
     *                                il path deve essere assoluto
     * @param specularMapPath Path della mappa spucular, indica quali quinti devono riflettere piu o meno luce;
     *                        il path deve essere assoluto
     */
    public World(double raggio, String texturePath, String bumpMapPath, String selfIlluminationMapPath, String specularMapPath){
        this(raggio, texturePath, bumpMapPath, selfIlluminationMapPath);
        textureMondo.setSelfIlluminationMap(new Image(specularMapPath));
    }

    /**
     * posizione l'oggetto sull'asse X
     * @param x Coordinate X (Ascissa) a cui posizionare l'oggetto
     */
    public void setX(double x){
        this.setTranslateX(x);
    }
    public double getX(){ return this.getTranslateX();}

    /**
     * posizione l'oggetto sull'asse Y
     * @param y Coordinate Y (Ordinate) a cui posizionare l'oggetto
     */
    public void setY(double y){
        this.setTranslateY(y);
    }
    public double getY(){ return this.getTranslateY();}

    /**
     * posizione l'oggetto sull'asse Z
     * @param z Coordinate Z (Profondità) a cui posizionare l'oggetto
     */
    public void setZ(double z){
        this.setTranslateZ(z);
    }
    public double getZ() {return this.getTranslateZ();}

    /**
     * setta la texture del world
     *
     * @param pathTexture Path assoluto della texture da applicare
     */
    public void setTexture(String pathTexture){
        textureMondo.setDiffuseMap(new Image(pathTexture));
        this.setMaterial(textureMondo);
    }

    /**
     * setta l'asse di rotazione del mondo
     *
     * @param axis asse di rotazione [ X, Y, Z ]
     */
    public void setRotationAxis(char axis) throws InvalidAxisException {
        switch(axis){
            case 'X':
            case 'x':
                this.setRotationAxis(new Point3D(1.0,0.0,0.0));
                break;
            case 'Y':
            case 'y':
                this.setRotationAxis(new Point3D(0.0,1.0,0.0));
                break;
            case 'Z':
            case 'z':
                this.setRotationAxis(new Point3D(0.0,0.0,1.0));
                break;
            default: throw new InvalidAxisException(axis);
        }
    }

    /**
     * Animate the rotation of the world, this rotation is definited by the rotation axis,
     * this as default is on Y axis and rotation goes from right to left, to invert is sufficient
     * set <strong>invert</strong> parameter to <strong>true</strong>;
     *
     * @param velocity velocity of rotation, as its high as animation is slower
     * @param invert Invert the rotation of the world
     */
    public void animateRotation(int velocity, boolean invert){
        Timeline t;
        if(!invert){
            //animation of the world from right to left
            t = new Timeline(
                    new KeyFrame(Duration.seconds(0), new KeyValue(this.rotateProperty(), 0)),
                    new KeyFrame(Duration.seconds(velocity), new KeyValue(this.rotateProperty(), 360))
            );
        }else{
            //animation of the world from right to left
            t = new Timeline(
                    new KeyFrame(Duration.seconds(0), new KeyValue(this.rotateProperty(), 360)),
                    new KeyFrame(Duration.seconds(velocity), new KeyValue(this.rotateProperty(), 0))
            );
        }
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

}
