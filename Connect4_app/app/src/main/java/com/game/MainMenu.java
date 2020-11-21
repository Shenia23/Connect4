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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.content.Intent;

public class MainMenu extends AppCompatActivity {
    ParametrosJuego paramJug;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        paramJug=new ParametrosJuego();
        setContentView(R.layout.activity_main);
    }

    public void goGame(View view) {
        Button buttonGame = findViewById(R.id.bSearch);
        buttonGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //setContentView(R.layout.activity_main_game);
                Intent gameIntent = new Intent(getApplicationContext(), MainGame.class);
                startActivity(gameIntent);
            }
        });
    }

    public void goSettings(View view){
        ImageButton button =  findViewById(R.id.settings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.settings);
            }
        });
    }
    public void goMainfromSettingsSave(View view){
        Button button =  findViewById(R.id.bsave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.activity_main);
            }
        });

    }
    public void goMainfromSettingsBack(View view){
        Button button =  findViewById(R.id.bback);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.activity_main);
            }
        });
    }
    public void showPopupWindowInfo(View view) {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupinfoautor, null);
        ImageButton button =  findViewById(R.id.info);
        final PopupWindow popupInfo=new PopupWindow(popupView,800,400,true);
        popupInfo.showAtLocation(popupView, Gravity.CENTER, 0, 40);;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                popupInfo.dismiss();

            }
        });

        }

    public void showPopupWindowRules(View view) {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popuprules, null);
        ImageButton button =  findViewById(R.id.rules);
        final PopupWindow popupInfo=new PopupWindow(popupView,800,400,true);
        popupInfo.showAtLocation(popupView, Gravity.CENTER, 0, 40);;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                popupInfo.dismiss();
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
            case R.id.Impossible:
                if (checked)
                    paramJug.selImpossible();
                    break;
        }
    }
    public void onRadioButtonColorClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.fichaama:
                if (checked)
                    paramJug.seleccionFicha(0);
                break;
            case R.id.ficharoj:
                if (checked)
                    paramJug.seleccionFicha(1);
                break;
            case R.id.fichaver:
                if (checked)
                    paramJug.seleccionFicha(2);
                break;
            case R.id.fichavio:
                if (checked)
                    paramJug.seleccionFicha(3);
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
                break;
            case R.id.LightTheme:
                if (checked)
                    paramJug.lightTheme();
                break;

        }

    }
    public void onRadioButtonMusicClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.MusicOff:
                if (checked)
                    paramJug.disMusic();
                break;
            case R.id.MusicOn:
                if (checked)
                    paramJug.enMusic();
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
                    paramJug.enSounds();
                break;
            case R.id.SEon:
                if (checked)
                    paramJug.disSounds();
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
                    paramJug.disTime();
                break;
            case R.id.Ton:
                if (checked)
                    paramJug.enTime();
                break;

        }

    }

}
