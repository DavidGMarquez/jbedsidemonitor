/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

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
public class SignalManagerTest {

    public SignalManagerTest() {
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
     * Test of getInstance method, of class SignalManager.
     */
    @Test
    public void testCrear() {
      
        SignalManager signalManager = SignalManager.getInstance();
        SignalManager aaa=SignalManager.getInstance();

        assertEquals(aaa, signalManager);
        // TODO review the generated test code and remove the default call to fail.
    }


}