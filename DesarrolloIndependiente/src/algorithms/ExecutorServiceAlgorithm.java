package algorithms;

import signals.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceAlgorithm {

    private final ExecutorService executorService;

    public ExecutorServiceAlgorithm() {
        executorService = Executors.newCachedThreadPool();
    }

    public void executeAlgorithmReadResult(Algorithm algorithm, ReadResult readResult) {
    //@debug    System.out.println("----Recibiendo en Algorithm Manager para " + readResult.getIdentifierOwner());
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
        //@debug   System.out.println("Multisignal");
        }
        if (readResult instanceof ReadResultTimeSeries) {
            ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResult;
   //@debug     System.out.println("TimeSeries"+readResultTimeSeries.getIdentifierSignal());
    //@debug       System.out.println("N"+readResultTimeSeries.getData().length);
        }
        if (readResult instanceof ReadResultEventSeries) {
            ReadResultEventSeries readResultEventSeries = (ReadResultEventSeries) readResult;
  //@debug          System.out.println("EventSeries"+readResultEventSeries.getIdentifierSignal());
 //@debug           System.out.println("N" + readResultEventSeries.getEventsRead().size());
        }
   //@debug     System.out.println("Haciendo un AlgorithmRunner");
        this.executorService.execute(new AlgorithmRunner(algorithm, readResult));
    }
}
