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

    //@debug    System.out.println("Recibiendo datosen"+this.getIdentifierSignal()+
      //@debug          "PAra"+writerRunnableTimeSeries.getIdentifier()+
      //@debug          "sampleInit"+writerRunnableTimeSeries.getOlderSampleAvailable()+
      //@debug          "size"+writerRunnableTimeSeries.getSamplesReadyToReadInOrder()+
      //@debug          "getLast"+this.getLastSampleReported());

        if(writerRunnableTimeSeries.getIdentifier().equals(identifierSignal)){
            //@pendiente si la ultima muestra del agloritmo es mas nueva que la antigua hemos perdido datos
            if(writerRunnableTimeSeries.getOlderSampleAvailable()>(this.lastSampleReportedToAlgorithm+1))
            {
                //@pendiente esto esta perdiendo datos ha ocurrido algo malo
                //@pendiente newData puede estar a negativo si se desordenan
            }
     //@debug       System.out.println("Añadimos datos al trigger estaba en "+this.newData+"Ponemos a"+((writerRunnableTimeSeries.getOlderSampleAvailable() +
     //@debug               writerRunnableTimeSeries.getSamplesReadyToReadInOrder())-(this.lastSampleReportedToAlgorithm+1))+"limite"+this.getTheshold());
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
