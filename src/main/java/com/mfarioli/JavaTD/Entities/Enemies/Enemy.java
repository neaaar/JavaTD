package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Helpers.Constants;

import java.awt.*;
import static com.mfarioli.JavaTD.Helpers.Constants.Direction.*;
import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.*;

public abstract class Enemy {
    protected int id;
    protected float x, y;

    protected Rectangle bounds;

    protected int health, maxhealth;

    protected int enemyTipe;

    protected int lastDirection;

    protected boolean alive;

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

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnemyTipe() {
        return enemyTipe;
    }

    public void setEnemyTipe(int enemyTipe) {
        this.enemyTipe = enemyTipe;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }

    public boolean isAlive() {
        return alive;
    }

    public Enemy(int id, float x, float y, int enemyTipe) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle((int)x, (int)y, 32, 32);
        this.enemyTipe = enemyTipe;
        lastDirection = -1; //starting direction
        setStartingHealth();
        alive = true;
    }

    private void setStartingHealth() {
        health = Constants.EnemyTypes.getStartingHealth(enemyTipe);
        maxhealth = health;
    }

    public float getHealthBarFloat() {
        return (float)health/maxhealth;
    }

    public void hurt(int damage) {
        this.health -= damage;
        if(health <= 0) alive = false;
    }

    public void move(float speed, int direction) {
        switch(direction) {
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
    }

    //only use this method for position fix
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
