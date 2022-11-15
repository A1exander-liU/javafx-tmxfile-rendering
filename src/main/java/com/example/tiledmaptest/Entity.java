package com.example.tiledmaptest;

import javafx.geometry.Point2D;

/**
 * A game entity that has a position and a size.
 */
public class Entity {
    private Point2D position;
    private int width;
    private int height;

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
        position = new Point2D(x, y);
        this.width = width;
        this.height = height;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(final Point2D position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }
}
