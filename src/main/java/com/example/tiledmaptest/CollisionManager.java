package com.example.tiledmaptest;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.world.World;

import java.util.List;

/**
 * Manages a world instance where collisions will be simulated.
 */
public final class CollisionManager {
    public static final float PIXELS_TO_METERS = 16;
    private static CollisionManager collisionManager;
    private static World<Body> world;
    private CollisionManager() { }

    /**
     * Returns the instance of the world. The same world is returned every time.
     * @return the world
     */
    public static CollisionManager getInstance() {
        if (collisionManager == null) {
            collisionManager = new CollisionManager();
            world = new World<>();
            // since the game is top-down, there is no gravity
            world.setGravity(0, 0);
        }
        return collisionManager;
    }

    /**
     * Advances the world simulation by a single time step.
     * @param secondsSinceLastFrame the seconds elapsed since the last frame
     */
    public void updateWorld(final float secondsSinceLastFrame) {
        world.update(secondsSinceLastFrame);
    }
    public List<Body> getBodies() {
        return world.getBodies();
    }

    /**
     * Adds a movable rectangle to the world. Any rectangle that should be moved
     * in the application should use this method.
     * @param entity the rectangle to add to the world
     */
    public void addDynamicRectangle(final Entity entity) {
        Body body = new Body();
        final float width = entity.getWidth() / PIXELS_TO_METERS;
        final float height = entity.getHeight() / PIXELS_TO_METERS;
        body.addFixture(Geometry.createRectangle(width, height));
        body.setMass(MassType.NORMAL);
        entity.setBody(body);
        body.setUserData(entity);
        world.addBody(entity.getBody());
    }

    /**
     * Adds a fixed rectangle to the world. Any rectangle that should not move in
     * the application should use this method.
     * @param entity the rectangle to add to the world
     */
    public void addStaticRectangle(final Entity entity) {
        Body body = new Body();
        final float width = entity.getWidth() / PIXELS_TO_METERS;
        final float height = entity.getHeight() / PIXELS_TO_METERS;
        body.addFixture(Geometry.createRectangle(width, height));
        body.setMass(MassType.INFINITE);
        entity.setBody(body);
        body.setUserData(entity);
        world.addBody(entity.getBody());
    }
}
