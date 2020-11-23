package com.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game.ai.AiPlayer;
import java.io.Serializable;

public class MainGame extends AppCompatActivity {
    private static final int N_COLS = 7;
    private static final int N_ROWS = 6;

    private ParametrosJuego param = new ParametrosJuego();

    public  MediaPlayer mysong;
    private static char PLAYER = '+';
    private static char AI = '0';

    private AiPlayer aiPlayer;

    boolean winner = false;
    Logic logic = new Logic();
    int MAX_TURNS = 42;
    int current_turns = 0;

    LinearLayout gameLayout;

    public void goMenu(View view) {
        ImageButton buttonHome = findViewById(R.id.back_button);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), MainMenu.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);
                setContentView(R.layout.activity_main);
            }
        });
    }
    public void goSettings(View view) {
        ImageButton buttonSetting = findViewById(R.id.config_button);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), Settings.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                gameIntent.putExtra("PREVIOUS_ACTIVITY", "MainGame");
                getIntent().getStringExtra("PREVIOUS_ACTIVITY");
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);
                setContentView(R.layout.settings);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        param = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");
        setContentView(R.layout.activity_main_game);

        gameLayout = findViewById(R.id.gameLayout);
        ImageView yourColor = (ImageView) findViewById(R.id.PlayerTurnImage);
        if(param==null){
            param=new ParametrosJuego();
        }else{
            System.out.println(param.music);
            if(param.music){
                mysong= MediaPlayer.create(MainGame.this,R.raw.tetris);
                mysong.start();
            }

            switch(param.selFicha){
                case 0:
                    yourColor.setBackgroundResource(R.drawable.green_color);
                    break;
                case 1:
                    yourColor.setBackgroundResource(R.drawable.red_color);
                    break;
                case 2:
                    yourColor.setBackgroundResource(R.drawable.yellow_color);
                    break;
                case 3:
                    yourColor.setBackgroundResource(R.drawable.violet_color);
                    break;
            }
        }

        setDifficultyLabel();
        setColumnSelector();

        aiPlayer = param.getAiPlayer();
        first_move();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mysong != null) {
            mysong.pause();
            if (isFinishing()) {
                mysong.stop();
                mysong.release();
            }
        }
    }

    protected void first_move(){
        if(param.getFirstTurn()){
            turnSelector(true);
        } else{
            ai_move();
        }
    }


    protected void player_move(int col){
        int row = logic.validarJugada(col,PLAYER);
        if(row==-1){
            return;
        }

        displayPiece(col,row,false);
        turnSelector(false);

        winner = logic.ganador(PLAYER);
        updateMoveCount();
        if(!winner && current_turns<=MAX_TURNS) {
            ai_move();
        }else if(winner){
            //IR A PANTALLA FINAL COMO GANAR
            Intent finIntent = new Intent(getApplicationContext(), Final.class);
            finIntent.putExtra("ParametrosJuego", (Serializable) param);
            getIntent().getSerializableExtra("ParametrosJuego");
            finIntent.putExtra("RESULT", "Win");
            getIntent().getStringExtra("RESULT");
            startActivity(finIntent);
            setContentView(R.layout.activity_win);

        }else{
            Intent finIntent = new Intent(getApplicationContext(), Final.class);
            finIntent.putExtra("ParametrosJuego", (Serializable) param);
            getIntent().getSerializableExtra("ParametrosJuego");
            finIntent.putExtra("RESULT", "Tie");
            getIntent().getStringExtra("RESULT");
            startActivity(finIntent);
            setContentView(R.layout.activity_win);
        }
    }

    protected void ai_move(){
        int[] move = aiPlayer.getAiMove(AI,logic);
        displayPiece(move[0],move[1],true);

        winner = logic.ganador(AI);
        updateMoveCount();
        if(!winner && current_turns<=MAX_TURNS) {
            turnSelector(true);
        }else if(winner){
            //IR A PANTALLA FINAL COMO PERDER
            Intent finIntent = new Intent(getApplicationContext(), Final.class);
            finIntent.putExtra("ParametrosJuego", (Serializable) param);
            getIntent().getSerializableExtra("ParametrosJuego");
            finIntent.putExtra("RESULT", "Lose");
            getIntent().getStringExtra("RESULT");
            startActivity(finIntent);
            setContentView(R.layout.activity_win);
        }else{
            Intent finIntent = new Intent(getApplicationContext(), Final.class);
            finIntent.putExtra("ParametrosJuego", (Serializable) param);
            getIntent().getSerializableExtra("ParametrosJuego");
            finIntent.putExtra("RESULT", "Tie");
            getIntent().getStringExtra("RESULT");
            startActivity(finIntent);
            setContentView(R.layout.activity_win);

        }

    }


    protected void displayPiece(int col, int row, boolean isAi){
        LinearLayout column = (LinearLayout) gameLayout.getChildAt(col);
        ImageView piece = (ImageView) column.getChildAt(row);
        if(isAi) {
            piece.setBackgroundResource(R.drawable.ai);
        } else{
            switch(param.selFicha){
                case 0:
                    piece.setBackgroundResource(R.drawable.green);
                    break;
                case 1:
                    piece.setBackgroundResource(R.drawable.red);
                    break;
                case 2:
                    piece.setBackgroundResource(R.drawable.yellow);
                    break;
                case 3:
                    piece.setBackgroundResource(R.drawable.violet);
                    break;
            }
        }
    }


    protected void setDifficultyLabel(){
        TextView label = findViewById(R.id.label_difficulty);
        String label_text = "";
        switch(param.getDifficulty()){
            case 0:
                label_text = "EASY";
                break;
            case 1:
                label_text = "MEDIUM";
                break;
            case 2:
                label_text = "HARD";
                break;
        }
        label.setText(label_text);
        return;
    }


    protected void setColumnSelector(){

        LinearLayout gameLayout= findViewById(R.id.gameLayout);

        for (int i = 0; i < N_COLS; i++) {

            LinearLayout column = (LinearLayout) gameLayout.getChildAt(i);

            column.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int indexOfMyView = ((LinearLayout ) v.getParent()).indexOfChild(v);
                    player_move(indexOfMyView);
                }
            });


        }
        return;
    }

    protected void turnSelector(Boolean isPlayerTurn){

        for (int i = 0; i < N_COLS; i++) {
            LinearLayout column = (LinearLayout) gameLayout.getChildAt(i);
            column.setClickable(isPlayerTurn);
        }

        return;
    }

    private void updateMoveCount() {
        current_turns++;
        TextView moveCount = findViewById(R.id.moveCount);
        String moveLable = current_turns + " moves";
        moveCount.setText(moveLable);
    }

}
