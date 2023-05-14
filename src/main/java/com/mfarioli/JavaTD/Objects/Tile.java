package com.mfarioli.JavaTD.Objects;

import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage[] sprite;

    int id;

    String name;

    public BufferedImage getSprite(int animationIndex) {
        return sprite[animationIndex];
    }
    public BufferedImage getSprite() {
        return sprite[0];
    }

    public void setImage(BufferedImage image) {
        sprite[0] = image;
    }

    public Tile(BufferedImage[] sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public Tile(BufferedImage sprite, int id, String name) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.name = name;
    }

    public boolean isAnimation() {
        return sprite.length > 1;
    }
}
