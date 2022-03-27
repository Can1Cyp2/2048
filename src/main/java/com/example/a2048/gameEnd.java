package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class gameEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_screen);

        configureMenuButton();
        configureReplayButton();

        // Displaying text based on if the user won or not
        if (Gameplay.game_loss){
            ((TextView)findViewById(R.id.result)).setText("You lost :( - Restart the game to play again");
        }
        else{
            ((TextView)findViewById(R.id.result)).setText("You WON! :) - Restart the game and win again");
        }
        ((TextView)findViewById(R.id.final_score)).setText("Score: " + Gameplay.user_score);
    }


    public void configureMenuButton() {
        Button menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gameEnd.this, MainActivity.class));
            }
        });
    }

    public void configureReplayButton() {
        Button replayButton = (Button) findViewById(R.id.replay_button);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Gameplay.game_loss) {
                    MainActivity.user_goal = "999999";
                    startActivity(new Intent(gameEnd.this, Gameplay.class));
                }
                else{
                    ((TextView)findViewById(R.id.result)).setText("You lost. You cannot continue playing.");
                }
            }
        });
    }
}
