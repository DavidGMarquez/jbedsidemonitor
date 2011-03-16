package algorithms;

import signals.WriterRunnableTimeSeries;

class TimeSeriesTrigger {

    private String identifierSignal;
    private int lastSampleReported;
    private int newData;
    private int theshold;

    public TimeSeriesTrigger(String identifierSignal,int theshold) {
        this.identifierSignal=identifierSignal;
        this.theshold = theshold;
        this.lastSampleReported = 0;
        this.newData = 0;
    }

    public void update(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        if(writerRunnableTimeSeries.getIdentifier().equals(identifierSignal))
            this.newData += writerRunnableTimeSeries.getDataToWrite().length;
    }

    public boolean trigger() {
        if (this.newData > theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.lastSampleReported += this.newData;
        this.newData = 0;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }

    public int getLastSampleReported() {
        return lastSampleReported;
    }

    public int getNewData() {
        return newData;
    }

    public int getTheshold() {
        return theshold;
    }
}
