package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends SuperScene implements SceneInterface{
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private BufferedImage image;

    private Random rnd;

    public Menu(Game game) {
        super(game);
        importImage();
        loadSprites();
        this.rnd = new Random();
    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(sprites.get(19), 0, 0, null);
        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 20; x++) {
                g.drawImage(sprites.get(getRndInt(20)), x*32, y*32, null);
            }
        }
    }

    private void importImage() {
        InputStream inputStream = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //loads every sprite in spriteatlas.png and adds it to a list
    private void loadSprites() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x*32, y*32, 32, 32));
            }
        }
    }

    private int getRndInt(int bound) {
        return rnd.nextInt(bound);
    }
}
