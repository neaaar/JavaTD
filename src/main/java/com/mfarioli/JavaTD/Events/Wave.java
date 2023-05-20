package com.mfarioli.JavaTD.Events;

import java.util.ArrayList;

public class Wave {
    ArrayList<Integer> enemyTypes;

    public ArrayList<Integer> getEnemyTypes() {
        return enemyTypes;
    }

    public Wave(ArrayList<Integer> enemyTypes) {
        this.enemyTypes = enemyTypes;
    }
}
