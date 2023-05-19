package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Entities.Enemies.Enemy;
import com.mfarioli.JavaTD.Helpers.Constants;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Objects.Projectile;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.Projectiles.*;
import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.*;

public class ProjectileHandler {
    private Playing playing;

    private ArrayList<Projectile> projectiles;

    private ArrayList<Explosion> explosions;

    private int projectilesId;

    private BufferedImage[] projectileImages, explosionImages;

    public ProjectileHandler(Playing playing) {
        this.playing = playing;
        projectiles = new ArrayList<>();
        explosions = new ArrayList<>();
        projectilesId = 0;
        projectileImages = new BufferedImage[3];
        explosionImages = new BufferedImage[7];
        loadProjectileImages();
    }

    private void loadProjectileImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i = 0; i < projectileImages.length; i++) {
            projectileImages[i] = atlas.getSubimage((7 + i)*32, 1*32, 32, 32);
        }

        loadExplosionImages(atlas);
    }

    private void loadExplosionImages(BufferedImage atlas) {
        for(int i = 0; i < explosionImages.length; i++) {
            explosionImages[i] = atlas.getSubimage(i*32, 2*32, 32, 32);
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

        float rotation = 0;

        if(projectileType == ARROW) {
            //if the projectile is an arrow, calculates the value for the rotation
            float arcValue = (float) Math.atan((float) yDist / xDist);
            rotation = (float) Math.toDegrees(arcValue);
        }

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

    private boolean isProjectileHittingEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyHandler().getEnemies()) {
            if(e.getBounds().contains(p.getPosition())) {
                e.hurt(p.getDamage());
                return true;
            }
        }
        return false;
    }

    //using an inner class since we don't need it anywhere else
    public class Explosion {
        Point2D.Float position;

        private int explosionTick, explosionIndex;

        public Point2D.Float getPosition() {
            return position;
        }

        public int getExplosionIndex() {
            return explosionIndex;
        }
        public Explosion(Point2D.Float position) {
            this.position = position;
        }

        public void update() {
            explosionTick++;
            if(explosionTick >= 12) {
                explosionTick = 0;
                explosionIndex++;
            }
        }
    }

    private void explodeOnEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyHandler().getEnemies()) {
            if(!e.isAlive()) continue; //if enemy isn't alive, continue
            float radius = 40.0f;

            float xDist = Math.abs(p.getPosition().x - e.getX());
            float yDist = Math.abs(p.getPosition().y - e.getY());

            float hypot = (float) Math.hypot(xDist, yDist);

            if(!(hypot <= radius)) continue;
            e.hurt(p.getDamage());
        }
    }

    public void update() {
        for(Projectile p : projectiles) {
            if(!p.isActive()) continue; //if current projectile isn't active continue to next projectile

            //projectiles can move before hitting an enemy
            p.move();

            if(!isProjectileHittingEnemy(p)) continue; //if after moving the projectile doesn't hit an enemy continue
            p.setActive(false); //if current projectile hit an enemy, disable it
            if(p.getProjectileType() == BOMB) {
                //if the projectile that just hit an enemy was a bomb, we need to draw the explosion
                //we can't draw it in the update method, so we just set a boolean to true and draw it in the draw method
                explosions.add(new Explosion(p.getPosition()));
                explodeOnEnemy(p);
            } else {
                //do nothing
            }
        }

        for(Explosion e : explosions) {
            if(!(e.getExplosionIndex() < 7)) continue;
            e.update();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(Projectile p : projectiles) {
            if(!p.isActive()) continue;

            if(p.getProjectileType() == ARROW) {
                //if the projectile is an arrow:
                //sets the center of the rotation on the projectile, then rotates and draws it
                g2d.translate(p.getPosition().x, p.getPosition().y);
                g2d.rotate(Math.toRadians(p.getRotation()));

                //x and y parameters are set to -16 because the centre is set on the projectile
                g2d.drawImage(projectileImages[p.getProjectileType()], -16, -16, null);

                //now we need to backtrace to get back to the values we had before translating/rotating
                g2d.rotate(Math.toRadians(-p.getRotation()));
                g2d.translate(-p.getPosition().x, -p.getPosition().y);
            } else if(p.getProjectileType() == BOMB){
                //if the projectile isn't an arrow, just draw it normally
                g2d.drawImage(projectileImages[p.getProjectileType()], (int)p.getPosition().x -32, (int)p.getPosition().y -16, null);
                //x is p.getPosition() -32 so the bomb spawns from the opening of the cannon
            } else if(p.getProjectileType() == CHAINS){
                //if the projectile isn't a bomb, just draw it normally
                g2d.drawImage(projectileImages[p.getProjectileType()], (int)p.getPosition().x -16, (int)p.getPosition().y -16, null);
            }

            //draw explosions
            for(Explosion e : explosions) {
                if(e.getExplosionIndex() < 7) {
                    g2d.drawImage(explosionImages[e.getExplosionIndex()], (int)e.getPosition().x -16, (int)e.getPosition().y -16, null);
                }
            }
        }
    }
}
