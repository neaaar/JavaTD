package com.mfarioli.JavaTD;

import com.mfarioli.JavaTD.Inputs.CustomKeyListener;
import com.mfarioli.JavaTD.Inputs.CustomMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Game game;

    private Dimension size;

    private CustomMouseListener mouseListener;

    private CustomKeyListener keyListener;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
        initImputs();
    }

    //sets the dimension for the Game panel
    private void setPanelSize() {
        size = new Dimension(640, 720);

        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    public void initImputs() {
        mouseListener = new CustomMouseListener(game);
        keyListener = new CustomKeyListener(game);

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addKeyListener(keyListener);
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getGameRender().render(g);
    }
}
