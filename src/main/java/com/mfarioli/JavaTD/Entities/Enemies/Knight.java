package com.mfarioli.JavaTD.Entities.Enemies;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.KNIGHT;

public class Knight extends Enemy {
    public Knight(int id, float x, float y) {
        super(id, x, y, KNIGHT);
        setStartingHealth();
    }
}
