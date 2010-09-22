/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 *
 * @author USUARIO
 */

    public class AlgorithmImpl implements Algorithm {
        String identifier;
        AlgorithmReadSubscription algorithmReadSubscription;
        String identifierSignalToWrite;

        public AlgorithmImpl(String identifier, AlgorithmReadSubscription algorithmReadSubscription, String identifierSignalToWrite) {
            this.identifier = identifier;
            this.algorithmReadSubscription = algorithmReadSubscription;
            this.identifierSignalToWrite = identifierSignalToWrite;
        }


        public String getIdentifier() {
            return identifier;
        }

        public AlgorithmReadSubscription getReadSubscription() {
            return algorithmReadSubscription;
        }

        public String getIdentifierSignalToWrite() {
            return identifierSignalToWrite;
        }

        public boolean execute(AlgorithmExecutionContext ExCon) {
            return false;
        }
    }