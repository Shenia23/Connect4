package com.game;

public class GameRules {
    private boolean isFirstTurn;
    private int playerColor;
    private int difficulty;

    public GameRules(){
        playerColor = R.color.colorOrange;
        difficulty = 0;
        isFirstTurn = decideFirstTurn(difficulty);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean getFirstTurn(){
        return isFirstTurn;
    }

    public int getPlayerColor(){
        return playerColor;
    }

    private boolean decideFirstTurn(int difficulty){
        return true;
    }

    public int getAiColor(){
        return playerColor = playerColor == R.color.colorOrange ? R.color.colorOrange : R.color.colorLilac;
    }
}
