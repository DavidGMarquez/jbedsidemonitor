/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import integration.AlgorithmManager;
import java.util.ArrayList;
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
public class AlgorithmManagerTest {

    public AlgorithmManagerTest() {
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
     * Test of addAlgorithm method, of class AlgorithmManager.
     */
    @Test
    public void testAddAlgorithm() {
        System.out.println("addAlgorithm");
        Algorithm algorithm = new AlgorithmImpl("Algorithm_1", null, "Out_Algorithm_1");
        AlgorithmManager instance = AlgorithmManager.getInstance();
        assertEquals(instance.getAllAlgorithms().size(),0);
        boolean expResult = true;
        boolean result = instance.addAlgorithm(algorithm);
        assertEquals(expResult, result);
        assertEquals(instance.getAllAlgorithms().size(),1);
        assertEquals(algorithm,instance.getAlgorithm(0));
        


    }
    @Test
    public void testInstance(){
        AlgorithmManager instance1=AlgorithmManager.getInstance();
        AlgorithmManager instance2=AlgorithmManager.getInstance();
        AlgorithmManager instance3=AlgorithmManager.getInstance();
        assertEquals(instance1,instance2);
        assertEquals(instance3, AlgorithmManager.getInstance());
        instance1.addAlgorithm(new AlgorithmImpl("Algorithm_1", null, "Out_Algorithm_1"));
        assertEquals(instance1,AlgorithmManager.getInstance());        

    }

        @Test
    public void testCompleteAlgorithm() {
        System.out.println("addAlgorithm");
        Algorithm algorithm1 = new AlgorithmImpl("Algorithm_1", null, "Out_Algorithm_1");
        Algorithm algorithm2 = new AlgorithmImpl("Algorithm_2", null, "Out_Algorithm_2");
        Algorithm algorithm3 = new AlgorithmImpl("Algorithm_3", null, "Out_Algorithm_3");
        AlgorithmManager instance = AlgorithmManager.getInstance();
        assertEquals(instance.getAllAlgorithms().size(),0);
        boolean expResult = true;
        boolean result = instance.addAlgorithm(algorithm1);
        assertEquals(expResult, result);
         result = instance.addAlgorithm(algorithm2);
         assertEquals(expResult, result);
        result = instance.addAlgorithm(algorithm3);
        result = instance.addAlgorithm(algorithm3);
        assertEquals(expResult, result);
        assertEquals(instance.getAllAlgorithms().size(),3);
        assertEquals(algorithm1,instance.getAlgorithm(0));



    }

    /**
     * Test of getAlgorithm method, of class AlgorithmManager.
     */
 

}