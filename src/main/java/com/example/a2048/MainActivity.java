package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // TODO: RUNS THE GAME
    public void run() {
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

    /* TODO Display numbers to the grid by changing the text boxes in the grid in game_screen.xml
    public void display_grid(){

     } */




    /* TODO Starts the game: Initializes the grid by calling run, then switches to game screen
    public void startGame() {
        // Starts the game and switches the screen when "PLAY" is clicked
        run();
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

