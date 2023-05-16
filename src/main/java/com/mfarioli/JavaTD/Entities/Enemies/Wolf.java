package com.mfarioli.JavaTD.Entities.Enemies;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.WOLF;

public class Wolf extends Enemy {
    public Wolf(int id, float x, float y) {
        super(id, x, y, WOLF);
    }
}
