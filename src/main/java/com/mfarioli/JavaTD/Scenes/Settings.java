package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;
import com.mfarioli.JavaTD.UI.CustomButton;

import java.awt.*;

public class Settings extends SuperScene implements SceneInterface{
    private CustomButton bMenu;

    public Settings(Game game) {
        super(game);
        bMenu = new CustomButton("Menu", 5, 5, 60, 20);
    }

    @Override
    public void render(Graphics g) {
        bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }
}
