package com.mfarioli.JavaTD.Entities.Allies;

public class Tower {
    private int id;
    private int x, y, towerType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public Tower(int id, int x, int y, int towerType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.towerType = towerType;
    }
}
