package com.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainGame extends AppCompatActivity {
    private static final int N_COLS = 7;
    private static final int N_ROWS = 6;

    private GameRules rules = new GameRules();

    private static char PLAYER = '+';
    private static char AI = 'o';

    boolean winner = false;
    Logic logic = new Logic();
    int MAX_TURNS = 42;
    int current_turns = 1;

    LinearLayout gameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        gameLayout = findViewById(R.id.gameLayout);

        setDifficultyLabel();
        setColumnSelector();

        first_move();

    }

    protected void first_move(){
        if(rules.getFirstTurn()){
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

        boolean winner = logic.ganador(PLAYER);
        current_turns++;
        if(!winner && current_turns<=MAX_TURNS) {
            ai_move();
        }
    }

    protected void ai_move(){
        int max = N_COLS -1;
        int min = 0;
        Random r = new Random();
        int col =  r.nextInt((max - min) + 1) + min;

        int row = -1;
        while(true) {
            row = logic.validarJugada(col,AI);
            if (row != -1) {
                break;
            }
        }
        displayPiece(col,row,true);

        boolean winner = logic.ganador(AI);
        current_turns++;
        if(!winner && current_turns<=MAX_TURNS) {
            turnSelector(true);
        }

    }


    protected void displayPiece(int col, int row, boolean isAi){
        LinearLayout column = (LinearLayout) gameLayout.getChildAt(col);
        ImageView piece = (ImageView) column.getChildAt(row);
        if(isAi) {
            piece.setBackgroundResource(R.drawable.player2_shape);
        } else{
            piece.setBackgroundResource(R.drawable.player1_shape);
        }
    }


    protected void setDifficultyLabel(){
        TextView label = findViewById(R.id.label_difficulty);
        String label_text = "";
        switch(rules.getDifficulty()){
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

}
