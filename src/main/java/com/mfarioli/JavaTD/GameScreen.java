package com.mfarioli.JavaTD;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {
    private Random rnd;

    private BufferedImage image;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public GameScreen(BufferedImage image) {
        this.image = image;
        loadSprites();
        this.rnd = new Random();
    }

    private void loadSprites() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x*32, y*32, 32, 32));
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g.drawImage(sprites.get(19), 0, 0, null);

        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 20; x++) {
                g.drawImage(sprites.get(getRndInt(20)), x*32, y*32, null);
            }
        }
    }

    private int getRndInt(int bound) {
        return rnd.nextInt(bound);
    }
    private Color getRndColor() {
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);

        return new Color(r, g, b);
    }
}
