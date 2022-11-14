package com.example.tiledmaptest.starter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Rectangle player;

    @Override
    public void start(Stage stage) throws IOException {
        Image image = new Image("map-0-0.png");
        ImageView imageView = new ImageView(image);
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        gridPane.add(imageView, 0, 0, 30, 24);
        gridPane.setGridLinesVisible(true);
        stackPane.getChildren().add(gridPane);
        player = new Rectangle(16, 16);
        stackPane.getChildren().add(player);
        Scene scene = new Scene(new Group(imageView, player), image.getWidth(), image.getHeight());
        EventHandler<KeyEvent> movement = new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> player.setY(player.getY() - 5);
                    case RIGHT -> player.setX(player.getX() + 5);
                    case DOWN -> player.setY(player.getY() + 5);
                    case LEFT -> player.setX(player.getX() - 5);
                }

            }
        };
        stage.setTitle("Game");
        stage.setScene(scene);
        scene.setOnKeyPressed(movement);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}