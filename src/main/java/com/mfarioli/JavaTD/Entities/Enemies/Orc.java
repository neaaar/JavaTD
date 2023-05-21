package com.mfarioli.JavaTD.Entities.Enemies;

import com.mfarioli.JavaTD.Handlers.EnemyHandler;

import static com.mfarioli.JavaTD.Helpers.Constants.EnemyTypes.*;

public class Orc extends Enemy {

    public Orc(int id, float x, float y, EnemyHandler enemyHandler) {
        super(id, x, y, ORC, enemyHandler);
    }
}
