package com.example.tiledmaptest;
import com.example.tiledmaptest.tests.RealTest;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.BodyType;
import org.tiledreader.TiledReader;
import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledLayer;
import org.tiledreader.TiledObjectLayer;
import org.tiledreader.TiledObject;
public final class WorldManager {
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 8;
    private static final int POSITION_ITERATION = 3;
    private static boolean BUILT_OBSTACLES = false;
    private static WorldManager worldManager;
    private static World world;
    private static BodyDef bodyDef;
    private static FixtureDef fixtureDef;
    private WorldManager() { }
    /**
     * Returns the instance of the world. Subsequent calls will refer to the same world.
     * @return the world
     */
    public static WorldManager getInstance() {
        if (worldManager == null) {
            worldManager = new WorldManager();
            world = new World(new Vec2(0, 0));
            bodyDef = new BodyDef();
            fixtureDef = new FixtureDef();
        }
        return worldManager;
    }
    /**
     * Advances the physics simulation by a single time step that is defined as
     * 1 / 60f, basically a 60th of a second/60FPS.
     */
    public void updateWorld() {
        // obstacles from tiled map are on;y ever built once when application opens
        if (!BUILT_OBSTACLES) {
            createObstacles();
            BUILT_OBSTACLES = true;
        }
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATION);
        Body firstBody = world.getBodyList();
        // the body list is a linked list
        while (firstBody != null) {
            Entity entity = (Entity) firstBody.getUserData();
            if (entity != null) {
                // update the position based on the position in the body
                /*
                jBox2D has the x and y at the center, need to subtract half the width and height
                to get the position of the top left corner (the x and y for javafx).
                 */
                entity.setX(firstBody.getPosition().x - entity.getWidth() / 2f);
                entity.setY(firstBody.getPosition().y - entity.getHeight() / 2f);
            }
            firstBody = firstBody.getNext();
        }
    }
    /**
     * Gets the list of the bodies in the world.
     * @return the list of bodies
     */
    public Body getBodies() {
        return world.getBodyList();
    }
    /**
     * Creates a new body for the specified game entity for the physics simulation. User data is
     * associated with these bodies. Use this method when user data is relevant and the body is
     * supposed to move.
     * @param entity the game entity to create a body for
     */
    public void createDynamicRectangle(final Entity entity) {
        // dynamic means the body can move
        bodyDef.type = BodyType.DYNAMIC;
        /*
            jBox2D has the x and y at the center, need to subtract half the width and height
            to get the position of the top left corner (the x and y for javafx).
        */
        bodyDef.position.set(entity.getX() + entity.getWidth() / 2f,
                entity.getY() + entity.getHeight() / 2f);
        Body body = world.createBody(bodyDef);
        // make the shape of the player
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(entity.getWidth() / 2f, entity.getHeight() / 2f);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
        body.setUserData(entity);
        entity.setBody(body);
    }
    /**
     * Creates a new body for the specified game entity for the physics simulation. User data is
     * associated with these bodies. Use this method when user data is relevant and the body is
     * not supposed to move.
     * @param entity the game entity to create the body for
     */
    public void createStaticRectangle(final Entity entity) {
        // body won't move
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(entity.getX(), entity.getY());
        Body body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(entity.getWidth(), entity.getHeight());
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
        body.setUserData(entity);
        entity.setBody(body);
    }
    /**
     * Creates a new body with the specified dimensions and position for the physics simulation.
     * No user data is associated with these bodies. Use this method when having user data is
     * irrelevant and the body is not supposed to move.
     * @param x the x location of the body (center of the body)
     * @param y the y location of the body (center of the body)
     * @param width width of the rectangle body
     * @param height height of the rectangle body
     */
    public void createStaticRectangle(final float x, final float y, final float width, final float height) {
        // body won't move
        bodyDef.type = BodyType.STATIC;
        /*
            set at the center since that is where x and y is for jBox2D
        */
        bodyDef.position.set(x + width / 2, y + height / 2);
        Body body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
    }
    private void createObstacles() {
        TiledReader reader = new FileSystemTiledReader();
        TiledMap map = reader.getMap(RealTest.class.getResource("/map-0-0.tmx").getPath());
        for (TiledLayer layer: map.getTopLevelLayers()) {
            // tiled object layers are where the objects on the map
            if (layer instanceof TiledObjectLayer tiledObjectLayer) {
                // build and each body of the object
                for (TiledObject tiledObject: tiledObjectLayer.getObjects()) {
                    createStaticRectangle(
                            tiledObject.getX(), tiledObject.getY(),
                            tiledObject.getWidth(), tiledObject.getHeight()
                    );
                }
            }
        }
    }
}