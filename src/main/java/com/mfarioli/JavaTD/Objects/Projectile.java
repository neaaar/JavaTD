package com.mfarioli.JavaTD.Objects;

import java.awt.geom.Point2D;

public class Projectile {
    private int id;

    private Point2D.Float position;

    private float rotation;

    private float xSpeed, ySpeed;

    private int projectileType, damage;

    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public float getRotation() {
        return rotation;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Projectile(int id, float x, float y, int damage, float rotation, float xSpeed, float ySpeed, int projectileType) {
        this.id = id;
        position = new Point2D.Float(x, y);
        this.damage = damage;
        this.rotation = rotation;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.projectileType = projectileType;
        this.active = true;
    }

    public void move() {
        position.x += xSpeed;
        position.y += ySpeed;
    }
}
