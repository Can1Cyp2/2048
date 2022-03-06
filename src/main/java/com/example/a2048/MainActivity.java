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
        run();
    }

    // TODO: RUNS THE GAME
    public void run(){
        // Makes the grid to start the game
        Gameplay.makeGrid(Gameplay.GRIDSIZE);

        boolean loss = false; // if the player lost
        while (!loss){
            Gameplay.fillRandomSpot();

            // If there are no available spots left and no moves can be made player loses
            if (Gameplay.endGame()){
                loss = true;
            }

        }

        /* TODO Display numbers to the grid by changing the text boxes in the grid in game_screen.xml
        public void display_grid(){

        } */


    }

    public void pointsUp(View v){

        EditText pointView = (EditText) findViewById(R.id.input_points);
        String pV = pointView.getText().toString();

        int points = Integer.parseInt(pV);
        points *= 2;

        ((TextView) findViewById(R.id.input_points)).setText(points);

    }


}

