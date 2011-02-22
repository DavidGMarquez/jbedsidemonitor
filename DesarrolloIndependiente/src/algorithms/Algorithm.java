package algorithms;

import signals.ReadResult;

public interface Algorithm {
    //@pendiente revisar si falta algo de la especificación de la memoria inicial.
    public String getIdentifier();

    public String getIdentifierSignalToWrite();

    public AlgorithmNotifyPolice getNotifyPolice();

    public boolean execute(ReadResult readResult);
    //Hacer metodos de interfaz para acceder y para poder saber lo que quiere, señales, señales que escribe, cada caunto
}
