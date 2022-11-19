package com.example.tiledmaptest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tiledreader.TiledTile;
import org.tiledreader.TiledMap;
import org.tiledreader.TiledTileset;
import org.tiledreader.TiledLayer;
import org.tiledreader.TiledTileLayer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Provides different ways to 'render' a tiled map.
 */
public final class MapRenderer {
    private static MapRenderer mapRenderer;
    private MapRenderer() { }

    /**
     * Returns an instance of a MapReader.
     * @return a MapRenderer
     */
    public static MapRenderer getInstance() {
        if (mapRenderer == null) {
            mapRenderer = new MapRenderer();
        }
        return mapRenderer;
    }

    /**
     * Renders a map into a StackPane of GridPane's. Each tile will be stored
     * as an ImageView in each cell of the GridPane.
     * @param map the map to render
     * @return the rendered map
     */
    public StackPane render(final TiledMap map) throws IOException {
        StackPane stackPane = new StackPane();
        HashMap<String, HashMap<Integer, Image>> tiles = new HashMap<>();
        for (TiledTileset tiledTileset: map.getTilesets()) {
            tiles.put(tiledTileset.getName(), new HashMap<>());
        }
        // iterate through each layer
        for (TiledLayer tiledLayer: map.getTopLevelLayers()) {
            GridPane gridPane = new GridPane();
            // TiledTileLayers are the layers where actual tiles are drawn
            if (tiledLayer instanceof TiledTileLayer tiledTileLayer) {
                // iterate through all the tiles on the layer
                for (int x = 0; x < map.getWidth(); x++) {
                    for (int y = 0; y < map.getHeight(); y++) {
                        // get tile at the specified location
                        TiledTile tile = tiledTileLayer.getTile(x, y);
                        ImageView imageView;
                        // make the actual tile with its image if a tile was drawn there
                        if (tile != null) {
                            imageView = makeTile(tiles, tile, tile.getTileset().getName(),
                                    tile.getTilesetX(), tile.getTilesetY());
                        } else { // no tile was drawn here so make a dummy tile
                            imageView = makeEmptyTile(map.getTileWidth(), map.getTileHeight());
                        }
                        gridPane.add(imageView, x, y);
                    }
                }
                stackPane.getChildren().add(gridPane);
            }
        }
        return stackPane;
    }
    /*
    Returns an image view of the image of the tile. If the image already exists in the hashmap,
    it will use the one inside otherwise it will make a new image and add it into the hashmap
    and use the image that was added.
     */
    private ImageView makeTile(
            final HashMap<String, HashMap<Integer, Image>> tiles,
            final TiledTile tile, final String tilesetName,
            final int x, final int y) throws IOException {
        // check if the hashmap contains the image of the tile
        if (tiles.get(tilesetName).containsKey(tile.getID())) {
            // use the image inside if it was contained
            Image image = tiles.get(tilesetName).get(tile.getID());
            return new ImageView(image);
        } else {
            // since the image was not in the hashmap, make the image and add it into the hashmap
            Image image = ImageCropper.crop(tile.getTileset().getImage().getSource(),
                    x * tile.getTileset().getTileWidth(), y * tile.getTileset().getTileHeight(),
                    tile.getTileset().getTileWidth(), tile.getTileset().getTileHeight(), false);
            tiles.get(tilesetName).put(tile.getID(), image);
            return new ImageView(image);
        }
    }
    /*
    Returns an image view of a dummy tile where a tile was not drawn. Defines the width and height
    of the dummy tile to be the same as a regular tile.
     */
    private ImageView makeEmptyTile(final int tileWidth, final int tileHeight) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(tileWidth);
        imageView.setFitHeight(tileHeight);
        return imageView;
    }
}
