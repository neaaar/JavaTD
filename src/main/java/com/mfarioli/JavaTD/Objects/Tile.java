package com.mfarioli.JavaTD.Objects;

import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage sprite;

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setImage(BufferedImage image) {
        sprite = image;
    }

    public Tile(BufferedImage image) {
        sprite = image;
    }

}
