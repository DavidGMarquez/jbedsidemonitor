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
        this.executorService.execute(new AlgorithmRunner(algorithm, readResult));
    }
}
