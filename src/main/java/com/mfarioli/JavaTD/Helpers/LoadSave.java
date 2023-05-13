package com.mfarioli.JavaTD.Helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public static BufferedImage getMenuBackground() {
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("menubackground.png");

        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}
