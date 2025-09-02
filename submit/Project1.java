import itsc2214.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

/**
 * Implements Conway's Game of Life using a 2D array.
 */
public class Project1 implements GameOfLife {
    private int rows;
    private int cols;

    private boolean[][] previousBoard;
    private boolean[][] currentBoard;

    /**
     * Initialize the board with a width and height of zero.
     */
    public Project1() {
        rows = 0;
        cols = 0;
    }

    /**
     * Initialize the board with a set width and height.
     * @param rows The number of rows, or the width, of the board
     * @param cols The number of columns, or the height, of the board
     */
    public Project1(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    // TODO Implement all the methods fron the GameOfLife

    /**
     * Check if the cell at the indicated index is alive.
     * @param row the row of the cell to check, starting from index 0
     * @param column the column of the cell to check, starting from index 0
     * @return a boolean value of whether or not the cell is alive
     */
    @Override
    public boolean isAlive(int row, int column) {
        return currentBoard[row][column];
    }

    /**
     * Deep copy a 2d boolean array.
     * @param board the array you want to be deep copied
     * @return the deep copy of the board array
     */
    public boolean[][] copyBoard(boolean[][] board) {
        // if the board has a width or height of 0, do nothing
        if (board.length == 0 || board[0].length == 0) return null;
        boolean[][] temp = new boolean[board.length][board[0].length];
        // loop through the board and copy each cell into the temporary board
        for (int y = 0; y < numRows(); y++)
            for (int x = 0; x < numCols(); x++)
                temp[y][x] = board[y][x];
        return temp;
    }

    /**
     * Initialize the board with each cell have a random chance of being alive.
     * @param probability The probability any given cell is alive
     */
    @Override
    public void randomInitialize(double probability) {
        currentBoard = new boolean[rows][cols];
        previousBoard = new boolean[rows][cols];
        // loop through every cell in current board and randomly decide if it lives
        for (int y = 0; y < numRows(); y++)
            for (int x = 0; x < numCols(); x++)
                currentBoard[y][x] = Math.random() <= probability;
        // ensure that previous board is initialized
        previousBoard = copyBoard(currentBoard);
    }

    /**
     * Counts the living neighbors of a cell.
     * @param row The row of the cell, starting with an index of 0
     * @param col The column of the cell, starting with an index of 0
     * @return The number of living neighbors a cell has
     */
    @Override
    public int countLiveNeighbors(int row, int col) {
        int alive = 0;
        // loop through all neighboring cells, and if a cell is alive, add one to the count
        for (int y = row - 1; y <= row + 1; y++)
            for (int x = col - 1; x <= col + 1; x++) {
                // If the check would go out of bounds, go to next iteration
                if (y < 0 || x < 0 || y >= rows || x >= cols) continue;
                // if on current cell, go to next iteration 
                if (y == row && x == col) continue;
                if (isAlive(y, x)) alive++;
            }
        return alive;
    }

    /**
     * get the number of columns on the board
     * @return the number of columns on the board
     */
    @Override
    public int numCols() {
        return cols;
    }

    /**
     * Get the number of rows on the board
     * @return the number of rows on the board
     */
    @Override
    public int numRows() {
        return rows;
    }

    /**
     * Check if this generation was a still life, meaning it didn't change between generations
     * @return Whether or not the previous and current boards match
     */
    @Override
    public boolean isStillLife() {
        return Arrays.deepEquals(previousBoard, currentBoard);
    }

    @Override
    public void loadFromFile(String file) throws FileNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadFromString(String boardString) {
        Scanner scan = new Scanner(boardString);
        rows = scan.nextInt();
        cols = scan.nextInt();
        scan.nextLine();
        currentBoard = new boolean[rows][cols];
        previousBoard = new boolean[rows][cols];
        int y = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            System.out.println(line);
            for (int x = 0; x < line.length(); x++) {
                currentBoard[y][x] = line.charAt(x) == 'O';
            }
            y++;
        }
        scan.close();
        for (boolean[] cols : currentBoard)
            for (boolean cell : cols)
                System.out.println(cell);
    }

    @Override
    public void nextGeneration() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Get number of living cells (used for testing)
     * @return the number of still alive cells
     */
    public int numAlive() {
        int alive = 0;
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                alive += currentBoard[y][x] ? 1 : 0;
        return alive;
    }
}