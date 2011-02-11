package algorithms;

import signals.ReadResult;

public class AlgorithmDefaultImplementation implements Algorithm {

    private String identifier;
    private String identifierSignalToWrite;
    private AlgorithmNotifyPolice algorithmNotifyPolice;

    public AlgorithmDefaultImplementation(String identifier, String identifierSignalToWrite, AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.identifier = identifier;
        this.identifierSignalToWrite = identifierSignalToWrite;
        this.algorithmNotifyPolice = algorithmNotifyPolice;
    }

    public AlgorithmNotifyPolice getNotifyPolice() {
        //@pendiente asegurarse de que no hay que pasar una copia
        return this.algorithmNotifyPolice;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierSignalToWrite() {
        return this.identifierSignalToWrite;
    }

    public boolean execute(ReadResult readResult) {
        throw new UnsupportedOperationException("Not supported yet.");
        //Implementar aqui
    }
    //@duda habría que hacer algún método para permitir al algoritmo decir cuando quiere grabar algo
}
