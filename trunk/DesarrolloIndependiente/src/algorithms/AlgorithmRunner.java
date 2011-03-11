package algorithms;

import signals.ReadResult;

class AlgorithmRunner implements Runnable {

    Algorithm algorithm;
    ReadResult readResult;

    public AlgorithmRunner(Algorithm algorithm, ReadResult readResult) {
        this.algorithm = algorithm;
        this.readResult = readResult;
    }

    public void run() {
//        algorithm.execute(readResult);
    }
}
