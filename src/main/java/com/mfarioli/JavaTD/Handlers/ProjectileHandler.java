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
        int xDist = (int)(t.getX() - e.getX());
        int yDist = (int)(t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPercent = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPercent*Constants.Projectiles.getSpeed(projectileType);
        float ySpeed = Constants.Projectiles.getSpeed(projectileType) - xSpeed;

        if(t.getX() > e.getX()) {
            xSpeed *= -1;
        }

        if(t.getY() > e.getY()) {
            ySpeed *= -1;
        }

        //calculating the value for the rotation
        float arcValue = (float) Math.atan((float)yDist / xDist);
        float rotation = (float) Math.toDegrees(arcValue);

        if(xDist < 0) {
            rotation += 180;
        }

        projectiles.add(new Projectile(projectilesId++, t.getX() + 16, t.getY() + 16, t.getDamage(), rotation, xSpeed, ySpeed, projectileType));
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
        Graphics2D g2d = (Graphics2D) g;

        for(Projectile p : projectiles) {
            if(!p.isActive()) continue;

            //sets the center of the rotation on the projectile, then rotates and draws it
            g2d.translate(p.getPosition().x, p.getPosition().y);
            g2d.rotate(Math.toRadians(p.getRotation()));
            //x and y parameters are set to -16 because the centre is set on the projectile
            g2d.drawImage(projectileImages[p.getProjectileType()], -16, -16, null);

            //now we need to backtrace to get back to the values we had before translating/rotating
            g2d.rotate(Math.toRadians(-p.getRotation()));
            g2d.translate(-p.getPosition().x, -p.getPosition().y);
        }
    }
}
