package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;
import com.mfarioli.JavaTD.Handlers.TileHandler;
import com.mfarioli.JavaTD.UI.CustomButton;

import java.awt.*;

import static com.mfarioli.JavaTD.Helpers.LevelBuilder.getLevelData;

public class Playing extends SuperScene implements SceneInterface{
    private int[][] level;

    private TileHandler tileHandler;

    private CustomButton bMenu;

    public Playing(Game game) {
        super(game);
        level = getLevelData();
        tileHandler = new TileHandler();
        bMenu = new CustomButton("Menu", 5, 5, 60, 20);
    }

    /*
    * the render method for the playing scene uses the 2D array from LevelBuilder
    * and passes the value of each element of the array to a tileHandler that then
    * gets the sprite associated to that element of the array (used as an ID for the getSprite method)
    * and finally that sprite is drawn on the JPanel through the drawImage method
    */
    @Override
    public void render(Graphics g) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                g.drawImage(tileHandler.getSprite(id), x*32, y*32, null);
            }
        }
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
