/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 *  
 * @author USUARIO
 */
public abstract class Series {
    //Parametros generales

    private String identifier;
    private String agent;
    private long timeinit;

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

    public long getTimeinit() {
        return timeinit;
    }

    private void setTimeinit(long timeinit) {
        this.timeinit = timeinit;
    }
    
    protected Series(String identifier,String agent,long timeinit)
    {
        this.setIdentifier(identifier.trim());
        this.setAgent(agent.trim());
        this.setTimeinit(timeinit);
    }
}
