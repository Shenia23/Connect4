package com.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.IOException;
import java.io.Serializable;


public class Settings extends AppCompatActivity {
    ParametrosJuego paramJug = new ParametrosJuego();
    ParametrosJuego paramJug2 = new ParametrosJuego();
    String previousActivity="";
    MediaPlayer mp;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        mp=MediaPlayer.create(this,R.raw.click);
        paramJug = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");
        paramJug2.music=paramJug.music;
        paramJug2.sounds=paramJug.sounds;
        paramJug2.timeEnable=paramJug.timeEnable;
        paramJug2.darktheme=paramJug.darktheme;
        paramJug2.selFicha=paramJug.selFicha;
        paramJug2.gameDifficulty=paramJug.gameDifficulty;
        previousActivity= i.getStringExtra("PREVIOUS_ACTIVITY");
        if(paramJug==null){
            paramJug=new ParametrosJuego();
        }else{
            if(paramJug.darktheme){
                setTheme(R.style.settingsDark);
            }else{
                setTheme(R.style.settingsLight);
            }
            setContentView(R.layout.settings);

                ((RadioButton) findViewById(R.id.MusicOn)).setChecked(paramJug.music);
                ((RadioButton) findViewById(R.id.MusicOff)).setActivated(!paramJug.music);

                ((RadioButton) findViewById(R.id.SEon)).setChecked(paramJug.sounds);
                ((RadioButton) findViewById(R.id.SEoff)).setChecked(!paramJug.sounds);

                ((RadioButton) findViewById(R.id.Ton)).setChecked(paramJug.timeEnable);
                ((RadioButton) findViewById(R.id.Toff)).setChecked(!paramJug.timeEnable);

        }
    }

    



    public void goMainfromSettingsSave(View view){
        Button button =  findViewById(R.id.bsave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){mp.start();}
                if(previousActivity.equals("MainMenu")) {
                    Intent gameIntent = new Intent(getApplicationContext(), MainMenu.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug2);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    if(paramJug.darktheme){
                        setTheme(R.style.mainDark);
                    }else{
                        setTheme(R.style.mainLight);
                    }
                    setContentView(R.layout.activity_main);
                }else{
                    Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug2);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    if(paramJug.darktheme){
                        setTheme(R.style.gameDark);
                    }else{
                        setTheme(R.style.gameLight);
                    }
                    setContentView(R.layout.activity_main_game);

                }
            }
        });

    }
    public void goMainfromSettingsBack(View view){
        Button button =  findViewById(R.id.bback);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){mp.start();}
                if(previousActivity.equals("MainMenu")) {
                    Intent gameIntent = new Intent(getApplicationContext(), MainMenu.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    if(paramJug.darktheme){
                        setTheme(R.style.mainDark);
                    }else{
                        setTheme(R.style.mainLight);
                    }
                    setContentView(R.layout.activity_main);
                }else{
                    Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                    gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug);
                    getIntent().getSerializableExtra("ParametrosJuego");
                    startActivity(gameIntent);
                    if(paramJug.darktheme){
                        setTheme(R.style.gameDark);
                    }else{
                        setTheme(R.style.gameLight);
                    }
                    setContentView(R.layout.activity_main_game);
                }
            }
        });
    }


    public void onRadioButtonMusicClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.MusicOff:
                if (checked)
                    paramJug2.disMusic();
                break;
            case R.id.MusicOn:
                if (checked)
                    paramJug2.enMusic();

                break;

        }

    }
    public void onRadioButtonSoundsClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.SEoff:
                if (checked)
                    paramJug2.disSounds();
                break;
            case R.id.SEon:
                if (checked)
                    paramJug2.enSounds();
                break;

        }

    }
    public void onRadioButtonTimeClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Toff:
                if (checked)
                    paramJug2.disTime();
                break;
            case R.id.Ton:
                if (checked)
                    paramJug2.enTime();
                break;

        }

    }


}
