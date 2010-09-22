/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class AlgorithmTest {

    public AlgorithmTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getIdentifier method, of class Algorithm.
     */
    @Test
    public void testGetIdentifier() {
        System.out.println("getIdentifier");
        Algorithm instance = new AlgorithmImpl("Algorithm_1", null, "Out_Algorithm_1");
        String expResult = "Algorithm_1";
        String result = instance.getIdentifier();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReadSubscription method, of class Algorithm.
     */
    @Test
    public void testGetIdentifierSignalToWrite() {
        System.out.println("getIdentifierSignalToWrite");
       Algorithm instance = new AlgorithmImpl("Algorithm_1", null, "Out_Algorithm_1");
        String expResult = "Out_Algorithm_1";
        String result = instance.getIdentifierSignalToWrite();
        assertEquals(expResult, result);
    }

    /**
     * Test of execute method, of class Algorithm.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        AlgorithmExecutionContext ExCon = null;

    }


}