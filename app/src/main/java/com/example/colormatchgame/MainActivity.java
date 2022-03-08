package com.example.colormatchgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView color_button, color_arrow;
    TextView score;
    ProgressBar ProgressBar;
    Handler handler;
    Runnable runnable;
    Random r;

    private final static int Blue = 1;
    private final static int Red = 2;
    private final static int Green = 3;
    private final static int Orange = 4;

    int buttonState = Blue;
    int arrowState = Blue;
    int CurrentTimer = 4000;
    int StartTimer = 4000;
    int CurrentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color_button = findViewById(R.id.color_button);
        color_arrow = findViewById(R.id.color_arrow);
        score = findViewById(R.id.score);
        ProgressBar =  findViewById(R.id.ProgressBar);


        //Timer
        ProgressBar.setMax(StartTimer);
        ProgressBar.setProgress(StartTimer);

        //Score
        score.setText("Your Score: " + CurrentScore);

        //Arrow Color
        r = new Random();
        arrowState = r.nextInt(4) + 1;
        setArrowImage(arrowState);

        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonImage(setButtonPosition(buttonState));

            }
        });

        //Game Loop
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                CurrentTimer = CurrentTimer - 100;
                ProgressBar.setProgress(CurrentTimer);
                if (CurrentTimer > 0) {
                    handler.postDelayed(runnable, 100);
                } else {
                    if (buttonState == arrowState) {
                        CurrentScore = CurrentScore + 1;
                        score.setText("Your Score: " + CurrentScore);

                        //Faster Gameplay
                        StartTimer = StartTimer - 100;
                        if (StartTimer < 1000) {
                            StartTimer = 2000;
                        }

                        ProgressBar.setMax(StartTimer);
                        CurrentTimer = StartTimer;
                        ProgressBar.setProgress(CurrentTimer);

                        //New Arrow Color
                        arrowState = r.nextInt(4) + 1;
                        setArrowImage(arrowState);

                        handler.postDelayed(runnable, 100);
                    }else {
                        color_button.setEnabled(false);

                        //DialogBox na nagtatanong kung maglalaro ulit o mag kuquit na
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("GAME OVER!");
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                "QUIT",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.exit(0);
                                    }
                                });

                        builder1.setNegativeButton(
                                "PLAY AGAIN",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();


                                        //irerestart yung mismong program
                                        startActivity(getIntent());
                                    }
                                });

                        AlertDialog alert = builder1.create();
                        alert.show();
                    }
                    }
                }
            };
        // Game loop
        handler.postDelayed(runnable,100);


        }

    private void setArrowImage (int state) {
        switch (state) {
            case Blue:
                color_arrow.setImageResource(R.drawable.arrow_blue1);
                arrowState = Blue;
                break;

            case Red:
                color_arrow.setImageResource(R.drawable.arrow_red2);
                arrowState = Red;
                break;

            case Green:
                color_arrow.setImageResource(R.drawable.arrow_green3);
                arrowState = Green;
                break;

            case Orange:
                color_arrow.setImageResource(R.drawable.arrow_orange4);
                arrowState = Orange;
                break;

        }
    }

    //Rotation Animation
    private  void setRotation (final ImageView image, final int drawable) {

        // Rotation
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(rotateAnimation);

    }
    //Color Button Position
    private int setButtonPosition(int position) {
        position = position + 1;
        if (position == 5) {
            position = 1;
        }
        return position;
    }
    //Button Color Position
    private  void setButtonImage (int state){
        switch (state) {
            case Blue:
                setRotation(color_button, R.drawable.color_blue1);
                buttonState = Blue;
                break;

            case Red:
                setRotation(color_button, R.drawable.color_red2);
                buttonState = Red;
                break;

            case Green:
                setRotation(color_button, R.drawable.color_green3);
                buttonState = Green;
                break;

            case Orange:
                setRotation(color_button, R.drawable.color_orange4);
                buttonState = Orange;
                break;

        }


    }
}