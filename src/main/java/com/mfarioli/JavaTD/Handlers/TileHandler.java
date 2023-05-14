package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Helpers.Utilities;
import com.mfarioli.JavaTD.Objects.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.LoadSave.getSpriteAtlas;

public class TileHandler {
    public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER,
            L_WATER, TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE;

    private BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsS = new ArrayList<>();
    public ArrayList<Tile> roadsC = new ArrayList<>();
    public ArrayList<Tile> corners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    public TileHandler() {
        loadAtlas();
        createTiles();
    }

    /*
    * tiles index to tile name:
    * 0  - Grass
    * 1  - Water
    *
    * 2  - Road Left to Right
    * 3  - Road Top to Bottom
    *
    * 4  - Road Bottom to Right
    * 5  - Road Left to Bottom
    * 6  - Road Left to Top
    * 7  - Road Top to Right
    *
    * 8  - Bottom Left Water Corner
    * 9  - Top Left Water Corner
    * 10 - Top Right Water Corner
    * 11 - Bottom Right Water Corner
    *
    * 12 - Top Water Border
    * 13 - Right Water Border
    * 14 - Bottom Water Border
    * 15 - Left Water Border
    *
    * 16 - Top Left Isle
    * 17 - Top Right Isle
    * 18 - Bottom Right Isle
    * 19 - Bottom Left Isle
     */
    private void createTiles() {
        int id = 0;

        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, "grassTile"));
        tiles.add(WATER = new Tile(getAniSprites(0, 0), id++, "waterTile"));

        roadsS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, "roadTile"));
        roadsS.add(ROAD_TB = new Tile(Utilities.getRotImg(getSprite(8, 0), 90), id++, "roadTile"));

        roadsC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, "roadTile"));
        roadsC.add(ROAD_L_TO_B = new Tile(Utilities.getRotImg(getSprite(7, 0), 90), id++, "roadTile"));
        roadsC.add(ROAD_L_TO_T = new Tile(Utilities.getRotImg(getSprite(7, 0), 180), id++, "roadTile"));
        roadsC.add(ROAD_T_TO_R = new Tile(Utilities.getRotImg(getSprite(7, 0), 270), id++, "roadTile"));

        corners.add(BL_WATER_CORNER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 0), id++, "waterTile"));
        corners.add(TL_WATER_CORNER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 90), id++, "waterTile"));
        corners.add(TR_WATER_CORNER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 180), id++, "waterTile"));
        corners.add(BR_WATER_CORNER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(5, 0), 270), id++, "waterTile"));

        beaches.add(T_WATER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 0), id++, "waterTile"));
        beaches.add(R_WATER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 90), id++, "waterTile"));
        beaches.add(B_WATER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 180), id++, "waterTile"));
        beaches.add(L_WATER = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(6, 0), 270), id++, "waterTile"));

        islands.add(TL_ISLE = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 0), id++, "waterTile"));
        islands.add(TR_ISLE = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 90), id++, "waterTile"));
        islands.add(BR_ISLE = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 180), id++, "waterTile"));
        islands.add(BL_ISLE = new Tile(Utilities.getBuildRotImg(getAniSprites(0, 0), getSprite(4, 0), 270), id++, "waterTile"));

        tiles.addAll(roadsS);
        tiles.addAll(roadsC);
        tiles.addAll(corners);
        tiles.addAll(beaches);
        tiles.addAll(islands);
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

    public BufferedImage getAniSprite(int id, int animationIndex) {
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAniSprites(int xCord, int yCord) {
        BufferedImage[] bufferedImages = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            bufferedImages[i] = getSprite(xCord + i, yCord);
        }

        return bufferedImages;
    }

    public boolean isSpriteAnimation(int spriteID) {
        return tiles.get(spriteID).isAnimation();
    }

    private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[]{getSprite(firstX, firstY), getSprite(secondX, secondY)};
    }
}
