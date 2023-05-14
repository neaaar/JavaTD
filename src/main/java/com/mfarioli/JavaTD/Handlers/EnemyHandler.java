package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Entities.Enemies.Enemy;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.Direction.*;
import static com.mfarioli.JavaTD.Helpers.Constants.Tiles.*;

public class EnemyHandler {
    private Playing playing;

    private BufferedImage[] enemyImages;

    private ArrayList<Enemy> enemies;

    private float speed = 0.5f;

    public EnemyHandler(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        loadEnemyImages();

        enemies = new ArrayList<>();
        addEnemy(3*32, 9*32);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = atlas.getSubimage(0*32, 1*32, 32, 32);
        enemyImages[1] = atlas.getSubimage(1*32, 1*32, 32, 32);
        enemyImages[2] = atlas.getSubimage(2*32, 1*32, 32, 32);
        enemyImages[3] = atlas.getSubimage(3*32, 1*32, 32, 32);
    }

    public void addEnemy(int x, int y) {
        enemies.add(new Enemy(0, x, y, 0));
    }

    public void update() {
        for(Enemy e : enemies) {
            //pathfinding: given enemy pos and direction, is next tile a road tile? if yes, move there
            if(isNextTileRoad(e)) {

            }
        }
    }

    private boolean isNextTileRoad(Enemy e) {
        int newX = (int)(e.getX() + getSpeedX(e.getLastDirection()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDirection()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            e.move(speed, e.getLastDirection());
            return true;
        } else if(isAtEnd(e)) {
            return false;
        } else {
            setNewDirectionAndMove(e);
        }

        return false;
    }

    private void setNewDirectionAndMove(Enemy e) {
        int direction = e.getLastDirection();

        int xCord = (int)(e.getX()/32);
        int yCord = (int)(e.getY()/32);
        fixEnemyOffsetTile(e, direction, xCord, yCord);

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int)(e.getY() + getSpeedY(UP));
            if(getTileType((int)e.getX(), newY) == ROAD_TILE) {
                e.move(speed, UP);
            } else {
                e.move(speed, DOWN);
            }
        }

        if(direction == UP || direction == DOWN) {
            int newX = (int)(e.getX() + getSpeedX(RIGHT));
            if(getTileType(newX, (int)e.getY()) == ROAD_TILE) {
                e.move(speed, RIGHT);
            } else {
                e.move(speed, LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int direction, int xCord, int yCord) {
        switch(direction) {
/*            case LEFT -> {
                if(xCord > 0) {
                    xCord--;
                }
                break;
            }

            case UP -> {
                if(yCord > 0) {
                    yCord--;
                }
                break;
            }*/

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
        }

        e.setPosition(xCord*32, yCord*32);
    }

    private boolean isAtEnd(Enemy e) {
        //if enemy pos = end return true

        return false;
    }

    private float getSpeedX(int direction) {
        if(direction == LEFT) {
            return -speed;
        } else if(direction == RIGHT) {
            return speed + 32;
        }
        return 0;
    }

    private float getSpeedY(int direction) {
        if(direction == UP) {
            return -speed;
        } else if(direction == DOWN) {
            return speed + 32;
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
        g.drawImage(enemyImages[enemy.getId()], (int)enemy.getX(), (int)enemy.getY(), null);
    }
}
