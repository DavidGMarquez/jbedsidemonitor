package algorithms;

import signals.WriterRunnableTimeSeries;

class TimeSeriesTrigger {

    private String identifierSignal;
    private int lastSampleReportedToAlgorithm;
    private int newData;
    private int theshold;

    public TimeSeriesTrigger(String identifierSignal,int theshold) {
        this.identifierSignal=identifierSignal;
        this.theshold = theshold;
        this.lastSampleReportedToAlgorithm = -1;
        this.newData = 0;
    }

    public void update(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        System.out.println("Recibiendo datosen"+this.getIdentifierSignal()+
                "PAra"+writerRunnableTimeSeries.getIdentifier()+
                "sampleInit"+writerRunnableTimeSeries.getOlderSampleAvailable()+
                "size"+writerRunnableTimeSeries.getSamplesReadyToReadInOrder()+
                "getLast"+this.getLastSampleReported());
        if(writerRunnableTimeSeries.getIdentifier().equals(identifierSignal)){
            //@pendiente si la ultima muestra del agloritmo es mas nueva que la antigua hemos perdido datos
            if(writerRunnableTimeSeries.getOlderSampleAvailable()>(this.lastSampleReportedToAlgorithm+1))
            {
                //@pendiente esto esta perdiendo datos ha ocurrido algo malo
            }

            this.newData=(writerRunnableTimeSeries.getOlderSampleAvailable() +
                    writerRunnableTimeSeries.getSamplesReadyToReadInOrder())-(this.lastSampleReportedToAlgorithm+1);
        }
    }

    public boolean trigger() {
        if (this.newData > theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.lastSampleReportedToAlgorithm += this.newData;
        this.newData = 0;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }

    public int getLastSampleReported() {
        return lastSampleReportedToAlgorithm;
    }

    public int getNewData() {
        return newData;
    }

    public int getTheshold() {
        return theshold;
    }
}
