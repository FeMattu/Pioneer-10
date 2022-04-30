package com.pioneer10.model;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public abstract class Utils {

    /** directory for image*/
    public static final String ROOT = "./src/main/resources/";

    /**
     * Return the image object by given name of image
     * the image is get from the resource folder.
     * The image should be under directory {@see ROOT}
     *
     * @param fileName Name of the image
     * @return The image object of the given image
     */
    public static String getPathFileFromResources(String fileName) throws InvalidPathException {
        String p = Path.of(ROOT+fileName).toAbsolutePath().toString();
        return Path.of(ROOT+fileName).toAbsolutePath().toString();
    }


}
