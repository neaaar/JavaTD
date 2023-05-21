package com.mfarioli.JavaTD.UI;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.Helpers.Constants;
import com.mfarioli.JavaTD.Scenes.Playing;

import static com.mfarioli.JavaTD.GameStates.*;
import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.getGoldReward;
import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.getTowerCost;
import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.getTowerName;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class ActionBar extends Bar {

    private Playing playing;

    private CustomButton bMenu, bPause, sellTower, upgradeTower;

    private CustomButton[] towerButtons;

    private Tower selectedTower, displayedTower;

    private DecimalFormat formatter;

    private int gold, towerCostType, lives;

    private boolean showTowerCost;

    public int getLives() {
        return lives;
    }

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.formatter = new DecimalFormat("0.0");
        gold = 75;
        lives = 5;

        initButtons();
    }

    private void initButtons() {
        bMenu = new CustomButton("Menu", 2, 642, 100, 30);
        bPause = new CustomButton("Pause", 2, 675, 100, 30);
        sellTower = new CustomButton("Sell", 470, 680, 50, 25);
        upgradeTower = new CustomButton("Upgrade", 560, 680, 50, 25);

        towerButtons = new CustomButton[3];

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new CustomButton(i, "", xStart + i * xOffset, yStart, w, h);
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        //bPause.draw(g);

        for (CustomButton b : towerButtons) {
            //b.draw(g);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerHandler().getTowerImages()[b.getId()], b.x, b.y, b.width, b.height, null);

            drawButtonFeedback(g, b);
        }
    }

    public void draw(Graphics g) {
        //Background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        //Buttons
        drawButtons(g);
        drawButtonFeedback(g, bMenu);

        //Displayed tower
        drawDisplayedTower(g);

        //Wave infos
        drawWaveInfos(g);

        //Gold info
        drawGoldAmount(g);
        drawTowerCost(g);

        //Lives
        drawLives(g);
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayedTower == null)
            return;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(420, 650, 195, 60);

        g.setColor(Color.BLACK);
        g.drawRect(420, 650, 195, 60);

        g.drawImage(playing.getTowerHandler().getTowerImages()[displayedTower.getTowerType()], 430, 665, 30, 30, null);
        g.drawRect(425, 660, 40, 40);

        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        g.drawString("" + getTowerName(displayedTower.getTowerType()), 470, 670);
        //g.drawString("Tier: " + displayedTower.getTier(), 530, 670);

        drawDisplayedTowerBorder(g);
        drawDisplayedTowerRange(g);

        g.setFont(new Font("LucidaSans", Font.BOLD, 10));
        sellTower.draw(g);
/*        if(displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower()) {
            upgradeTower.draw(g);
        }*/

        if(sellTower.isMouseOver()) {
            g.setColor(Color.RED);
            g.drawString(getSellAmount(displayedTower) + "g" , 530, 695);
        } else if(upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
            g.setColor(Color.BLUE);
            g.drawString(getUpgradeAmount(displayedTower) + "g" , 530, 695);
        }
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayedTower.getX() + 16 - (int) displayedTower.getRange(), displayedTower.getY() + 16 - (int) displayedTower.getRange(), (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
    }

    private void drawWaveInfos(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveHandler().getWaveIndex();
        int size = playing.getWaveHandler().getWaves().size();

        g.setColor(new Color(255, 255, 255, 128));
        g.fillRect(537, 8, 94, 25);
        g.setColor(Color.BLACK);
        g.drawString("Waves: " + (current + 1) + "/" + size, 541, 25);

    }

    private void drawEnemiesLeftInfo(Graphics g) {
        if(!playing.getWaveHandler().isWaveTimerStarted()) {
            int killedEnemies = playing.getEnemyHandler().getDeadEnemiesAmount();
            int remaining = playing.getWaveHandler().getWaves().get(playing.getWaveHandler().getWaveIndex()).getEnemyTypes().size();

            g.setColor(new Color(255, 255, 255, 128));
            g.fillRect(16, 8, 125, 25);
            g.setColor(Color.BLACK);
            g.drawString("Enemies: " + killedEnemies + "/" + remaining, 20, 25);
        }
    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveHandler().isWaveTimerStarted()) {
            float timeLeft = playing.getWaveHandler().getTimeLeft();
            String formattedText = formatter.format(timeLeft);

            g.setColor(new Color(255, 255, 255, 128));
            g.fillRect(16, 8, 125, 25);
            g.setColor(Color.BLACK);
            g.drawString("Time Left: " + formattedText, 20, 25);
        }
    }

    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: " + gold, 110, 715);
    }

    private void drawTowerCost(Graphics g) {
        if(!showTowerCost) return;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(280, 650, 120, 40);

        g.setColor(Color.BLACK);
        g.drawRect(280, 650, 120, 40);

        g.drawString("" + getTowerName(towerCostType), 285, 668);

        if(isGoldEnough(towerCostType)) {
            g.drawString("Cost: " + getTowerCost(towerCostType) + "g", 285, 683);
        } else { //if gold isn't enough, show red text
            g.setColor(Color.RED);
            g.drawString("Cost: " + getTowerCost(towerCostType) + "g", 285, 683);
        }

    }

    private void drawLives(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));

        g.setColor(new Color(255, 255, 255, 128));
        g.fillRect(287, 8, 75, 25);
        g.setColor(Color.BLACK);
        g.drawString("Lives: " + lives, 290, 25);
    }

    public void payForTowerType(int towerType) {
        gold -= getTowerCost(towerType);
    }

    private boolean isGoldEnough(int towerType) {
        return gold > getTowerCost(towerType);
    }

    public void addGold(int goldAmount) {
        gold += goldAmount;
    }

    public void removeOneLife() {
        lives--;
        if(lives <= 0) {
            setGameState(GAME_OVER);
        }
    }

    private void sellTowerClicked() {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost /= 2;

        playing.removeTower(displayedTower);
        addGold(getTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost);
        displayedTower = null;
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost /= 2;

        return getTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int)(getTowerCost(displayedTower.getTowerType()) * 0.33f);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            playing.resetEverything();
            setGameState(MENU);
        } else if (bPause.getBounds().contains(x, y)) {
            playing.setGamePaused(!playing.isGamePaused());
        } else {
            if(displayedTower != null) {
                if(sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                    upgradeTowerClicked();
                    return;
                }
            }

            for (CustomButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if(!isGoldEnough(b.getId())) return; //if gold isn't enough, do not select tower

                    selectedTower = new Tower(-1, 0, 0, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        showTowerCost = false;

        for (CustomButton b : towerButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMouseOver(true);
        } else {
            if(displayedTower != null) {
                if(sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }

            for (CustomButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(displayedTower != null) {
            if(sellTower.getBounds().contains(x, y)) {
                sellTower.setMousePressed(true);
                return;
            } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                upgradeTower.setMousePressed(true);
                return;
            }
        }

        for (CustomButton b : towerButtons) {
            if (b.getBounds().contains(x, y)) {
                b.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(int x, int y) {
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        for (CustomButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void resetEverything() {
        gold = 75;
        lives = 5;
        towerCostType = 0;
        showTowerCost = false;
        selectedTower = null;
        displayedTower = null;
    }
}

