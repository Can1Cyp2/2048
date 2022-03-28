package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;

public class gameEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_screen);                               // Sets the view to the End Game screen

        configureMenuButton();                                                  // Configure buttons on the screen and write high scores to the file
        configureContinueButton();
        //writeScores();


        if (Gameplay.game_loss){                                                                                // Displaying text based on if the user won or not
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
                startActivity(new Intent(gameEnd.this, MainActivity.class));            // Configures the button to take the user back to the Main Menu if clicked
            }
        });
    }


    public void configureContinueButton() {
        Button continueButton = (Button) findViewById(R.id.replay_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Gameplay.game_loss) {
                    MainActivity.user_goal = "999999";
                    startActivity(new Intent(gameEnd.this, Gameplay.class));
                }
                else {
                    ((TextView)findViewById(R.id.result)).setText("You lost. You cannot continue playing.");
                }
            }
        });
    }


    public void writeScores() {

            try {
                File highScores = new File(getFilesDir(), "highscores.txt");
                highScores.createNewFile();                                             // Creates file to store scores if it doesn't exist already
                FileWriter hsWriter = new FileWriter("highscores.txt");         // Used to write scores to the file
                Scanner hsReader = new Scanner(highScores);                             // Used to read scores from the file

                String[] highScoresList = new String[5];
                int counter = 0;

                while (hsReader.hasNextLine()) {                                        // Reads in scores from the file
                    highScoresList[counter] = hsReader.nextLine();
                    counter++;
                }

                hsReader.close();

                for (int a = 0; a < 5; a ++) {                                          // Fills up empty list spots with 0 before trying to add a score to the list (for the first time the file is created)
                    if (highScoresList[a].equals(null)) {
                        highScoresList[a] = "0";
                    }
                }

                for (int b = 0; b < 5; b++) {                                               // If the score is high enough to make it to the list, it's added and everything below is pushed down a spot
                    if (Gameplay.user_score > Integer.parseInt(highScoresList[b])) {

                        for (int c = 4; c > b; c--) {
                            highScoresList[c] = highScoresList[c - 1];
                        }

                        highScoresList[b] = Integer.toString(Gameplay.user_score);
                        break;
                    }
                }

                for (int d = 0; d < 5; d++) {                                       // Writes scores to file, may need to use BufferedWriter instead for .newLine
                    hsWriter.write(highScoresList[d]);
                }

                hsWriter.close();

            } catch (IOException error) {
                System.out.println(error);
            }
    }
}
