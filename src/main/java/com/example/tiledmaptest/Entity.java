package com.example.tiledmaptest;

import javafx.geometry.Point2D;

/**
 * A game entity that has a position and a size.
 */
public class Entity {
    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Constructs a new game entity with the specified size. Position will be set at origin,
     * @param width the desired width
     * @param height the desired height
     */
    public Entity(final int width, final int height) {
        this(0, 0, width, height);
    }
    /**
     * Constructs a new game entity with the specified position and size.
     * @param x the desired x position
     * @param y the desired y position
     * @param width the desired width
     * @param height the desired height
     */
    public Entity(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }
}
