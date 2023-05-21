package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Handlers.EnemyHandler;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.KNIGHT;

public class Knight extends Enemy {
    public Knight(int id, float x, float y, EnemyHandler enemyHandler) {
        super(id, x, y, KNIGHT, enemyHandler);
    }
}
