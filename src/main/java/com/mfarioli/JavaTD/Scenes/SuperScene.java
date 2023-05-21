package com.mfarioli.JavaTD.Scenes;

import com.mfarioli.JavaTD.Game;

public class SuperScene {
    public Game game;

    public SuperScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
