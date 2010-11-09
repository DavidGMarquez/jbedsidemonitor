package signals;

import java.util.ArrayList;

public abstract class Series {

    private String identifier;
    private String agent = "simulated";
    private long origin;
    private ArrayList<String> seriesIsGeneratedFrom;

    protected Series(String identifier, String agent, long timeinit) {
        this.setIdentifier(identifier.trim());
        this.setAgent(agent.trim());
        this.setOrigin(timeinit);
        this.seriesIsGeneratedFrom = new ArrayList<String>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAgent() {
        return agent;
    }

    private boolean setAgent(String agent) {
        if (agent.isEmpty()) {
            this.agent = "simulated";
        } else {
            this.agent = agent.trim();
        }
        return true;
    }

    private boolean setIdentifier(String identifier) {
        this.identifier = identifier.trim();
        return true;
    }

    public long getOrigin() {
        return origin;
    }

    private void setOrigin(long timeinit) {
        this.origin = timeinit;
    }

    public ArrayList<String> getSeriesIsGeneratedFrom() {
        return new ArrayList<String>(seriesIsGeneratedFrom);
    }

    public void setSeriesIsGeneratedFrom(ArrayList<String> seriesIsGeneratedFrom) {
        this.seriesIsGeneratedFrom = new ArrayList<String>(seriesIsGeneratedFrom);
    }
}
