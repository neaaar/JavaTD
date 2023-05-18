package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Entities.Enemies.Enemy;
import com.mfarioli.JavaTD.Helpers.Constants;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Objects.Projectile;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.Projectiles.*;
import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.*;

public class ProjectileHandler {
    private Playing playing;

    private ArrayList<Projectile> projectiles;

    private int projectilesId;

    private BufferedImage[] projectileImages;

    public ProjectileHandler(Playing playing) {
        this.playing = playing;
        projectiles = new ArrayList<>();
        projectilesId = 0;
        projectileImages = new BufferedImage[3];
        loadProjectileImages();
    }

    private void loadProjectileImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i = 0; i < projectileImages.length; i++) {
            projectileImages[i] = atlas.getSubimage((7 + i)*32, 1*32, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        int projectileType = getProjectileType(t);
        int xDist = Math.abs((int)(t.getX() - e.getX()));
        int yDist = Math.abs((int)(t.getY() - e.getY()));
        int totDist = xDist + yDist;

        float xPercent = (float)xDist / totDist;

        float xSpeed = xPercent*Constants.Projectiles.getSpeed(projectileType);
        float ySpeed = (1.0f - xPercent)*Constants.Projectiles.getSpeed(projectileType);

        if(t.getX() > e.getX()) {
            xSpeed *= -1;
        }

        if(t.getY() > e.getY()) {
            ySpeed *= -1;
        }

        projectiles.add(new Projectile(projectilesId++, t.getX() + 16, t.getY() + 16, t.getDamage(), xSpeed, ySpeed, projectileType));
    }

    private int getProjectileType(Tower t) {
        switch(t.getTowerType()) {
            case CANNON -> {
                return BOMB;
            }

            case ARCHER -> {
                return ARROW;
            }

            case WIZARD -> {
                return CHAINS;
            }
        }

        return 0;
    }

    public void update() {
        for(Projectile p : projectiles) {
            if(!p.isActive()) continue; //if current projectile isn't active continue to next projectile

            //projectiles can move before hitting an enemy
            p.move();

            if(!isProjectileHittingEnemy(p)) continue; //if after moving the projectile doesn't hit an enemy continue
            p.setActive(false); //if current projectile hit an enemy, disable it
        }
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyHandler().getEnemies()) {
            if(e.getBounds().contains(p.getPosition())) {
                e.hurt(p.getDamage());
                return true;
            }
        }
        return false;
     }

    public void draw(Graphics g) {
        for(Projectile p : projectiles) {
            if(!p.isActive()) continue;
            g.drawImage(projectileImages[p.getProjectileType()], (int)p.getPosition().x, (int)p.getPosition().y, null);
        }
    }
}
