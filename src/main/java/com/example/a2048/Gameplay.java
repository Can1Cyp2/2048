package com.example.a2048;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

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

        // Setting up the intro text
        ((TextView)findViewById(R.id.point_goal)).setText("Click a movement button to start your game");
    }

    public void configureLeaveButton() {
        Button backButton = (Button) findViewById(R.id.Main_Screen);
        backButton.setOnClickListener(this::onClick);
    }

    public void configureMoveUp() {
        Button backButton = (Button) findViewById(R.id.move_up);
        backButton.setOnClickListener(this::onClick2);
    }

    public void configureMoveDown() {
        Button backButton = (Button) findViewById(R.id.move_down);
        backButton.setOnClickListener(this::onClick3);
    }

    public void configureMoveLeft() {
        Button backButton = (Button) findViewById(R.id.move_left);
        backButton.setOnClickListener(this::onClick4);
    }

    public void configureMoveRight() {
        Button backButton = (Button) findViewById(R.id.move_right);
        backButton.setOnClickListener(this::onClick5);
    }


    public static final int GRIDSIZE = 4; // Size of default grid
    public static int[][] grid = new int[GRIDSIZE][GRIDSIZE];  // Creating base grid

    public static int user_score = 0;

    public static boolean game_loss = true;


    public static void makeGrid(int gridSize) {
        // Creates a grid for the game
        // gridSize is standard 4

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



    public void run_displays() {
        display_grid();
        display_points();
        updateColours();
    }


    // Displays the numbers to the grid, and updates the grid
    public void display_grid() {

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

        // Updates the users score with the update of a grid display
        updateScore();
        ((TextView)findViewById(R.id.userScore)).setText("Score: " + user_score);

    }


    public void display_points(){
        // Displays the users points
        ((TextView)findViewById(R.id.point_goal)).setText("Point goal: " + MainActivity.user_goal);
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

                // if there is an empty spot then return true. The player can move
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

        // If there are no moves to be made then return false
        return false;
    }





    // Checks if there are any empty spots and possible spots for the player to move
    public void endGame() {

        if (availableSpots() == 0 && !availableMoves()) {                               // If out of moves, game is lost
            game_loss = true;
            startActivity(new Intent(Gameplay.this, gameEnd.class));        // Sends to gameEnd screen
        }

        if (user_score >= parseInt(MainActivity.user_goal)){                            // If target number is reached, game is won

            game_loss = false;
            startActivity(new Intent(Gameplay.this, gameEnd.class));        // Sends to gameEnd screen

        }
    }



    // Fills a random spot on the board with a 2 or 4
    public void fillRandomSpot(){
        Random rand = new Random();
        int x, y;

        if (availableSpots() == 0) return;

        // Finds an empty space on the grid
        do {

            x = rand.nextInt(GRIDSIZE);
            y = rand.nextInt(GRIDSIZE);

        } while (!isSpotEmpty(x, y));

        // Getting number to fill the empty grid spot
        int randNum = rand.nextInt(10);

        int high = 4;   // The highest random number to generate
        int low = 2;    // The lowest random number to generate


        // The high and low numbers change based on the users score making the game easier to continue
        if (user_score == 64){
            // Low is the default value -> low = 2

            // 25% chance the highest number will be an 8
            int randHigh = rand.nextInt(4);
            if (randHigh == 3) {
                high = 8;
            }
            else{
                high = 4;
            }

        }

        else if (user_score == 128){
            // No more 2's will be spawned on the grid
            high = 8;
            low = 4;

            // Removes the number 2 and replaces it with 4 because 2 will not spawn anymore
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] == 2) grid[i][j] = 4;
                }
            }
        }

        else if (user_score == 256){
            high = 16;

            // About a 66% chance the lowest number will be an 8, 33% it is 4
            int randLow = rand.nextInt(3);
            if (randLow == 2) {
                low = 8;
            }
            else{
                low = 4;
            }
        }

        else if (user_score == 512){
            high = 16;
            low = 8;

            // 25% chance the highest number will be a 32
            int randHigh = rand.nextInt(4);
            if (randHigh == 3) {
                high = 32;
            }

            // Removes the number 4 and replaces it with 8 because 4 will not spawn anymore
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] == 4) grid[i][j] = 8;
                }
            }
        }

        else if (user_score == 1024){
            high = 16;
            low = 8;

            // 33% chance the highest number will be an 32
            int randHigh = rand.nextInt(4);
            if (randHigh == 3) {
                high = 32;
            }
        }

        else if (user_score >= 2048){
            high = 32;
            low = 16;

            // 25% chance the highest number will be an 64
            int randHigh = rand.nextInt(4);
            if (randHigh == 3) {
                high = 64;
            }

            // Removes the number 8 by replacing it with 0 because those numbers will not spawn anymore
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] == 8) grid[i][j] = 0;
                    // Replacing with 0 provides the user with more opportunity to continue the game
                }
            }
        }

        // Less of a chance to place a 4
        int fill_num = 0;
        if (randNum < 8) fill_num = low;
        else fill_num = high;   // 30% chance to get a high number

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
            run_displays();
        }

        // moves grid pieces to the left
        public void moveLeft () {
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
            run_displays();
        }

        // Moves the grid pieces up
        public void moveUp () {
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
            run_displays();
        }

        // Moves the grid pieces down
        public void moveDown () {
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
            run_displays();
        }

        /* *************************** ^^ MOVEMENT OF GRID PIECES ^^ ******************************* */


    public static void updateScore() {
        user_score = grid[0][0];                            // Sets the user's score equal to the value in the top left square as a baseline

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (grid[a][b] > user_score) {
                    user_score = grid[a][b];                // Sets the user's score equal to the current highest number on the grid
                }
            }
        } 
    }

    public void updateColours() {

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {                                                                                       // Cycles through each square on the grid
                String grid_num = "grid" + x + "_" + y;

                int boxID = getResources().getIdentifier(grid_num, "id", getPackageName());

                switch (grid[x][y]) {                                                                                           // The square will be made a certain colour depending on the number in it
                    case 0:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#cdc1b5"));
                        break;
                    case 2:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#eee4da"));
                        break;
                    case 4:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#ece0c8"));
                        break;
                    case 8:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#f2b179"));
                        break;
                    case 16:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#f59563"));
                        break;
                    case 32:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#f57c5f"));
                        break;
                    case 64:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#f65d3b"));
                        break;
                    case 128:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#eccf73"));
                        break;
                    case 256:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#edcc61"));
                        break;
                    case 512:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#eec84a"));
                        break;
                    case 1024:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#edc53f"));
                        break;
                    case 2048:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#eec22d"));             // Only the first 11 numbers were assigned colours but you could continue to assign more colours for every 2^n above 11
                        break;
                    default:
                        ((TextView) findViewById(boxID)).setBackgroundColor(Color.parseColor("#ee235a"));
                }

            }
        }
    }


    private void onClick(View view) {
        finish();
    }

    private void onClick2(View view) {
        moveUp();
    }

    private void onClick3(View view) {
        moveDown();
    }

    private void onClick4(View view) {
        moveLeft();
    }

    private void onClick5(View view) {
        moveRight();
    }
}