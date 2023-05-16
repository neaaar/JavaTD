package com.mfarioli.JavaTD.Entities.Allies;

import com.mfarioli.JavaTD.Helpers.Constants;

public class Tower {
    private int id;
    private int x, y, towerType;

    private float damage, range, cooldown;

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

    public float getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public Tower(int id, int x, int y, int towerType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.towerType = towerType;

        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    private void setDefaultDamage() {
        damage = Constants.TowerTypes.getStartingDamage(towerType);
    }

    private void setDefaultRange() {
        range = Constants.TowerTypes.getDefaultRange(towerType);
    }

    private void setDefaultCooldown() {
        cooldown = Constants.TowerTypes.getDefaultCooldown(towerType);
    }
}
