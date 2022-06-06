package com.pioneer10.model;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Utils {

    /**directory for image*/
    public static final String ROOT = "src/main/resources/";

    /**
     * Return the path string of given file
     * the path is absolute; the file should be
     * under {@see ROOT}
     *
     * @param fileName Name of the file
     * @return the absolute path of the file
     */
    public static String getPathFileFromResources(String fileName) throws InvalidPathException {
        return Path.of(ROOT,fileName).toAbsolutePath().normalize().toString();
    }

    public static Blend neonEffect(){
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(66, 119, 254, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.rgb(0, 121, 141, 1));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.rgb(255, 255, 255, 1));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.rgb(255, 255, 255, 1));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        return blend;
    }
}
