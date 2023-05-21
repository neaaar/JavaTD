package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Handlers.EnemyHandler;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.BAT;

public class Bat extends Enemy {
    public Bat(int id, float x, float y, EnemyHandler enemyHandler) {
        super(id, x, y, BAT, enemyHandler);
    }
}
