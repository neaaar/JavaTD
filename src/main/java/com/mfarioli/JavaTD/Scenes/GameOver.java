package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.UI.CustomButton;

import java.awt.*;

import static com.mfarioli.JavaTD.GameStates.*;

public class GameOver extends SuperScene implements SceneInterface{
    private CustomButton bMenu, bReplay;
    public GameOver(Game game) {
        super(game);
        InitButtons();
    }

    private void InitButtons() {
        int w = 200;
        int h = 20;
        int x = (640 - w) / 2;
        int y = 245;
        int yOffset = 30;

        bMenu = new CustomButton("Menu", x, y, w, h);
        bReplay = new CustomButton("Replay", x, y + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {
        bMenu.draw(g);
        bReplay.draw(g);

        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.BLUE);
        g.drawString("Game over", 180, 215);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            game.getPlaying().resetEverything();
            setGameState(MENU);
        }

        if(bReplay.getBounds().contains(x, y)) {
            game.getPlaying().resetEverything();
            setGameState(PLAYING);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if(bReplay.getBounds().contains(x, y)) {
            bReplay.setMouseOver(true);
        }
    }
}
