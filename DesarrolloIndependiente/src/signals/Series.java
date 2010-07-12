/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author USUARIO
 */
public abstract class Series {
    //Parametros generales

    private String identifier;
    private String agent;

    public String getIdentifier() {
        return identifier;
    }

    public String getAgent() {
        return agent;
    }

    public boolean setAgent(String agent) {
        if (agent.isEmpty()) {
            this.agent = "simulated";
        } else {
            this.agent = agent;
        }
        return true;
    }

    public boolean setIdentifier(String identifier) {
        if (identifier.startsWith(" ") || identifier.startsWith("  ") || identifier.endsWith(" ") || identifier.endsWith("  ")) {
            return false;
        } else {
            this.identifier = identifier;
        }
        return true;
    }
}
