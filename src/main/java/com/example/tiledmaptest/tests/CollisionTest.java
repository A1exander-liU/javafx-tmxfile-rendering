package com.example.tiledmaptest.tests;

import com.example.tiledmaptest.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tiledreader.*;

/**
 * To test collision detection with tiled map object layers.
 */
public class CollisionTest extends Application {
    /**
     * Stuff to do when starting the application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        TiledReader tiledReader = new FileSystemTiledReader();
        TiledMap map = tiledReader.getMap(ResourcePathMapper.getPathToResource("map-0-0.tmx", true));
        Group root = new Group();
        root.getChildren().add(MapRenderer.getInstance().render(map));
        root.getChildren().add(new RectangleObject(16, 16));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        GameLoop gameLoop = new GameLoop() {
            @Override
            public void tick(final float secondsSinceLastFrame) {

            }
        };
        gameLoop.start();
    }
    /**
     * Launches the application.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch();
    }
    /*
    Adds objects from tiled map object layer to the world for collision detection
    and resolution.
     */
    private void addCollidableObjects(final TiledMap map) {
        // makes the assumption there is only a single layer where collision objects are defined
        TiledObjectLayer objectLayer = (TiledObjectLayer) map.getTopLevelLayers().get(2);
        // reusable entity
        Entity entity;
        for (TiledObject object: objectLayer.getObjects()) {
            entity = new Entity(object.getX(), object.getY(), object.getWidth(), object.getHeight());
            CollisionManager.getInstance().addStaticRectangle(entity);
        }
    }
}
