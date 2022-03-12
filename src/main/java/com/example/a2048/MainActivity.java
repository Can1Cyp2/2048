package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

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

    // TODO: RUNS THE GAME
    public void run(View view) {                             // Changed "startGame" to "run" and run() to run(View view)
        // Makes the grid to start the game
        Gameplay.makeGrid(Gameplay.GRIDSIZE);

        boolean loss = false; // if the player lost
        while (!loss) {
            Gameplay.fillRandomSpot();

            // If there are no available spots left and no moves can be made player loses
            if (Gameplay.endGame()) {
                loss = true;
            }

        }
    }

    // TODO Display numbers to the grid by changing the text boxes in the grid in game_screen.xml
    public void display_grid(){
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                String grid_num = "grid" + x + "_" + y;

                // Turning the space of the grid to an ID so it can be displayed
                int boxID = getResources().getIdentifier(grid_num, "id",getPackageName());
                ((TextView) findViewById(boxID)).setText(Gameplay.grid[x][y]);
            }
        }

    }



    /* TODO Starts the game: Initializes the grid by calling run, then switches to game screen
    public void startGame() {
        // Starts the game and switches the screen when "PLAY" is clicked
        run();
        display_grid();
        setContentView(R.layout.game_screen);
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

