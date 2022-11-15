package com.example.tiledmaptest;

import javafx.scene.shape.Rectangle;

public final class RectangleObject extends Rectangle {
    public RectangleObject(final int x, final int y, final int width, final int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    public RectangleObject(final int width, final int height) {
        this(0, 0, width, height);
    }
}
