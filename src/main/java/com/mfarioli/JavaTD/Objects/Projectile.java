package com.mfarioli.JavaTD.Objects;

import java.awt.geom.Point2D;

public class Projectile {
    private int id;

    private Point2D.Float position;

    private int projectileType;

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

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Projectile(int id, float x, float y, int projectileType) {
        this.id = id;
        position = new Point2D.Float(x, y);
        this.projectileType = projectileType;
        this.active = true;
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
    }
}
