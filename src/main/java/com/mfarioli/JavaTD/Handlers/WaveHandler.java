package com.mfarioli.JavaTD.Handlers;

import com.mfarioli.JavaTD.Events.Wave;
import com.mfarioli.JavaTD.Scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveHandler {
    private Playing playing;

    private ArrayList<Wave> waves;

    private int enemySpawnTick, enemySpawnTickLimit;

    private int enemyIndex, waveIndex;

    private int waveTick, waveTickLimit;

    private boolean waveStartTimer, waveTickTimerOver;

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public WaveHandler(Playing playing) {
        this.playing = playing;
        enemySpawnTickLimit = 60 * 2; //a new enemy every 2 seconds, can be changed
        enemySpawnTick = enemySpawnTickLimit; //spawns an enemy right away
        waveTickLimit = 60 * 15; //15 seconds between waves
        waveTick = 0;
        this.waves = new ArrayList<>();
        createWaves();
    }

    /* For waves:
        Orc = 0
        Bat = 1
        Knight = 2
        Wolf = 3
     */
    private void createWaves() {
        waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 1, 0, 1, 0, 1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(3, 2, 1, 0, 1, 3, 2, 1, 0, 1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(2, 3, 3, 1, 1, 3, 3, 2, 0, 0))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 2, 3, 1, 1, 1, 2, 3))));
    }

    public float getTimeLeft() {
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft / 60.0f; //since there are 60 updates in a second, ticks / 60 returns seconds left
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyTypes().get(enemyIndex++);
    }

    public boolean isThereMoreEnemyInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyTypes().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void increaseWaveIndex() {
        waveIndex++;
        waveTick = 0;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public void update() {
        if(enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
        }

        if(waveStartTimer) {
            waveTick++;
            if(waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }
        }
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
}
