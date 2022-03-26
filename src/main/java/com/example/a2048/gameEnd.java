package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class gameEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_screen);

        configureMenuButton();
        configureReplayButton();
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
                startActivity(new Intent(gameEnd.this, Gameplay.class));
            }
        });
    }
}
