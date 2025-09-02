import itsc2214.*;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Project1Test {

    private Project1 runner;
    private Project1 runnerNoArgs;

    /**
     * setup() method, creates a game of life with 3x3 grid.
     */
    @Before
    public void setup() {
        runner = new Project1(3, 3);
        runnerNoArgs = new Project1();
    }

    /**
     * Checks basic setup of 3x3 and 0x0.
     */
    @Test
    public void testOne()
    {
        assertEquals(3, runner.numRows());
        assertEquals(3, runner.numCols());
        assertEquals(0, runnerNoArgs.numRows());
        assertEquals(0, runnerNoArgs.numCols());
    }

    /**
     * Test that the getAliveNeighbors and isAlive functions work.
     */
    @Test
    public void testNeighbors() {
        String inputData = 
        "3 3\n" + 
        "O..\n" +
        "...\n" +
        "O.O\n";
        runner.loadFromString(inputData);
        assertTrue("Cell 0,0 should be alive", runner.isAlive(0, 0));
        assertFalse("Cell 1,1 should be dead", runner.isAlive(1, 1));
        assertEquals("Cell 0,0 should have zero living neighbors", runner.countLiveNeighbors(0, 0), 0);
        assertEquals("Cell 2,2 should have 0 living neighbors", runner.countLiveNeighbors(2, 2), 0);
        assertEquals("Cell 1,1 should have 3 living neighbors", runner.countLiveNeighbors(1, 1), 3);
    }

    /**
     * Checks a basic initial grid.
     */
    @Test
    public void testSmallGrid()
    {
        String inputData =
            "3 3\n" +
            "O..\n" +
            "...\n" +
            "...\n";
        runner.loadFromString(inputData);
        // check position 0,0
        assertTrue("Position 0,0 should be alive", 
            runner.isAlive(0,0));
        // run generation
        runner.nextGeneration();
        // check position 0,0
        assertFalse("Position 0,0 should NOT be alive", 
            runner.isAlive(0,0));
    }

    /**
     * Test the still life checker, as well as the file loader
     * @throws FileNotFoundException If the file does not exist
     */
    @Test
    public void checkStillLife() throws FileNotFoundException {
        runner.loadFromFile("../empty.txt");
        runner.nextGeneration();
        runner.nextGeneration();
        runner.nextGeneration();
        assertTrue("An empty board should stay empty", runner.isStillLife());
    }

    /**
     * Test that my board copy function properly deep copies a 2d boolean array.
     */
    @Test
    public void testBoardCopy() {
        boolean[][] board1 = new boolean[3][3];
        boolean[][] board2 = new boolean[3][3];

        for (int y = 0; y < board1.length; y++)
            for (int x = 0; x < board1[y].length; x++)
                board1[y][x] = false;
        
        board2 = runner.copyBoard(board1);

        // assertEquals is deprecated for objects, so it is best practice not to use it
        assertTrue("The copied board should equal the original", Arrays.deepEquals(board1, board2));
        // changing something in board1 shouldn't affect board2 since it is a deep copy
        board1[2][1] = true;
        assertFalse("The changed board shouldn't equal the 2nd board", Arrays.deepEquals(board1, board2));

        boolean[][] emptyBoard = new boolean[0][1];
        boolean[][] emptyBoard2 = new boolean[1][0];
        boolean[][] emptyBoard3;
        emptyBoard3 = runner.copyBoard(emptyBoard);
        assertTrue("The height is zero so the return value should be null", emptyBoard3 == null);
        emptyBoard3 = runner.copyBoard(emptyBoard2);
        assertTrue("The width is zero so the return value should be null", emptyBoard3 == null);
    }

    /**
     * Test the random initialization function (This is kinda jank since the function doesn't have a predictable outcome)
     */
    @Test
    public void testRandomInit() {
        Project1 randomRunner = new Project1(10000, 10000);
        randomRunner.randomInitialize(0.5);
        double percentAlive = (double) randomRunner.numAlive() / (10000*10000);
        assertTrue("The sheer amount of cells means the percent alive should be close to the probability of living", (0.49 < percentAlive && percentAlive < 0.51));
    }
}
