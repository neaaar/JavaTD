package com.mfarioli.JavaTD;

import com.mfarioli.JavaTD.Inputs.CustomKeyListener;
import com.mfarioli.JavaTD.Inputs.CustomMouseListener;
import com.mfarioli.JavaTD.Scenes.Menu;
import com.mfarioli.JavaTD.Scenes.Playing;
import com.mfarioli.JavaTD.Scenes.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;

    private Thread gameThread;

    private CustomMouseListener mouseListener;

    private CustomKeyListener keyListener;

    private GameRender gameRender;

    private Menu menu;

    private Playing playing;

    private Settings settings;

    public GameRender getGameRender() {
        return gameRender;
    }

    public void setGameRender(GameRender gameRender) {
        this.gameRender = gameRender;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public void setPlaying(Playing playing) {
        this.playing = playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Game() {
        gameScreen = new GameScreen(this);
        add(gameScreen);

        gameThread = new Thread(this);

        mouseListener = new CustomMouseListener();
        keyListener = new CustomKeyListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addKeyListener(keyListener);
        requestFocus();

        this.gameRender = new GameRender(this);
        this.menu = new Menu(this);
        this.playing = new Playing(this);
        this.settings = new Settings(this);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();  //initialize JFrame size and position
        game.start();       //start the gameThread Thread
    }

    private void initialize() {
        setLocation(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack(); //lets the windowManager create the panel given the values in JPanel
        setVisible(true);
    }

    private void start() {
        gameThread.start();
    }

    @Override
    public void run() {
        //FPS stuff
        final int FPS = 144;
        double timePerFrame = 1000000000 / FPS;
        long lastFrame = System.nanoTime();
        int frames = 0;

        //UPS stuff
        final int UPS = 60;
        double timePerUpdate = 1000000000 / UPS;
        long lastUpdate = System.nanoTime();
        int updates = 0;

        //used for both FPS and UPS
        long lastTimeCheck = System.currentTimeMillis();
        long now;

        while(true) {
            //update now variable
            now = System.nanoTime();

            //enough time has passed, render frame
            if(now - lastFrame > timePerFrame) {
                lastFrame = now;
                repaint();
                frames++;
            }

            //enough time has passed, update the game
            if(now - lastUpdate > timePerUpdate) {
                lastUpdate = now;
                //updateGame metodo per fare update
                updates++;
            }

            //a second has passed, print FPS and UPS
            if(System.currentTimeMillis() - lastTimeCheck >= 1000) {
                lastTimeCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}