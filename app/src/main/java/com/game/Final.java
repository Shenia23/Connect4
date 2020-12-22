package com.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Final extends AppCompatActivity {
    private String result;
    private Jugador playerStats;
    ParametrosJuego param=null;

    public MediaPlayer mysongWinorLose;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        result = i.getStringExtra("RESULT");
        param = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");

        playerStats = new Jugador().readSerializable(getApplicationContext());
        if(playerStats==null){
            System.out.println("Not able to load player stats! Creating new class object");
            playerStats = new Jugador();
        }

        if(result!=null && param!=null){
            if(param.darktheme){
                setTheme(R.style.winDark);
            }else{
                setTheme(R.style.winLight);
            }
            if(param.music){
                if(result.equals("Win")){
                    mysongWinorLose= MediaPlayer.create(Final.this,R.raw.win);
                    mysongWinorLose.start();
                }else{
                    mysongWinorLose= MediaPlayer.create(Final.this,R.raw.lose);
                    mysongWinorLose.start();
                }
            }
            setContentView(R.layout.activity_win);
            updateStats();
            setStatText();
        }
    }


    protected void updateStats(){
        switch (result){
            case "Win":
                ((TextView) findViewById(R.id.winText)).setText("You won!");
                playerStats.editStats(param.getDifficulty(),true);
                break;
            case "Lose":
                ((TextView) findViewById(R.id.winText)).setText("You lose!");
                playerStats.editStats(param.getDifficulty(),false);
                break;
            case "Tie":
                ((TextView) findViewById(R.id.winText)).setText("Tie !");

                break;
        }
    }

    protected void setStatText(){
        int wins = playerStats.victorias[param.getDifficulty()];
        int loses = playerStats.derrotas[param.getDifficulty()];

        ((TextView) findViewById(R.id.difficultyText)).setText(param.getDifficultyLabel());
        ((TextView) findViewById(R.id.winStats)).setText("Wins: " + wins);
        ((TextView) findViewById(R.id.loseStats)).setText("Losses: " + loses);

    }



    public void goPlay(View view){
        Button button =  findViewById(R.id.play_another);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    playerStats.saveSerializable(getApplicationContext());
                    /**
                if(param.darktheme){
                    setTheme(R.style.gameDark);
                }else{
                    setTheme(R.style.gameLight);
                }
                    setContentView(R.layout.activity_main_game);
                */
            }
        });

    }

    public void goMenu(View view){
        Button button =  findViewById(R.id.menu);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), MainMenu.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);
                playerStats.saveSerializable(getApplicationContext());
                /**
                if(param.darktheme){
                    setTheme(R.style.mainDark);
                }else{
                    setTheme(R.style.mainLight);
                }
                setContentView(R.layout.activity_main);
                */
            }
        });

    }
}
