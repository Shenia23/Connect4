package com.game;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.content.Intent;

import java.io.Serializable;

import android.media.MediaPlayer;
public class MainMenu extends AppCompatActivity {
    ParametrosJuego paramJug= null;
    MediaPlayer mp;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
         paramJug = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");
        if(paramJug==null){
            paramJug=new ParametrosJuego();
            setTheme(R.style.mainLight);
            setContentView(R.layout.activity_main);
            mp=MediaPlayer.create(this,R.raw.click);
        }else{
            if(paramJug.darktheme){
                setTheme(R.style.mainDark);
            }else{
                setTheme(R.style.mainLight);
            }
            mp=MediaPlayer.create(this,R.raw.click);
            setContentView(R.layout.activity_main);
            inicializarMenu();

        }
    }

    public void goGame(View view) {
        Button buttonGame = findViewById(R.id.bSearch);
        buttonGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){ mp.start();}
                //setContentView(R.layout.activity_main_game);
                Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug);
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);

            }
        });
    }

    public void goSettings(View view){
        ImageButton button =  findViewById(R.id.settings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){ mp.start();}
                Intent gameIntent = new Intent(getApplicationContext(), Settings.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) paramJug);
                gameIntent.putExtra("PREVIOUS_ACTIVITY", (String)"MainMenu");
                getIntent().getStringExtra("PREVIOUS_ACTIVITY");
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);

            }
        });
    }

    public void showPopupWindowInfo(View view) {
        ImageButton button =  findViewById(R.id.info);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){ mp.start();}
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popupinfoautor, null);
                final PopupWindow popupInfo=new PopupWindow(popupView,800,400,true);
                popupInfo.showAtLocation(popupView, Gravity.CENTER, 0, 40);;
            }
        });
        }

    public void showPopupWindowRules(View view) {
        ImageButton button =  findViewById(R.id.rules);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(paramJug.sounds){ mp.start();}
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popuprules, null);
                final PopupWindow popupInfo=new PopupWindow(popupView,800,400,true);
                popupInfo.showAtLocation(popupView, Gravity.CENTER, 0, 40);;
            }
        });
    }
    public void onRadioButtonDifClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Easy:
                if (checked)
                    paramJug.selEasy();
                    break;
            case R.id.Medium:
                if (checked)
                    paramJug.selMedium();
                    break;
            case R.id.Hard:
                if (checked)
                    paramJug.selHard();
                    break;
        }
    }
    public void onRadioButtonColorClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton fichaverde_imagen = (RadioButton) findViewById(R.id.fichaver);
        RadioButton ficharoja_imagen = (RadioButton) findViewById(R.id.ficharoj);
        RadioButton fichaamarilla_imagen = (RadioButton) findViewById(R.id.fichaama);
        RadioButton fichavioleta_imagen = (RadioButton) findViewById(R.id.fichavio);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.fichaver:
                if (checked) {
                    paramJug.seleccionFicha(0);
                    fichaverde_imagen.setBackgroundResource(R.drawable.green_selected);
                    ficharoja_imagen.setBackgroundResource(R.drawable.red);
                    fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                    fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                }
                break;
            case R.id.ficharoj:
                if (checked) {
                    paramJug.seleccionFicha(1);
                    fichaverde_imagen.setBackgroundResource(R.drawable.green);
                    ficharoja_imagen.setBackgroundResource(R.drawable.red_selected);
                    fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                    fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                }
                break;
            case R.id.fichaama:
                if (checked) {
                    paramJug.seleccionFicha(2);
                    fichaverde_imagen.setBackgroundResource(R.drawable.green);
                    ficharoja_imagen.setBackgroundResource(R.drawable.red);
                    fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow_selected);
                    fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                }
                break;
            case R.id.fichavio:
                if (checked) {
                    paramJug.seleccionFicha(3);
                    fichaverde_imagen.setBackgroundResource(R.drawable.green);
                    ficharoja_imagen.setBackgroundResource(R.drawable.red);
                    fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                    fichavioleta_imagen.setBackgroundResource(R.drawable.violet_selected);
                }
                break;
        }
    }
    public void onRadioButtonThemeClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Darktheme:
                if (checked)
                    paramJug.darkTheme();
                    setTheme(R.style.mainDark);
                setContentView(R.layout.activity_main);
                inicializarMenu();
                break;
            case R.id.LightTheme:
                if (checked)
                    paramJug.lightTheme();
                    setTheme(R.style.mainLight);
                setContentView(R.layout.activity_main);
                inicializarMenu();
                break;

        }

    }

    public void inicializarMenu(){
        switch(paramJug.gameDifficulty){
            case 0:
                ((RadioButton) findViewById(R.id.Easy)).setChecked(true);
                ((RadioButton) findViewById(R.id.Medium)).setChecked(false);
                ((RadioButton) findViewById(R.id.Hard)).setChecked(false);
                break;

            case 1:
                ((RadioButton) findViewById(R.id.Easy)).setChecked(false);
                ((RadioButton) findViewById(R.id.Medium)).setChecked(true);
                ((RadioButton) findViewById(R.id.Hard)).setChecked(false);
                break;

            case 2:
                ((RadioButton) findViewById(R.id.Easy)).setChecked(false);
                ((RadioButton) findViewById(R.id.Medium)).setChecked(false);
                ((RadioButton) findViewById(R.id.Hard)).setChecked(true);
                break;

        }

        RadioButton fichaverde_imagen = (RadioButton) findViewById(R.id.fichaver);
        RadioButton ficharoja_imagen = (RadioButton) findViewById(R.id.ficharoj);
        RadioButton fichaamarilla_imagen = (RadioButton) findViewById(R.id.fichaama);
        RadioButton fichavioleta_imagen = (RadioButton) findViewById(R.id.fichavio);

        switch(paramJug.selFicha) {
            case 0:
                fichaverde_imagen.setBackgroundResource(R.drawable.green_selected);
                ficharoja_imagen.setBackgroundResource(R.drawable.red);
                fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                break;
            case 1:
                fichaverde_imagen.setBackgroundResource(R.drawable.green);
                ficharoja_imagen.setBackgroundResource(R.drawable.red_selected);
                fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                break;
            case 2:
                fichaverde_imagen.setBackgroundResource(R.drawable.green);
                ficharoja_imagen.setBackgroundResource(R.drawable.red);
                fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow_selected);
                fichavioleta_imagen.setBackgroundResource(R.drawable.violet);
                break;
            case 3:
                fichaverde_imagen.setBackgroundResource(R.drawable.green);
                ficharoja_imagen.setBackgroundResource(R.drawable.red);
                fichaamarilla_imagen.setBackgroundResource(R.drawable.yellow);
                fichavioleta_imagen.setBackgroundResource(R.drawable.violet_selected);
                break;
        }

        ((RadioButton) findViewById(R.id.LightTheme)).setChecked(!paramJug.darktheme);
        ((RadioButton) findViewById(R.id.Darktheme)).setChecked(paramJug.darktheme);
    }


}
