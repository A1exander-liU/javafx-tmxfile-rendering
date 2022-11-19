package com.example.tiledmaptest;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.jbox2d.common.Vec2;
import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledReader;

import java.io.IOException;
import java.security.Key;

public class RealTest extends Application {
    /**
     * Used for declaring some objects. Some prep stuff.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(final Stage primaryStage) {
        TiledReader reader = new FileSystemTiledReader();
        TiledMap map = reader.getMap(RealTest.class.getResource("/map-0-0.tmx").getPath());
        GridPane renderedMap = null;
        try {
            renderedMap = MapRenderer.getInstance().render(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Group root = new Group();
        if (renderedMap != null) {
            root.getChildren().add(renderedMap);
        }
        Entity player = new Entity(16, 16);
        WorldManager.getInstance().createDynamicRectangle(player);
        player.getRectangle().setFocusTraversable(true);
        player.getRectangle().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W -> player.getBody().setLinearVelocity(new Vec2(0, -player.getSpeed()));
                case A -> player.getBody().setLinearVelocity(new Vec2(-player.getSpeed(), 0));
                case S -> player.getBody().setLinearVelocity(new Vec2(0, player.getSpeed()));
                case D -> player.getBody().setLinearVelocity(new Vec2(player.getSpeed(), 0));
                default -> { }
            }
        });
        player.getRectangle().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case W, A, S, D -> player.getBody().setLinearVelocity(new Vec2(0, 0));
                default -> { }
            }
        });
        root.getChildren().add(player.getRectangle());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                WorldManager.getInstance().updateWorld();
            }
        };
        animationTimer.start();
    }

    /**
     * Launches the game.
     * @param args command line args
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
