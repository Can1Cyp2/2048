package com.example.a2048;

import java.util.Random;

public class Gameplay {

    public static final int GRIDSIZE = 4; // Size of default grid
    public static int[][] grid = new int[GRIDSIZE][GRIDSIZE];  // Creating base grid


    public void makeGrid(int gridSize){
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

    public boolean isSpotEmpty(int x, int y){
        // If spot in grid is equal to 0 it is empty, function returns true
        return grid[x][y] == 0; // If the spot has a number > 0, return false
    }


    // Returns number of available spots left
    public int availableSpots() {
        int availableCount = 0; // Number of empty spaces

        // Checks if spaces on grid are empty
        for (int x = 0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                if (grid[x][y] == 0) availableCount++;

            }
        }

        return availableCount;
    }

    // Checks if there are any empty spots and possible spots for the player to move
    public boolean endGame(){
        // TODO Finish end game function by seeing if there are possible moves for player

        //if (availableSpots() > 0)

        return false;
    }


    // Fills a random spot on the board with a 2 or 4
    public void fillRandomSpot(){
        Random rand = new Random();
        int x, y;

        // Finds an empty space on the grid
        do {

            x = rand.nextInt(GRIDSIZE);
            y = rand.nextInt(GRIDSIZE);

        } while(isSpotEmpty(x, y));
    }

    /* ****************************** MOVEMENT OF GRID PIECES: ******************************* */

    // moves grid pieces to the right
    public void moveRight(){
    // moving down the double array, to move right
      for (int x = 2; x >= 0; x--){
            for (int y = 0; y < 4; y++){

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] != 0) break;

                // Moving the numbers
                int z = x; // Checking for z in place of x:
                while (z < 3 && grid[z + 1][y] == 0){
                    grid[z + 1][y] = grid[z][y];
                    grid[z][y] = 0;
                    z++;
                }
                if (z < 3){
                    if (grid[z + 1][y] == grid[z][y]){
                        grid[z + 1][y] *= 2;
                        grid[z][y] = 0;
                    }
                }
            }
        }
    }

    // moves grid pieces to the left
    public void moveLeft(){
        // moving up the double array, to move left
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] != 0) break;

                // Moving the numbers left
                int z = x; // Checking for z in place of x:
                while (z > 0 && grid[z - 1][y] == 0){
                    grid[z - 1][y] = grid[z][y];
                    grid[z][y] = 0;
                    z--;
                }

                if (z > 0){
                    if (grid[z - 1][y] == grid[z][y]){
                        grid[z - 1][y] *= 2;
                        grid[z][y] = 0;
                    }
                }
            }
        }
    }

    // Moves the grid pieces up
    public void moveUp() {
        // Moving grid pieces up the double array by moving left in the array
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] != 0) break;
                
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
    }

    // Moves the grid pieces down
    public void moveDown() {
        // Moving grid pieces down by moving right in the double array
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){

                // If the space is not empty break out of the loop, do not move the number
                if (grid[x][y] != 0) break;

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
    }

    /* *************************** ^^ MOVEMENT OF GRID PIECES ^^ ******************************* */

}
