package algorithms;

import signals.WriterRunnableTimeSeries;

class DataLossException extends RuntimeException {

    private WriterRunnableTimeSeries writerRunnableTimeSeries;

    public DataLossException(String message,WriterRunnableTimeSeries writerRunnableTimeSeries) {
        super(message+" Signal:"+writerRunnableTimeSeries.getIdentifier());
        this.writerRunnableTimeSeries=writerRunnableTimeSeries;
    }

    public WriterRunnableTimeSeries getWriterRunnableTimeSeries() {
        return writerRunnableTimeSeries;
    }

}
