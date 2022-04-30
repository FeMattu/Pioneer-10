package com.pioneer10.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Utils {

    /** directory for image*/
    public static final String ROOT = "src/main/resources/";

    /**
     * Return the image object by given name of image
     * the image is get from the resource folder.
     * The image should be under directory {@see ROOT}
     *
     * @param fileName Name of the image
     * @return The image object of the given image
     */
    public static String getPathFileFromResources(String fileName) throws InvalidPathException {
        return Path.of(ROOT,fileName).toAbsolutePath().normalize().toString();
    }


}
