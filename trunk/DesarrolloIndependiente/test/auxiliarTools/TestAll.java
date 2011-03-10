/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliarTools;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({signals.EvenSeriesTest.class,
    signals.CircularBufferTest.class,
    signals.TesterWriterService.class,
    signals.TimeSeriesTest.class,
    signals.SignalManagerTest.class,
    signals.TesterReaderService.class,
    signals.EventTest.class,
    signals.CompletionExecutorServiceReaderTest.class,
    signals.LockManagerTest.class,
    algorithms.AlgorithmNotifyPoliceTest.class,
    algorithms.EventSeriesTriggerTest.class,
    algorithms.TimeSeriesTriggerTest.class,
    algorithms.TriggerTest.class,
    algorithms.AlgorithmDefaultImplementationTest.class,
    algorithms.AlgorithmManagerTest.class})
public class TestAll {
}
