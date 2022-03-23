package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private Activity view;
    private final Gameplay gameplay = new Gameplay();

    //TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurePlayButton();
        configureInstructionsButton();
        configureHiScoresButton();
    }

    public void configurePlayButton(){
        Button nextButton = (Button) findViewById(R.id.Game_Start);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Gameplay.class));
                run(view);

            }
        });
    }

    public void configureInstructionsButton() {
        Button instructions = (Button) findViewById(R.id.Game_Instructions);
        instructions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });
    }

    public void configureHiScoresButton() {
        Button hiscores = (Button) findViewById(R.id.high_scores);
        hiscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Scores.class));
            }
        });
    }

    public boolean game_lost = false;   // if the player lost or not


    // RUNS THE GAME
    public void run(View view) {
        // Makes the grid to start the game
        System.out.println("GAME STARTED");

        System.out.println(Arrays.deepToString(Gameplay.grid));
        Gameplay.makeGrid(Gameplay.GRIDSIZE);

    }



    public void pointsUp(View v){

        TextView pointView = (TextView) findViewById(R.id.input_points);
        String pV = pointView.getText().toString();

        int points = Integer.parseInt(pV);
        points *= 2;
        String goal = (String.valueOf(points));

        // Max amount of points the user can use
        if (points <= 16384) {
            ((TextView) findViewById(R.id.input_points)).setText(goal);
        }
    }


    public void pointsDown(View v){

        TextView pointView = (TextView) findViewById(R.id.input_points);
        String pV = pointView.getText().toString();

        int points = Integer.parseInt(pV);
        points /= 2;
        String goal = (String.valueOf(points));

        // Max amount of points the user can use
        if (points >= 64) {
            ((TextView) findViewById(R.id.input_points)).setText(goal);
        }
    }


}

