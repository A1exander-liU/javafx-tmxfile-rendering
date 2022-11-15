package com.example.tiledmaptest.tests;

import com.example.tiledmaptest.GameLoop;
import com.example.tiledmaptest.MapRenderer;
import com.example.tiledmaptest.RectangleObject;
import com.example.tiledmaptest.ResourcePathMapper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledReader;

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
}
