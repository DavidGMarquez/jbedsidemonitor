package signals;

public abstract class ReadResult {
    
    protected String identifierOwner;
  
    public ReadResult(String identifierOwner) {
        this.identifierOwner = identifierOwner;       
    }

    public String getIdentifierOwner() {
        return identifierOwner;
    }

}
