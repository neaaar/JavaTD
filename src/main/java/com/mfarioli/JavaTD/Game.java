package com.mfarioli.JavaTD;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {
    private GameScreen gameScreen;

    private BufferedImage image;

    public Game() {
        importImage();

        this.gameScreen = new GameScreen(image);
        add(gameScreen);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
    }

    private void importImage() {
        InputStream inputStream = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        setSize(640, 640);
        setVisible(true);
        setLocation(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //da fare: gameLoop per regolare FPS/UPS
}