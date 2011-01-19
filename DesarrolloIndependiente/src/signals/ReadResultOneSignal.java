package signals;

abstract class ReadResultOneSignal extends ReadResult {
    protected String identifierSignal;

    public ReadResultOneSignal(String identifierOwner, String identifierSignal) {
        super(identifierOwner);
        this.identifierSignal = identifierSignal;
    }
    public String getIdentifierSignal() {
        return identifierSignal;
    }
}
