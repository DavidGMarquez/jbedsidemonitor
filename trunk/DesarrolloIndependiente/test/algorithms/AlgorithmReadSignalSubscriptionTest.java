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
public class AlgorithmReadSignalSubscriptionTest {

    public AlgorithmReadSignalSubscriptionTest() {
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
     * Test of getDataCounter method, of class AlgorithmReadSignalSubscription.
     */
    @Test
    public void testGetDataCounter() {
        System.out.println("getDataCounter");
        AlgorithmReadSignalSubscription instance = new AlgorithmReadSignalSubscription(0, 0);
        int expResult = 0;
        int result = instance.getDataCounter();
        assertEquals(expResult, result);

    }

    /**
     * Test of getLastIndexRead method, of class AlgorithmReadSignalSubscription.
     */
    @Test
    public void testGetLastIndexRead() {
        System.out.println("getLastIndexRead");
        AlgorithmReadSignalSubscription instance = new AlgorithmReadSignalSubscription(0, 0);
        int expResult = 0;
        int result = instance.getLastIndexRead();
        assertEquals(expResult, result);

    }

}