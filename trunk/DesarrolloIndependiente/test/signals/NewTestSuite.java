package signals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({signals.EvenSeriesTest.class,
    signals.CircularBufferTest.class,
    signals.TesterWriterService.class,
    signals.TimeSeriesTest.class,
    signals.SignalManagerTest.class,
    signals.TesterReaderService.class,
    signals.EventTest.class})
public class NewTestSuite {
}
