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
}
