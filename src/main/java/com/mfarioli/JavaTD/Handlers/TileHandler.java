package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Objects.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.LoadSave.getSpriteAtlas;

public class TileHandler {
    public Tile grass, water, road;

    public BufferedImage atlas;

    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileHandler() {
        loadAtlas();

        grass = new Tile(getSprite(8, 1));
        water = new Tile(getSprite(0, 6));
        road = new Tile(getSprite(9, 0));

        createTiles();
    }

    private void createTiles() {
        tiles.add(grass);
        tiles.add(water);
        tiles.add(road);
    }

    private void loadAtlas() {

        atlas = LoadSave.getSpriteAtlas();
    }

    private BufferedImage getSprite(int x, int y) {
        return atlas.getSubimage(x*32, y*32, 32, 32);
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }
}
