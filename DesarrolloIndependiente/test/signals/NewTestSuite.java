/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author USUARIO
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({signals.EvenSeriesTest.class,signals.CircularBufferTest.class,signals.TesterWriterService.class,signals.TimeSeriesTest.class,signals.SignalManagerTest.class,signals.TesterReaderService.class,signals.EventTest.class})
public class NewTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}