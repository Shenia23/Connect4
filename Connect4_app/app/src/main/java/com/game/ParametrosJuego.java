package com.game;

public class ParametrosJuego {
    boolean music=true;
    boolean sounds=true;
    boolean timeEnable=true;
    boolean darktheme=true;
    int selFicha=0;
    int gameDifficulty=0;

    public ParametrosJuego(){

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
}
