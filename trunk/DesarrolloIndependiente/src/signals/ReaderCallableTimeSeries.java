package signals;

public class ReaderCallableTimeSeries extends ReaderCallableOneSignal {

    private int posInitToRead;
    private int sizeToRead;

    public ReaderCallableTimeSeries(String identifierSignal, String identifierOwner) {
        super(identifierSignal, identifierOwner);
    }

    public ReaderCallableTimeSeries(String identifierSignal, String identifierOwner, int posInitToRead, int sizeToRead) {
        super(identifierSignal, identifierOwner);
        this.posInitToRead = posInitToRead;
        this.sizeToRead = sizeToRead;
    }


    @Override
    protected ReadResult read() {
        SignalManager signalManager = SignalManager.getInstance();
        System.out.println(">>>>>>>>>>>>>EJECUTANDO READER CALLABLE<"+identifierSignal+" Desde "+posInitToRead+" Size:"+sizeToRead+" Para"+identifierOwner);
        this.readResult = new ReadResultTimeSeries(identifierOwner, identifierSignal,
                signalManager.readFromTimeSeries(identifierSignal, posInitToRead, sizeToRead), posInitToRead);
        return readResult;
    }

    public void setPosInitToRead(int posInitToRead) {
        this.posInitToRead = posInitToRead;
    }

    public void setSizeToRead(int sizeToRead) {
        this.sizeToRead = sizeToRead;
    }

    public int getPosInitToRead() {
        return posInitToRead;
    }

    public int getSizeToRead() {
        return sizeToRead;
    }

}
