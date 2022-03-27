package com.example.a2048;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class Scores extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores_screen);

        configureBackButton();
        //displayScores();
    }


    public void configureBackButton() {
        Button backButton = (Button) findViewById(R.id.main_menu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void displayScores() {                                           // Won't work without being able to read/write from/to the file but here if we need it
        File highscores = new File("highscores.txt");

        try {
            FileWriter hsWriter = new FileWriter("highscores.txt");         // Used to write scores to the file
            Scanner hsReader = new Scanner(highscores);

            List<Integer> high_scores = new ArrayList<>();

            while (hsReader.hasNextLine()) {                                        // Reads in scores from the file
                high_scores.add(hsReader.nextInt());
            }

            hsReader.close();

            int counter = 1;
            String output = "";

            Collections.sort(high_scores);

            for (int score : high_scores) {
                String.join("\n", output, Integer.toString(counter), ". ", Integer.toString(score));
                counter++;
            }

            ((TextView) findViewById(R.id.scores_list)).setText(output);

        } catch (FileNotFoundException error) {
            System.out.println(error);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
