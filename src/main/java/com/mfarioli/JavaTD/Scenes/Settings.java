package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;
import com.mfarioli.JavaTD.UI.CustomButton;

import java.awt.*;

public class Settings extends SuperScene implements SceneInterface {
    private CustomButton bMenu, bMusicOn, bMusicOff;

    public Settings(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        bMenu = new CustomButton("Menu", 5, 5, 60, 20);
        bMusicOn = new CustomButton("On", 280, 270, 30, 20);
        bMusicOff = new CustomButton("Off", 340, 270, 30, 20);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bMusicOn.draw(g);
        bMusicOff.draw(g);
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
        g.drawString("Background music", 255, 255);
        drawButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        } else if (bMusicOn.getBounds().contains(x, y)) {
            getGame().getBackgroundMusicPlayer().start();
        } else if (bMusicOff.getBounds().contains(x, y)) {
            getGame().getBackgroundMusicPlayer().stop();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bMusicOn.setMouseOver(false);
        bMusicOff.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bMusicOn.getBounds().contains(x, y)) {
            bMusicOn.setMouseOver(true);
        } else if (bMusicOff.getBounds().contains(x, y)) {
            bMusicOff.setMouseOver(true);
        }
    }
}
