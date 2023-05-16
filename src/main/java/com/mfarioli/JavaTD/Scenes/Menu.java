package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.UI.CustomButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public class Menu extends SuperScene implements SceneInterface{
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private BufferedImage atlas, background;

    private Random rnd;

    private CustomButton bPlaying, bSettings, bQuit;

    public Menu(Game game) {
        super(game);
        loadAtlas();
        loadSprites();
        this.rnd = new Random();
        initButtons();
    }

    @Override
    public void render(Graphics g) {
        background = LoadSave.getMenuBackground();
        g.drawImage(background, 0, 0, null);
        g.setFont(new Font ("LucidaSans", Font.PLAIN, 15));
        bPlaying.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    //loads every sprite in spriteatlas.png and adds it to a list
    private void loadSprites() {
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 10; x++) {
                sprites.add(atlas.getSubimage(x*32, y*32, 32, 32));
            }
        }
    }

    private void initButtons() {
        int w = 200;
        int h = 20;
        int x = (640 - w) / 2;
        int y = 275;
        int yOffset = 30;

        bPlaying = new CustomButton("Play", x, y, w, h);
        bSettings = new CustomButton("Settings", x, y + yOffset, w, h);
        bQuit = new CustomButton("Quit", x, y + yOffset * 2, w, h);
    }

    private int getRndInt(int bound) {
        return rnd.nextInt(bound);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bPlaying.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.PLAYING);
        }

        if(bSettings.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.SETTINGS);
        }

        if(bQuit.getBounds().contains(x, y)) {
            exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        if(bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        }

        bSettings.setMouseOver(false);
        if(bSettings.getBounds().contains(x, y)) {
            bSettings.setMouseOver(true);
        }

        bQuit.setMouseOver(false);
        if(bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }
}
