package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Entities.Enemies.Enemy;
import com.mfarioli.JavaTD.Game;
import com.mfarioli.JavaTD.GameStates;
import com.mfarioli.JavaTD.Handlers.*;
import com.mfarioli.JavaTD.Helpers.LevelBuilder;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Objects.PathPoint;
import com.mfarioli.JavaTD.UI.ActionBar;
import com.mfarioli.JavaTD.UI.BottomBar;
import com.mfarioli.JavaTD.UI.CustomButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.Tiles.GRASS_TILE;
import static com.mfarioli.JavaTD.Helpers.LevelBuilder.obsoleteGetLevelData;

public class Playing extends SuperScene implements SceneInterface {
    private int[][] level;

    private TileHandler tileHandler;

    private EnemyHandler enemyHandler;

    private TowerHandler towerHandler;

    private ProjectileHandler projectileHandler;

    private WaveHandler waveHandler;

    private Tower selectedTower;

    private CustomButton bMenu;

    private ActionBar actionBar;

    private int animationIndex;

    private int tick;

    private int mouseX, mouseY;

    private PathPoint start, end;

    public TowerHandler getTowerHandler() {
        return this.towerHandler;
    }

    public EnemyHandler getEnemyHandler() {
        return enemyHandler;
    }

    public WaveHandler getWaveHandler() {
        return waveHandler;
    }

    public Playing(Game game) {
        super(game);
        bMenu = new CustomButton("Menu", 5, 5, 60, 20);
        actionBar = new ActionBar(0, 640, 640, 80, this);

        //call this method once only for creating the .txt file, then just use getLevelData
        //this.createLevel();
        level = this.loadLevel(1);

        tileHandler = new TileHandler();
        enemyHandler = new EnemyHandler(this, start, end);
        towerHandler = new TowerHandler(this);
        projectileHandler = new ProjectileHandler(this);
        waveHandler = new WaveHandler(this);
    }

    /*
     * the render method for the playing scene uses the 2D array from LevelBuilder
     * and passes the value of each element of the array to a tileHandler that then
     * gets the sprite associated to that element of the array (used as an ID for the getSprite method)
     * and finally that sprite is drawn on the JPanel through the drawImage method
     */
    @Override
    public void render(Graphics g) {
        //updateTick();

        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if (isAnimation(id)) {
                    g.drawImage(tileHandler.getAniSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    g.drawImage(tileHandler.getSprite(id), x * 32, y * 32, null);
                }
            }
        }
        actionBar.draw(g);

        enemyHandler.draw(g);
        towerHandler.draw(g);
        projectileHandler.draw(g);

        drawHighlight(g);
        drawSelectedTower(g);
    }

    private void updateTick() {
        tick++;
        //change tick >= x to change the animation speed, the lower the x the faster the animation
        if (tick >= 18) {
            tick = 0;
            animationIndex++;
            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }

    private void drawHighlight(Graphics g) {
        g.setColor(new Color(211, 211, 211, 128));
        g.fillRect(mouseX, mouseY, 32, 32);
        g.setColor(new Color(0, 0, 0, 128));
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    public void update() {
        updateTick();

        waveHandler.update();

        if(areAllEnemiesDead()) {
            if(waveHandler.isThereMoreWaves()) {
                waveHandler.startWaveTimer();
                //check timer
                if(isWaveTimerOver()) {
                    //increase wave index
                    waveHandler.increaseWaveIndex();
                    enemyHandler.getEnemies().clear();
                    waveHandler.resetEnemyIndex();
                }
            }
        }

        if(isEnemyCooldownOver()) spawnEnemy();

        enemyHandler.update();
        towerHandler.update();
        projectileHandler.update();
    }

    private boolean areAllEnemiesDead() {
        //if wave not over, return false
        if(waveHandler.isThereMoreEnemyInWave()) return false;

        for (Enemy e : enemyHandler.getEnemies()) {
            if(e.isAlive()) return false;
        }

        return true;
    }

    private boolean isEnemyCooldownOver() {
        if(!waveHandler.isTimeForNewEnemy()) return false; //if not time for a new enemy, return false
        if(!waveHandler.isThereMoreEnemyInWave()) return false; //if no more enemies in wave, return false

        //if both conditions are met
        return true;
    }

    private boolean isWaveTimerOver() {
        return waveHandler.isWaveTimerOver();
    }

    private void spawnEnemy() {
        enemyHandler.spawnEnemy(waveHandler.getNextEnemy());
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileHandler.newProjectile(t, e);
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower == null)
            return;
        g.drawImage(towerHandler.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;
        if (xCord < 0 || xCord > 19 || yCord < 0 || yCord > 19) {
            return 0; //if out of bounds return water tile
        }
        int id = level[yCord][xCord];
        return getGame().getTileHandler().getTile(id).getTileType();
    }

    private boolean isAnimation(int spriteID) {
        return getGame().getTileHandler().isSpriteAnimation(spriteID);
    }

    private void displayInfo(int x, int y) {
        Tower t = checkTowerAt(x, y);
        actionBar.displayTower(t);
    }

    private boolean isTileGrass(int x, int y) {
        int id = level[y / 32][x / 32];
        int tileType = getGame().getTileHandler().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    private Tower checkTowerAt(int x, int y) {
        return towerHandler.checkTowerAt(x, y);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower == null)
                displayInfo(mouseX, mouseY);
            if (!isTileGrass(mouseX, mouseY))
                return;
            if (checkTowerAt(mouseX, mouseY) != null)
                return;

            towerHandler.addTower(selectedTower, mouseX, mouseY);
            selectedTower = null;
        }

        if (bMenu.getBounds().contains(x, y)) {
            GameStates.setGameState(GameStates.MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }

        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    public void mousePressed(int x, int y) {
        if (y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    private void createLevel() {
        int[][] idArray = LevelBuilder.obsoleteGetLevelData();
    }

    private int[][] loadLevel(int levelNumber) {
        level = LoadSave.getLevelData(levelNumber);
        ArrayList<PathPoint> pathPoints = LoadSave.getLevelPathPoints(levelNumber);

        start = pathPoints.get(0);
        end = pathPoints.get(1);

        return level;
    }
}
