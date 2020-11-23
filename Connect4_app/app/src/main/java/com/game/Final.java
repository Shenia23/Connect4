package com.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;

public class Final extends AppCompatActivity {
    String result;
    ParametrosJuego param=new ParametrosJuego();
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        result= i.getStringExtra("RESULT");
        param = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");
        setContentView(R.layout.activity_win);
        if(result==null || param==null){
            result="ERROR";
            ((TextView) findViewById(R.id.winText)).setText("ERROR !");
        }else{
            switch (result){
                case "Win":
                    ((TextView) findViewById(R.id.winText)).setText("You won!");

                    break;
                case "Lose":
                    ((TextView) findViewById(R.id.winText)).setText("You lose!");

                    break;
                case "Tie":
                    ((TextView) findViewById(R.id.winText)).setText("Tie !");

                    break;
            }
        }
    }

    public void goPlay(View view){
        Button button =  findViewById(R.id.play_another);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    setContentView(R.layout.activity_main_game);

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
                setContentView(R.layout.activity_main);

            }
        });

    }
}
