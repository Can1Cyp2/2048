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
    private Gameplay gameplay = new Gameplay();

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
                //text = (TextView) findViewById(R.id.textView2);
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


    // TODO: RUNS THE GAME
    public void run(View view) {            // Changed "startGame" to "run" and run() to run(View view)
        // Makes the grid to start the game
        System.out.println("GAME STARTED");
        // System.out.println(findViewById(R.id.text));
        System.out.println(Arrays.deepToString(Gameplay.grid));
        Gameplay.makeGrid(Gameplay.GRIDSIZE);
        gameplay.run_display_grid();

        // if the player lost:
        while (!game_lost) {
            Gameplay.fillRandomSpot();

            // If there are no available spots left and no moves can be made player loses
            if (Gameplay.endGame()) {
                game_lost = true;
            }

        }
    }





//        // Trying to change images
//        //"@drawable/drawtile"
//        ImageView ChangeImage;
//        ChangeImage = (ImageView)findViewById(R.id.imageView3);
//        ChangeImage.setImageResource(R.drawable.ic_launcher_background);





    /* TODO Starts the game: Initializes the grid by calling run, then switches to game screen
    public void startGame() {
        // Starts the game and switches the screen when "PLAY" is clicked
        Gameplay.makeGrid(Gameplay.GRIDSIZE);
        display_grid();

    } */

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

