package com.game;

import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.game.ai.AgenteDificil;
import com.game.ai.AgenteFacil;
import com.game.ai.AgenteMedio;
import com.game.ai.AiPlayer;
import java.io.Serializable;

public class ParametrosJuego extends AppCompatActivity implements Serializable {
    boolean music =false;
    boolean sounds =false;
    boolean timeEnable =false;
    boolean darktheme =false;
    int selFicha =3;
    int gameDifficulty =0;
    private boolean isFirstTurn = false;

    public ParametrosJuego(){
         music =false;
         sounds =false;
         timeEnable =false;
         darktheme =false;
         selFicha =0;
         gameDifficulty =0;
    }

    public int getDifficulty() {
        return gameDifficulty;
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
        gameDifficulty=1;
    }
    public void selMedium(){
        gameDifficulty=2;
    }
    public void selHard(){
        gameDifficulty=3;
    }
    public void selImpossible(){
        gameDifficulty=4;
    }
    public void seleccionFicha(int n){
        switch (n){
            case 0:
                selFicha=0;
                break;
            case 1:
                selFicha=1;
                break;
            case 2:
                selFicha=2;
                break;
            case 3:
                selFicha=3;
                break;
        }
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
                return new AgenteMedio();
            case 2:
                return new AgenteDificil();
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
