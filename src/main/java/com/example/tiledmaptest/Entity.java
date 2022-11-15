package com.example.tiledmaptest;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

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
    public Entity(final float width, final float height) {
        this(0, 0, width, height);
    }
    /**
     * Constructs a new game entity with the specified position and size.
     * @param x the desired x position
     * @param y the desired y position
     * @param width the desired width
     * @param height the desired height
     */
    public Entity(final float x, final float y, final float width, final float height) {
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

    public void setWidth(final float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }
    public Rectangle getEntityRectangle() {
        return new Rectangle(x, y, width, height);
    }
}
