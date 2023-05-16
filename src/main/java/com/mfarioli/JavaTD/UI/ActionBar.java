package com.mfarioli.JavaTD.UI;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.Scenes.Playing;

import static com.mfarioli.JavaTD.GameStates.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class ActionBar extends Bar {

    private Playing playing;

    private CustomButton bMenu;

    private CustomButton[] towerButtons;

    private Tower selectedTower;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        initButtons();
    }

    private void initButtons() {
        //da togliere questo bMenu oppure il bMenu di playing
        bMenu = new CustomButton("Menu", 2, 642, 100, 30);
        towerButtons = new CustomButton[3];

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for(int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new CustomButton(i, "", xStart + i * xOffset, yStart, w, h);
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for(CustomButton b : towerButtons) {
            //b.draw(g);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerHandler().getTowerImages()[b.getId()], b.x, b.y, b.width, b.height, null);

            drawButtonFeedback(g, b);
        }
    }

    public void draw(Graphics g) {

        // Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        // Buttons
        drawButtons(g);
        drawButtonFeedback(g, bMenu);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameState(MENU);
        } else {
            for(CustomButton b : towerButtons) {
                if(b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(-1, 0, 0, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for(CustomButton b : towerButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for(CustomButton b : towerButtons) {
                if(b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        for(CustomButton b : towerButtons) {
            if(b.getBounds().contains(x, y)) {
                b.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(int x, int y) {
        for(CustomButton b : towerButtons) {
            b.resetBooleans();
        }
    }
}

