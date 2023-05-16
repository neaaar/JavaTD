package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Entities.Allies.Tower;
import com.mfarioli.JavaTD.Helpers.LoadSave;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.mfarioli.JavaTD.Helpers.Constants.TowerTypes.*;

public class TowerHandler {
    private Playing playing;

    private BufferedImage[] towerImages;

    private ArrayList<Tower> towers;

    private int towerAmount;


    public TowerHandler(Playing playing) {
        this.playing = playing;
        towers = new ArrayList<>();
        towerAmount = 0;
        loadTowerImages();
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];

        for(int i = 0; i < 3; i++) {
            towerImages[i] = atlas.getSubimage((4 + i)*32, 1*32, 32, 32);
        }
    }

    public BufferedImage[] getTowerImages() {
        return towerImages;
    }

    public void addTower(Tower selectedTower, int xCord, int yCord) {
        towers.add(new Tower(towerAmount++, xCord, yCord, selectedTower.getTowerType()));
    }

    public Tower checkTowerAt(int x, int y) {
        for(Tower t : towers) {
            if(t.getX() == x && t.getY() == y) {
                return t;
            }
        }

        return null;
    }

    public void draw(Graphics g) {
        for(Tower t : towers) {
            g.drawImage(towerImages[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }

    public void update() {

    }


}
