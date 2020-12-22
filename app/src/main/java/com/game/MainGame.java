package com.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import com.game.ai.AiPlayer;
import com.game.ai.MinMax;

import java.io.Serializable;

import android.os.CountDownTimer;
public class MainGame extends AppCompatActivity {
    private static final int N_COLS = 7;
    private static final int N_ROWS = 6;
    boolean timer=false;
    CountDownTimer countDownTimer;
    private ParametrosJuego param = new ParametrosJuego();
    public  MediaPlayer mysong,mp;
    private static char PLAYER = '+';
    private static char AI = '0';
    private AiPlayer aiPlayer;
    char lastTurn = PLAYER;
    boolean winner = false;
    Logic logic = new Logic();
    int MAX_TURNS = 42;
    int current_turns = 0;
    LinearLayout gameLayout;

    public void goMenu(View view) {
        if(timer){countDownTimer.cancel();}
        ImageButton buttonHome = findViewById(R.id.back_button);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(param.sounds){ mp.start();}
                Intent gameIntent = new Intent(getApplicationContext(), MainMenu.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);
            }
        });
    }
    public void goSettings(View view) {
        if(timer){countDownTimer.cancel();}
        ImageButton buttonSetting = findViewById(R.id.config_button);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(param.sounds){ mp.start();}
                Intent gameIntent = new Intent(getApplicationContext(), Settings.class);
                gameIntent.putExtra("ParametrosJuego", (Serializable) param);
                gameIntent.putExtra("PREVIOUS_ACTIVITY", "MainGame");
                getIntent().getStringExtra("PREVIOUS_ACTIVITY");
                getIntent().getSerializableExtra("ParametrosJuego");
                startActivity(gameIntent);
            }
        });
    }
    protected void goFinal(String result){
        if(param.sounds){ mp.start();}
        if(timer){countDownTimer.cancel();}
        Intent finIntent = new Intent(getApplicationContext(), Final.class);
        finIntent.putExtra("ParametrosJuego", (Serializable) param);
        getIntent().getSerializableExtra("ParametrosJuego");
        finIntent.putExtra("RESULT", result);
        getIntent().getStringExtra("RESULT");
        startActivity(finIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp=MediaPlayer.create(this,R.raw.click);
        Intent i = getIntent();
        param = (ParametrosJuego)i.getSerializableExtra("ParametrosJuego");
        if(param==null){
            param=new ParametrosJuego();
        }else{
            System.out.println(param.music);
            if(param.music){
                mysong= MediaPlayer.create(MainGame.this,R.raw.game);
                mysong.start();
            }

            if(param.darktheme){
                setTheme(R.style.gameDark);
            }else{
                setTheme(R.style.gameLight);
            }
            setContentView(R.layout.activity_main_game);

            gameLayout = findViewById(R.id.gameLayout);
            ImageView yourColor = (ImageView) findViewById(R.id.PlayerTurnImage);
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
            lastTurn = PLAYER;
            turnSelector(true);
        } else{
            lastTurn = AI;
            ai_move();
        }
    }
    protected void turnManager(){
        winner = logic.ganador(lastTurn);
        updateMoveCount();
        if(winner){
            System.out.println("WINNING COMBO: "+ logic.fichasGanador);
            winAnimation();
        } else if(current_turns >= MAX_TURNS) {
            if(timer)countDownTimer.cancel();
            goFinal("Tie");

        } else {
            lastTurn = lastTurn == PLAYER ? AI : PLAYER;

            if(lastTurn==AI){
                ai_move();
            } else{
                turnSelector(true);
            }
        }
        /**
         if(winner && lastTurn==AI){
         goFinal("Lose");
         return;
         } else if(winner && lastTurn==PLAYER){
         goFinal("Win");
         return;
         }else if(current_turns>=MAX_TURNS){
         goFinal("Tie");
         return;
         } */

    }
    protected void player_move(int col){
        int row = logic.validarJugada(col,PLAYER);
        if(row==-1){
            return;
        }
        turnSelector(false);
        displayPiece(col,row,false);
    }

    protected void ai_move(){
        if(param.sounds){ mp.start();}
        if(timer)countDownTimer.cancel();
        setcounter(param.gameDifficulty);
        int[] move = aiPlayer.getAiMove(AI,logic);
        Logic.actualizarTablero(logic.getTablero());
        displayPiece(move[0],move[1],true);
    }


    protected void displayPiece(int col, int row, boolean isAi){
        LinearLayout column = (LinearLayout) gameLayout.getChildAt(col);
        ImageView piece = (ImageView) column.getChildAt(row);
        int animationColor;
        if(isAi) {
            animationColor = param.getAiColor();
        } else{
            animationColor = param.getPlayerColor();
        }
        int colorAnimacion;
        if(param.darktheme){
            colorAnimacion=R.color.backgroundLilac;
        }else{
            colorAnimacion=R.color.backgroundWhite;
        }
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                getColor(colorAnimacion), getColor(animationColor));
        colorAnimation.setDuration(800); // milliseconds
        StateListDrawable state_drawable = (StateListDrawable) piece.getBackground();
        DrawableContainer.DrawableContainerState dcs = (DrawableContainer.DrawableContainerState) state_drawable.getConstantState();
        Drawable[] drawableItems = dcs.getChildren();
        final GradientDrawable background = (GradientDrawable) drawableItems[0]; // item 1

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                background.setColor((int) animator.getAnimatedValue());
            }


        });
        colorAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                turnManager();
            }
        });
        colorAnimation.start();

    }

    protected void winAnimation(){
        int animationColor;

        int colorBackground;
        if(param.darktheme){
            colorBackground=R.color.backgroundLilac;
        }else{
            colorBackground=R.color.backgroundWhite;
        }
        if(lastTurn == AI) {
            animationColor = param.getAiColor();
        } else{
            animationColor = param.getPlayerColor();
        }

        for(int ficha=0; ficha < 4; ficha++){
            int row = logic.fichasGanador[ficha][0];
            int col = logic.fichasGanador[ficha][1];
            final int animation_id = ficha;

            ImageView piece = (ImageView) ((LinearLayout) gameLayout.getChildAt(col)).getChildAt(row);

            ValueAnimator winAnim = ValueAnimator.ofObject(new ArgbEvaluator(), getColor(animationColor), getColor(colorBackground));
            StateListDrawable state_drawable = (StateListDrawable) piece.getBackground();
            DrawableContainer.DrawableContainerState dcs = (DrawableContainer.DrawableContainerState) state_drawable.getConstantState();
            Drawable[] drawableItems = dcs.getChildren();
            final GradientDrawable background = (GradientDrawable) drawableItems[0];
            winAnim.setDuration(800);
            winAnim.setRepeatCount(3);
            winAnim.setRepeatMode(ValueAnimator.REVERSE);
            winAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    background.setColor((int) animator.getAnimatedValue());
                }


            });
            winAnim.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(animation_id==3) {
                        if (lastTurn == AI) {
                            if(timer)countDownTimer.cancel();
                            goFinal("Lose");
                        } else {
                            if(timer)countDownTimer.cancel();
                            goFinal("Win");
                        }
                    }
                }
            });
            winAnim.start();

        }
    }


    protected void setDifficultyLabel(){
        TextView label = findViewById(R.id.label_difficulty);
        String label_text = "";
        final TextView textView = (TextView)findViewById(R.id.game_time);
        switch(param.getDifficulty()){
            case 0:
                timer=false;
                break;
            case 1:
                timer=true;
                countDownTimer = new CountDownTimer(40000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(""+String.format("%d sec",TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)));
                    }
                    public void onFinish() {
                        goFinal("Lose");
                    }
                };
                break;
            case 2:
                timer=true;
                countDownTimer = new CountDownTimer(20000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText("" + String.format("%d sec", TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish() {
                        goFinal("Lose");
                    }
                };
                break;
        }
        label.setText(param.getDifficultyLabel());
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

    private void setcounter(int w){
        final TextView textView = (TextView)findViewById(R.id.game_time);
        if(w==0|| !(param.timeEnable)) {
            textView.setText(" ");
        }
        else{

            if(w==1){
                countDownTimer = new CountDownTimer(40000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(""+String.format("%d sec", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
                    }
                    public void onFinish() {
                        goFinal("Lose");
                    }
                }.start();
            }
            else{
                countDownTimer = new CountDownTimer(20000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(""+String.format("%d sec", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));}
                    public void onFinish() {
                        goFinal("Lose");
                    }
                }.start();
            }
        }
    }

}
