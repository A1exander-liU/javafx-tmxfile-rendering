package com.example.tiledmaptest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledReader;
import org.tiledreader.FileSystemTiledReader;

/**
 * Test for rendering a tmx file with tile by tile rendering.
 */
public class TMXMapTest extends Application {
    private TiledMap tiledMap;

    /**
     * Renders a tmx file.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        // getting and reading the .tmx file
        TiledReader tiledReader = new FileSystemTiledReader();
        String path = ResourcePathMapper.getPathToResource("map-0-0.tmx", true);
        tiledMap = tiledReader.getMap(path);
        Group root = new Group();
        root.getChildren().add(MapRenderer.getInstance().render(tiledMap));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the program.
     * @param args command line args
     */
    public static void main(final String[] args) {
        launch();
    }
}
