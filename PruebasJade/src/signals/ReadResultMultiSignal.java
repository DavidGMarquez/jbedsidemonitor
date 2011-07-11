package signals;

import java.util.LinkedList;

public class ReadResultMultiSignal extends ReadResult {

    private LinkedList<ReadResultOneSignal> readResults;

    public ReadResultMultiSignal(String identifierOwner) {
        super(identifierOwner);
        readResults=new LinkedList<ReadResultOneSignal>();
    }

    //Estamos dejando escapar la referencia sin copias defensivas
   public LinkedList<ReadResultOneSignal> getReadResults() {
        return readResults;
    }

    public void addReadResultOneSignal(ReadResultOneSignal readResultOneSignal) {
        this.readResults.add(readResultOneSignal);
    }
}
