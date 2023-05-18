package com.mfarioli.JavaTD.Inputs;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener {
    private Game game;

    public CustomKeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameStates.gameState == GameStates.PLAYING) {
            game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
