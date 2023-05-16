package com.mfarioli.JavaTD.Entities.Enemies;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.BAT;

public class Bat extends Enemy {
    public Bat(int id, float x, float y) {
        super(id, x, y, BAT);
        setStartingHealth();
    }
}
