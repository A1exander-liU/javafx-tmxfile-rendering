package com.example.tiledmaptest;

import javafx.animation.AnimationTimer;

/**
 * Defines the game loop.
 */
public abstract class GameLoop extends AnimationTimer {
    private float lastFrameInNanoSeconds;
    private boolean paused = true;

    /**
     * All game logic will be handled through this method via calling the
     * tick method.
     * @param now
     *            The timestamp of the current frame given in nanoseconds. This
     *            value will be the same for all {@code AnimationTimers} called
     *            during one frame.
     */
    @Override
    public void handle(final long now) {
        if (!paused) {
            float secondsSinceLastFrame = (float) ((now - lastFrameInNanoSeconds) / 1e9);
            lastFrameInNanoSeconds = now;
            tick(secondsSinceLastFrame);
        }
    }

    /**
     * Called within the handle method every frame. Game logic will be
     * provided through this.
     * @param secondsSinceLastFrame seconds since the last frame
     */
    public abstract void tick(float secondsSinceLastFrame);
    /**
     * Resumes the game loop.
     */
    @Override
    public void start() {
        if (paused) {
            super.start();
            paused = false;
        }
    }
    /**
     * Pauses the game loop.
     */
    @Override
    public void stop() {
        if (!paused) {
            super.stop();
            paused = true;
        }
    }
}
