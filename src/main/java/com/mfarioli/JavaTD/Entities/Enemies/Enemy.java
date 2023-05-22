package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Handlers.EnemyHandler;
import com.mfarioli.JavaTD.Helpers.Constants;

import java.awt.*;

import static com.mfarioli.JavaTD.Helpers.Constants.Direction.*;

public abstract class Enemy {
    protected int id;
    protected float x, y;

    protected Rectangle bounds;

    protected int health, maxhealth;

    protected int enemyType;

    protected int lastDirection;

    protected boolean alive;

    protected int slowTick, slowTickLimit;

    protected EnemyHandler enemyHandler;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }

    public Enemy(int id, float x, float y, int enemyType, EnemyHandler enemyHandler) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle((int) x, (int) y, 32, 32);
        this.enemyType = enemyType;
        lastDirection = -1; //starting direction
        setStartingHealth();
        alive = true;
        slowTickLimit = 120;
        slowTick = slowTickLimit;

        this.enemyHandler = enemyHandler;
    }

    private void setStartingHealth() {
        health = Constants.EnemyTypes.getStartingHealth(enemyType);
        maxhealth = health;
    }

    public float getHealthBarFloat() {
        return (float) health / maxhealth;
    }

    public void kill() {
        //this isn't needed if an enemy reaches health < 0 through the hurt() method,
        //but it is needed to kill the enemy as soon as it reaches the end tile
        health = 0;
        alive = false;
    }

    public void hurt(int damage) {
        if(!isAlive()) return;

        this.health -= damage;
        if (health <= 0) {
            alive = false;
            enemyHandler.goldReward(enemyType);
        }
    }

    public void slow() {
        slowTick = 0;
    }

    public void move(float speed, int direction) {
        if(slowTick < slowTickLimit) { //2 seconds of slow since there are 60 updates per second and slowTickLimit = 120
            slowTick++;
            speed *= 0.3;
        }

        switch (direction) {
            case LEFT -> {
                this.x -= speed;
                break;
            }

            case UP -> {
                this.y -= speed;
                break;
            }

            case RIGHT -> {
                this.x += speed;
                break;
            }

            case DOWN -> {
                this.y += speed;
                break;
            }
        }
        lastDirection = direction;
        updateHitbox();
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    //only use this method for position fix
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
