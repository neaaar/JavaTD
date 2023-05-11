package com.mfarioli.JavaTD.Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("A has been pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
