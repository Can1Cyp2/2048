package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Gameplay extends AppCompatActivity {
    private Activity view;

    // Sets the screen up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        configureLeaveButton();
        configureMoveUp();
        configureMoveDown();
        configureMoveLeft();
        configureMoveRight();
        //setGoal();
    }

    public void configureLeaveButton() {
        Button backButton = (Button) findViewById(R.id.Main_Screen);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gameplay.this, MainActivity.class));;

            }
        });
    }

    public void configureMoveUp() {
        Button backButton = (Button) findViewById(R.id.move_up);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveUp();

            }
        });
    }

    public void configureMoveDown() {
        Button backButton = (Button) findViewById(R.id.move_down);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDown();

            }
        });
    }

    public void configureMoveLeft() {
        Button backButton = (Button) findViewById(R.id.move_left);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLeft();

            }
        });
    }

    public void configureMoveRight() {
        Button backButton = (Button) findViewById(R.id.move_right);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRight();

            }
        });
    }


    public static final int GRIDSIZE = 4; // Size of default grid
    public static int[][] grid = new int[GRIDSIZE][GRIDSIZE];  // Creating base grid

    public static int user_score = 0;


    public static void makeGrid(int gridSize) {
        // Creates a grid for the game
        // gridSize is standard 4

        final int EMPTY = 0; // Empty value for grid

        // Initializing grid values
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y] = 0;
            }
        }

        /* Visualization for a default grid of 4:
         *  [[0, 0, 0, 0],
         *  [0, 0, 0, 0],
         *  [0, 0, 0, 0],
         *  [0, 0, 0, 0]], */
    }


    /*public void setGoal(){
        TextView temp = findViewById(R.id.input_points);
        String points = temp.getText().toString();

        ((TextView)findViewById(R.id.point_goal)).setText(points);
    } */


    public void run_display_grid() {
        display_grid(view);
    }


    // Displays the numbers to the grid
    public void display_grid(Activity view) {

        //System.out.println(Arrays.deepToString(grid)); Displays full grid
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                String grid_num = "grid" + x + "_" + y;
                //System.out.println(String.valueOf(Gameplay.grid[x][y])); // Showing the grid contents

                // Turning the space of the grid to an ID so it can be displayed
                int boxID = getResources().getIdentifier(grid_num, "id", getPackageName());
                ((TextView) findViewById(boxID)).setText(String.valueOf(Gameplay.grid[x][y]));

            }
        }

        updateScore();
        ((TextView) findViewById(R.id.userScore)).setText("Score: " + Integer.toString(user_score));

    }


    public static boolean isSpotEmpty(int x, int y) {
        // If spot in grid is equal to 0 it is empty, function returns true
        return grid[x][y] == 0; // If the spot has a number > 0, return false
    }


    // Returns number of available spots left
    private static int availableSpots() {
        int availableCount = 0; // Number of empty spaces

        // Checks if spaces on grid are empty
        for (int x = 0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                if (grid[x][y] == 0) availableCount++;

            }
        }

        return availableCount;
    }


    // Checks if the player can make any more moves
    private static boolean availableMoves() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (isSpotEmpty(x, y)) {
                    System.out.println("1");
                    return true;
                }
                if (x < 3) {
                    if (grid[x][y] == grid[x + 1][y]) {
                        return true;
                    }
                }

                if (y < 3) {
                    if (grid[x][y] == grid[x][y + 1]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Checks if there are any empty spots and possible spots for the player to move
    public void endGame() {
        System.out.println(availableMoves() + " " + availableSpots());
        if (availableSpots() == 0 && !availableMoves()) {
            gameEnd();

            /*
            try {
                File highScores = new File(getFilesDir(), "highscores.txt");
                highScores.createNewFile();                                             // Creates file to store scores if it doesn't exist already
                FileWriter hsWriter = new FileWriter("highscores.txt");                 // Used to write scores to the file
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

                for (int b = 0; b < 5; b++) {                                           // If the score is high enough to make it to the list, it's added and everything below is pushed down a spot
                    if (user_score > Integer.parseInt(highScoresList[b])) {

                        for (int c = 4; c > b; c--) {
                            highScoresList[c] = highScoresList[c - 1];
                        }

                        highScoresList[b] = Integer.toString(user_score);
                        break;
                    }
                }

                for (int d = 0; d < 5; d++) {                                           // Writes scores to file, may need to use BufferedWriter instead for .newLine
                    hsWriter.write(highScoresList[d]);
                }

                hsWriter.close();

            } catch (IOException error) {
                System.out.println(error);
            } */

        }

        return;
        // Returns nothing if there are moves the player can make
    }


    // Fills a random spot on the board with a 2 or 4
    public static void fillRandomSpot() {
        Random rand = new Random();
        int x, y;

        if (availableSpots() == 0) return;

        // Finds an empty space on the grid
        do {

            x = rand.nextInt(GRIDSIZE);
            y = rand.nextInt(GRIDSIZE);

        } while (!isSpotEmpty(x, y));

        // Getting number to fill the empty grid spot
        int num = rand.nextInt(10);

        // Less of a chance to place a 4
        int fill_num = 0;
        if (num < 8) fill_num = 2;
        else fill_num = 4;

        grid[x][y] = fill_num;
    }






    /* ****************************** MOVEMENT OF GRID PIECES: ******************************* */

    // moves grid pieces to the right
    public void moveRight() {
        // moving down the double array, to move right
        for (int x = 2; x >= 0; x--) {
            for (int y = 0; y < 4; y++) {

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] == 0) continue;

                // Moving the numbers
                int z = x; // Checking for z in place of x:
                while (z < 3 && grid[z + 1][y] == 0) {
                    grid[z + 1][y] = grid[z][y];
                    grid[z][y] = 0;
                    z++;
                }
                if (z < 3) {
                    if (grid[z + 1][y] == grid[z][y]) {
                        grid[z + 1][y] *= 2;
                        grid[z][y] = 0;
                    }
                }
            }
        }
        endGame();
        fillRandomSpot();
        run_display_grid();
    }

    // moves grid pieces to the left
    public void moveLeft() {
        // moving up the double array, to move left
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] == 0) continue;

                // Moving the numbers left
                int z = x; // Checking for z in place of x:
                while (z > 0 && grid[z - 1][y] == 0) {
                    grid[z - 1][y] = grid[z][y];
                    grid[z][y] = 0;
                    z--;
                }

                if (z > 0) {
                    if (grid[z - 1][y] == grid[z][y]) {
                        grid[z - 1][y] *= 2;
                        grid[z][y] = 0;
                    }
                }
            }
        }
        endGame();
        fillRandomSpot();
        run_display_grid();
    }

    // Moves the grid pieces up
    public void moveUp() {
        // Moving grid pieces up the double array by moving left in the array
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] == 0) continue;

                int j = y;
                while (j > 0 && grid[x][j - 1] == 0) {
                    grid[x][j - 1] = grid[x][j];
                    grid[x][j] = 0;
                    j--;
                }

                if (j > 0) {
                    if (grid[x][j - 1] == grid[x][j]) {
                        grid[x][j - 1] *= 2;
                        grid[x][j] = 0;
                    }
                }
            }
        }
        endGame();
        fillRandomSpot();
        run_display_grid();
    }

    // Moves the grid pieces down
    public void moveDown() {
        // Moving grid pieces down by moving right in the double array
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] == 0) continue;

                int j = y;
                while (j < 3 && grid[x][j + 1] == 0) {
                    grid[x][j + 1] = grid[x][j];
                    grid[x][j] = 0;
                    j++;
                }

                if (j < 3) {
                    if (grid[x][j + 1] == grid[x][j]) {
                        grid[x][j + 1] *= 2;
                        grid[x][j] = 0;
                    }
                }
            }
        }
        endGame();
        fillRandomSpot();
        run_display_grid();
    }

    /* *************************** ^^ MOVEMENT OF GRID PIECES ^^ ******************************* */

    public static void updateScore() {
        user_score = grid[0][0];

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (grid[a][b] > user_score) {
                    user_score = grid[a][b];
                }
            }
        }

    }

    public void gameEnd() {
        startActivity(new Intent(Gameplay.this, gameEnd.class));
    }

}