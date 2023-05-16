package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Entities.Enemies.*;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Objects.PathPoint;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.Direction.*;
import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.*;
import static com.mfarioli.JavaTD.Helpers.Constants.Tiles.*;

public class EnemyHandler {
    private Playing playing;

    private BufferedImage[] enemyImages;

    private ArrayList<Enemy> enemies;

    //private float speed = 0.5f;

    private PathPoint start, end;

    public EnemyHandler(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        this.start = start;
        this.end = end;
        enemyImages = new BufferedImage[4];
        loadEnemyImages();

        enemies = new ArrayList<>();
        addEnemy(ORC);
        addEnemy(BAT);
        addEnemy(KNIGHT);
        addEnemy(WOLF);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i*32, 1*32, 32, 32);
        }
    }

    public void addEnemy(int enemyType) {
        int x = start.getxCord();
        int y = start.getyCord();

        switch(enemyType) {
            case ORC -> {
                enemies.add(new Orc(0, x, y));
                break;
            }

            case BAT -> {
                enemies.add(new Bat(0, x, y));
                break;
            }

            case KNIGHT -> {
                enemies.add(new Knight(0, x, y));
                break;
            }

            case WOLF -> {
                enemies.add(new Wolf(0, x, y));
                break;
            }
        }
    }

    public void update() {
        for(Enemy e : enemies) {
            //pathfinding: given enemy pos and direction, is next tile a road tile? if yes, move there
            updateEnemyMovement(e);
        }
    }

    private void updateEnemyMovement(Enemy e) {
        if(e.getLastDirection() == -1) {
            setNewDirectionAndMove(e);
        }

        int newX = (int)(e.getX() + getSpeedX(e.getLastDirection(), e.getEnemyTipe()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDirection(), e.getEnemyTipe()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            e.move(getSpeed(e.getEnemyTipe()), e.getLastDirection());
        } else if(isAtEnd(e)) {
            //e.kill(), takeOneLife()
            System.out.println("Life lost");
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int direction = e.getLastDirection();

        int xCord = (int)(e.getX()/32);
        int yCord = (int)(e.getY()/32);
        fixEnemyOffsetTile(e, direction, xCord, yCord);

        if(isAtEnd(e)) return; //no movement if the enemy is at the end point

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int)(e.getY() + getSpeedY(UP, e.getEnemyTipe()));
            if(getTileType((int)e.getX(), newY) == ROAD_TILE) {
                e.move(getSpeed(e.getEnemyTipe()), UP);
            } else {
                e.move(getSpeed(e.getEnemyTipe()), DOWN);
            }
        }

        if(direction == UP || direction == DOWN || direction == -1) {
            int newX = (int)(e.getX() + getSpeedX(RIGHT, e.getEnemyTipe()));
            if(getTileType(newX, (int)e.getY()) == ROAD_TILE) {
                e.move(getSpeed(e.getEnemyTipe()), RIGHT);
            } else {
                e.move(getSpeed(e.getEnemyTipe()), LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int direction, int xCord, int yCord) {
        switch(direction) {
            case RIGHT -> {
                if(xCord < 19) {
                    xCord++;
                }
                break;
            }

            case DOWN -> {
                if(yCord < 19) {
                    yCord++;
                }
                break;
            }

            default -> {
                break;
            }
        }

        e.setPosition(xCord*32, yCord*32);
    }

    private boolean isAtEnd(Enemy e) {
        //if enemy pos = end return true
        if(e.getX() == end.getxCord() && e.getY() == end.getyCord()) {
            return true;
        }
        return false;
    }

    private float getSpeedX(int direction, int enemyType) {
        if(direction == LEFT) {
            return -getSpeed(enemyType);
        } else if(direction == RIGHT) {
            return getSpeed(enemyType) + 32;
        }
        return 0;
    }

    private float getSpeedY(int direction, int enemyType) {
        if(direction == UP) {
            return -getSpeed(enemyType);
        } else if(direction == DOWN) {
            return getSpeed(enemyType) + 32;
        }
        return 0;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    public void draw(Graphics g) {
        for(Enemy e : enemies) {
            drawEnemy(e, g);
        }
    }

    private void drawEnemy(Enemy enemy, Graphics g) {
        g.drawImage(enemyImages[enemy.getEnemyTipe()], (int)enemy.getX(), (int)enemy.getY(), null);
    }
}
