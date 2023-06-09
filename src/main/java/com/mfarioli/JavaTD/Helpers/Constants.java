package com.mfarioli.JavaTD.Helpers;

public class Constants {
    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

    public static class EnemyTypes {
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case ORC -> {
                    return 0.5f;
                }

                case BAT -> {
                    return 0.65f;
                }

                case KNIGHT -> {
                    return 0.3f;
                }

                case WOLF -> {
                    return 0.75f;
                }
            }

            return 0;
        }

        public static int getStartingHealth(int enemyType) {
            switch (enemyType) {
                case ORC -> {
                    return 100;
                }

                case BAT -> {
                    return 60;
                }

                case KNIGHT -> {
                    return 250;
                }

                case WOLF -> {
                    return 75;
                }
            }

            return 0;
        }

        public static int getGoldReward(int enemyType) {
            switch (enemyType) {
                case ORC -> {
                    return 10;
                }

                case BAT -> {
                    return 5;
                }

                case KNIGHT -> {
                    return 25;
                }

                case WOLF -> {
                    return 15;
                }
            }

            return 0;
        }
    }

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class TowerTypes {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static String getTowerName(int towerType) {
            switch (towerType) {
                case CANNON -> {
                    return "Cannon";
                }

                case ARCHER -> {
                    return "Archer";
                }

                case WIZARD -> {
                    return "Wizard";
                }
            }

            return "";
        }

        public static int getTowerCost(int towerType) {
            switch (towerType) {
                case CANNON -> {
                    return 70;
                }

                case ARCHER -> {
                    return 30;
                }

                case WIZARD -> {
                    return 45;
                }
            }

            return 0;
        }

        public static int getStartingDamage(int towerType) {
            switch (towerType) {
                case CANNON -> {
                    return 15;
                }

                case ARCHER -> {
                    return 8;
                }

                case WIZARD -> {
                    return 0;
                }
            }

            return 0;
        }

        public static float getDefaultRange(int towerType) {
            switch (towerType) {
                case CANNON -> {
                    return 100;
                }

                case ARCHER -> {
                    return 100;
                }

                case WIZARD -> {
                    return 100;
                }
            }

            return 0;
        }

        public static float getDefaultCooldown(int towerType) {
            switch (towerType) {
                case CANNON -> {
                    return 120;
                }

                case ARCHER -> {
                    return 25;
                }

                case WIZARD -> {
                    return 40;
                }
            }

            return 0;
        }
    }

    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int BOMB = 2;

        public static float getSpeed(int projectileType) {
            switch (projectileType) {
                case ARROW -> {
                    return 8f;
                }

                case CHAINS -> {
                    return 6f;
                }

                case BOMB -> {
                    return 1.25f;
                }
            }

            return 0;
        }
    }
}
