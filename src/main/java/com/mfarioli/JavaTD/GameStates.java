package com.mfarioli.JavaTD;

public enum GameStates {
    MENU, SETTINGS, PLAYING, GAME_OVER;

    public static GameStates gameState = MENU;

    public static void setGameState(GameStates state) {
        GameStates.gameState = state;
    }
}
