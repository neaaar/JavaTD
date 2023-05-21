package com.mfarioli.JavaTD.Entities.Allies;

import com.mfarioli.JavaTD.Helpers.Constants;

import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.*;

public class Tower {
    private int id;
    private int x, y, towerType, damage, tier;

    private float range, cooldown;

    private float cooldownTick;
    /*
     * using a counter instead of System.nanotime() because otherwise
     * System.nanotime() would mess things up after a game pause
     */

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

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public int getTier() {
        return tier;
    }

    public Tower(int id, int x, int y, int towerType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.tier = 1;
        this.towerType = towerType;

        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }

    public void update() {
        cooldownTick++;
    }

    public void upgradeTower() {
        this.tier += 1;

        switch (towerType) {
            case ARCHER -> {
                damage += 2;
                cooldown -= 5;
                break;
            }

            case CANNON -> {
                damage += 5;
                cooldown -= 5;
                break;
            }

            case WIZARD -> {
                damage += 0;
                cooldown -= 10;
                break;
            }
        }
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

    public boolean isCooldownOver() {
        return cooldownTick >= cooldown;
    }

    public void resetCooldown() {
        this.cooldownTick = 0;
    }
}
