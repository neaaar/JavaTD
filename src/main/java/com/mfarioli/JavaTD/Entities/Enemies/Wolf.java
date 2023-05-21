package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Handlers.EnemyHandler;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.WOLF;

public class Wolf extends Enemy {
    public Wolf(int id, float x, float y, EnemyHandler enemyHandler) {
        super(id, x, y, WOLF, enemyHandler);
    }
}
