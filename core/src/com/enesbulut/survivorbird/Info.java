package com.enesbulut.survivorbird;

public class Info {


    static private int gameState;
    static private int score;
    static private int bestScore;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Info.score = score;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void setBestScore(int bestScore) {
        Info.bestScore = bestScore;
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int gameState) {
        Info.gameState = gameState;
    }
}
