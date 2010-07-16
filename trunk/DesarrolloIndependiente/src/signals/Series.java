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
    private String agent = "simulated";//comentario así nunca nos vamos a olvidar
    private long origin;

    protected Series(String identifier,String agent,long timeinit)    {
        this.setIdentifier(identifier.trim());
        this.setAgent(agent.trim());
        this.setOrigin(timeinit);
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
}
