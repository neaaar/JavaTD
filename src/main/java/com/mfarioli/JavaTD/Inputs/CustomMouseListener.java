package com.mfarioli.JavaTD.Inputs;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CustomMouseListener implements MouseListener, MouseMotionListener {
    private Game game;

    public CustomMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU -> {
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                }

                case SETTINGS -> {
                    game.getSettings().mouseClicked(e.getX(), e.getY());
                    break;
                }

                case PLAYING -> {
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                }

                case GAME_OVER -> {
                    game.getGameOver().mouseClicked(e.getX(), e.getY());
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU, GAME_OVER, SETTINGS -> {
                break;
            }

            case PLAYING -> {
                game.getPlaying().mousePressed(e.getX(), e.getY());
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU, SETTINGS -> {
                break;
            }

            case PLAYING -> {
                game.getPlaying().mouseReleased(e.getX(), e.getY());
                break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU -> {
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            }

            case SETTINGS -> {
                game.getSettings().mouseMoved(e.getX(), e.getY());
                break;
            }

            case PLAYING -> {
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            }

            case GAME_OVER -> {
                game.getGameOver().mouseMoved(e.getX(), e.getY());
            }
        }
    }
}
