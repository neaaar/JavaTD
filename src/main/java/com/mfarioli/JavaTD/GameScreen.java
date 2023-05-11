package com.mfarioli.JavaTD;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Game game;

    private Dimension size;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    //sets the dimension for the Game panel
    private void setPanelSize() {
        size = new Dimension(640, 640);

        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.getGameRender().render(g);
    }
}
