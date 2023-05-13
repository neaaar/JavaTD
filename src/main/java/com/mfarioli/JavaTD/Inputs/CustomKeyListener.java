package com.mfarioli.JavaTD.Inputs;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("change to playing scene");
            GameStates.gameState = GameStates.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
