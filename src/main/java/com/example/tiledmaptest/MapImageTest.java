package com.example.tiledmaptest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledReader;

/**
 * Tests rendering a tiled map from an image.
 */
public final class MapImageTest extends Application {
    private TiledMap tiledMap;
    /**
     * Test rendering a tiled map from a image.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        TiledReader tiledReader = new FileSystemTiledReader();
        String path = ResourcePathMapper.getPathToResource("map-0-0.tmx", true);
        tiledMap = tiledReader.getMap(path);
        Group root = new Group();
        root.getChildren().add(MapRenderer.getInstance().render(tiledMap, "map-0-0.png"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the application.
     * @param args command line args
     */
    public static void main(final String[] args) {
        launch();
    }
}
