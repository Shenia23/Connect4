package com.game;

import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.game.ai.AgenteDificil;
import com.game.ai.AgenteFacil;
import com.game.ai.MinMax;
import com.game.ai.AiPlayer;
import java.io.Serializable;

public class ParametrosJuego extends AppCompatActivity implements Serializable {
    boolean music =false;
    boolean sounds =false;
    boolean timeEnable =false;
    boolean darktheme =false;
    int selFicha =2;
    int gameDifficulty =0;
    private boolean isFirstTurn = false;

    private static final String[] difficultyLabels = {"EASY", "MEDIUM", "HARD"};

    private int aiColor = R.color.textPurple;
    private int playerColor = R.color.fichaamarilla;

    private static final int MINMAX_MEDIUM_DEPTH = 5;
    private static final int MINMAX_HARD_DEPTH = 8;

    public ParametrosJuego(){
        music =false;
        sounds =false;
        timeEnable =false;
        darktheme =false;
        selFicha =2;
        gameDifficulty =0;
    }

    public int getDifficulty() {
        return gameDifficulty;
    }

    public String getDifficultyLabel(){
        return difficultyLabels[gameDifficulty];
    }


    public int getAiColor(){
        return aiColor;
    }

    public int getPlayerColor(){
        return playerColor;
    }



    public void enMusic(){
        music=true;
    }
    public void disMusic(){
        music=false;
    }
    public void enSounds(){
        sounds=true;
    }
    public void disSounds(){
        sounds=false;
    }
    public void enTime(){
        timeEnable=true;
    }
    public void disTime(){
        timeEnable=false;
    }
    public void lightTheme(){
        darktheme=false;
    }
    public void darkTheme(){
        darktheme=true;
    }
    public void selEasy(){
        gameDifficulty=0;
    }
    public void selMedium(){
        gameDifficulty=1;
    }
    public void selHard(){
        gameDifficulty=2;
    }

    public void seleccionFicha(int n){
        switch (n){
            case 0:
                playerColor = R.color.fichaverde;
                break;
            case 1:
                playerColor = R.color.ficharoja;
                break;
            case 2:
                playerColor = R.color.colorOrange;
                break;
            case 3:
                playerColor = R.color.fichaoscura;
                break;
        }
        selFicha = n;
    }

    /**
     *
     * @return class AI dependiendo de la dificultad
     * Todas las clases pertenecen a la interfaz AiPlayer, y usarán los mismos métodos para que sea transparente a la lógica de juego.
     */
    public AiPlayer getAiPlayer(){
        switch(gameDifficulty){
            case 0:
                return new AgenteFacil();
            case 1:
                return new MinMax(MINMAX_MEDIUM_DEPTH);
            case 2:
                return new MinMax(MINMAX_HARD_DEPTH);
        }
        return null;
    }

    /**
     *
     * @return true si empieza el jugador, false si el ai, dependiendo de la dificultad
     */
    public boolean getFirstTurn(){

        switch(gameDifficulty){
            case 0:
                return true;
            case 1:
                return Math.random() < 0.5;
            case 2:
                return false;
            default:
                return true;
        }
    }
}