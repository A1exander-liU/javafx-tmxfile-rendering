package com.example.tiledmaptest;

import com.example.tiledmaptest.starter.HelloApplication;

import java.util.Objects;

/**
 * Class used to get paths from the 'resources' directory.
 */
public final class ResourcePathMapper {
    private ResourcePathMapper() { }

    /**
     * Returns the path of the specified resource in the 'resources' directory.
     * @param resource the desired file or directory inside the 'resources directory'
     * @param relativePath true if the path is a relative path
     * @return a path to the resource
     */
    public static String getPathToResource(final String resource, final boolean relativePath) {
        if (relativePath) {
            return Objects.requireNonNull(HelloApplication.class.getResource("/" + resource)).getPath();
        } else {
            return resource;
        }
    }
}
