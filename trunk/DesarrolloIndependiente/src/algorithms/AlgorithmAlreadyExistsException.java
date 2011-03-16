package algorithms;

class AlgorithmAlreadyExistsException extends RuntimeException {

    private String message;
    private Algorithm algorithm;

    public AlgorithmAlreadyExistsException(String message,Algorithm algorithm) {
        super(message+" Algorithm:"+algorithm.getIdentifier());
        this.message = message;
        this.algorithm=algorithm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
