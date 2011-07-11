package algorithms;

class AlgorithmAlreadyExistsException extends RuntimeException {

    private Algorithm algorithm;

    public AlgorithmAlreadyExistsException(String message,Algorithm algorithm) {
        super(message+" Algorithm:"+algorithm.getIdentifier());
        this.algorithm=algorithm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
