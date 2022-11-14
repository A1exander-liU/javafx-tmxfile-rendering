package com.example.tiledmaptest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
     * Renders a tiled map into a grid pane. Each tile rendered will be stored
     * as in image in each cell of the grid pane. Will be rendered into a single grid pane
     * @param tiledMap the map to render
     * @return the map rendered in a grid pane
     * @throws IOException when an illegal IO operation happens
     */
    public GridPane render(final TiledMap tiledMap) throws IOException {
        // To store all the images used on the tile map
        // A hashmap of hashmaps!? :0
        // Key(String) is the name of the tileset
        // Value(Hashmap<Integer, Image>) is a map of local tile ids and the associated image
        // Since the tile id is local to its own tileset, need to also define which tileset the tile belongs to
        HashMap<String, HashMap<Integer, Image>> tiles = new HashMap<>();
        for (TiledTileset tiledTileset: tiledMap.getTilesets()) {
            tiles.put(tiledTileset.getName(), new HashMap<>());
        }
        GridPane gridPane = new GridPane();
        // reusable tile
        TiledTile tile;
        for (TiledLayer mapLayer: tiledMap.getTopLevelLayers()) {
            // TiledTileLayers are the ones you actually render
            if (mapLayer instanceof TiledTileLayer) {
                // Nothing you can really do to not cast
                TiledTileLayer tiledTileLayer = (TiledTileLayer) mapLayer;
                // looping through each tile
                for (int x = 0; x < tiledMap.getWidth(); x++) {
                    for (int y = 0; y < tiledMap.getHeight(); y++) {
                        tile = tiledTileLayer.getTile(x, y);
                        // since there is a tile at this position, add its image
                        if (tile != null) {
                            makeTile(tile, tiles, gridPane, x, y);
                        } else {
                            // no tile at this position, so make an 'empty' tile to fill the space
                            gridPane.add(makeEmptyTile(tiledMap), x, y);
                        }
                    }
                }
            }
        }
        return gridPane;
    }
    /**
     * Renders a tiled map by taking an image instead. Divides the image into tiles
     * and stores in a grid pane.
     * @param tiledMap the tiled map
     * @param mapImagePath the relative path to the image of the map
     * @return the map rendered in a grid pane
     * @throws IOException when an illegal IO operation happens
     */
    public GridPane render(final TiledMap tiledMap, final String mapImagePath) throws IOException {
        GridPane gridPane = new GridPane();
        for (int x = 0; x < tiledMap.getWidth(); x++) {
            for (int y = 0; y < tiledMap.getHeight(); y++) {
                Image tileImg =  ImageCropper.crop(mapImagePath, x * tiledMap.getTileWidth(),
                        y * tiledMap.getTileHeight(), tiledMap.getTileWidth(),
                        tiledMap.getTileHeight(), true);
                gridPane.add(new ImageView(tileImg), x, y);
            }
        }
        return gridPane;
    }
    /**
     * Creates a new tile, if the tile already exists in the hashmap, it will reuse
     * the same image rather than creating a new image.
     * @param tile the tile of the current iteration
     * @param tiles the hashmap of the tile images in each tileset
     * @param gridPane where the tiles are added to
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @throws IOException when an illegal IO operation happens
     */
    private void makeTile(final TiledTile tile, final HashMap<String, HashMap<Integer, Image>> tiles,
                          final GridPane gridPane, final int x, final int y) throws IOException {
        String tileSetName = tile.getTileset().getName();
        // if the image already exists, use it instead of making anew one
        if (tiles.get(tileSetName).containsKey(tile.getID())) {
            ImageView view = new ImageView(tiles.get(tileSetName).get(tile.getID()));
            gridPane.add(view, x, y);
        } else {
            // make and add the image to the hashmap if it wasn't created already
            Image tileImg = ImageCropper.crop(tile.getTileset().getImage().getSource(),
                    tile.getTilesetX() * tile.getTileset().getTileWidth(),
                    tile.getTilesetY() * tile.getTileset().getTileHeight(),
                    tile.getTileset().getTileWidth(), tile.getTileset().getTileHeight(), false);
            tiles.get(tileSetName).put(tile.getID(), tileImg);
            ImageView view = new ImageView(tiles.get(tileSetName).get(tile.getID()));
            gridPane.add(view, x, y);
        }
    }
    /*
    Creates an empty image view that is the size of a single tile.
     */
    private ImageView makeEmptyTile(final TiledMap tiledMap) {
        ImageView view = new ImageView();
        view.setFitWidth(tiledMap.getTileWidth());
        view.setFitHeight(tiledMap.getTileHeight());
        return view;
    }
}
