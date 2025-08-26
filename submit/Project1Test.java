import itsc2214.*;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class Project1Test {

    private Project1 runner;


    /**
     * setup() method, creates a game of life with 3x3 grid.
     */
    @Before
    public void setup() {
        runner = new Project1(3,3);
    }

    /**
     * Checks basic setup of 3x3.
     */
    @Test
    public void testOne()
    {
        assertEquals(3, runner.numRows());
        assertEquals(3, runner.numCols());
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
}
