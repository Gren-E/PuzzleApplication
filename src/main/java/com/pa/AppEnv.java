package com.pa;

import java.io.File;

public class AppEnv {

    private static final String resourceDirectory = "C:\\Workspace\\Java\\PuzzleApplication\\src\\main\\resources";

    public static File getResourceDirectory() {
        return new File(resourceDirectory);
    }

    public static File getImageResourceDirectory() {
        return new File(getResourceDirectory(), "image");
    }

    public static File getPictureCatalogDirectory() {
        return new File(getImageResourceDirectory(), "pictureCatalog");
    }

}
