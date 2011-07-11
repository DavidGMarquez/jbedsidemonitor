package algorithms;
public class AlgorithmExecutionInfo {
         private String identifierAlgorithm;
         private Long numberOfTriggerUpdates;
         private Long numberOfExecutions;

    public AlgorithmExecutionInfo(String identifierAlgorithm) {
        this.identifierAlgorithm = identifierAlgorithm;
        numberOfExecutions=new Long(0);
        numberOfTriggerUpdates=new Long(0);
    }
    public synchronized void incrementTriggerUpdates()
    {
        this.numberOfTriggerUpdates=new Long(numberOfTriggerUpdates.longValue()+1);
    }
    public synchronized void incrementExecutionUpdates()
    {
        this.numberOfExecutions=new Long(numberOfExecutions.longValue()+1);
    }

    public String getIdentifierAlgorithm() {
        return identifierAlgorithm;
    }

    public synchronized Long getNumberOfExecutions() {
        return numberOfExecutions;
    }

    public synchronized Long getNumberOfTriggerUpdates() {
        return numberOfTriggerUpdates;
    }



}
