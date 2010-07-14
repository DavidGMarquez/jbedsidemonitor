/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class Event {
    private long location;
    private String type;
    private Map<Object,Object> attributes;

    public Event(int moment, String type) {
        this.location = moment;
        this.type = type;
         attributes = new HashMap<Object,Object>();
    }

    public Event(String type) {
        this.type = type;
        this.location=System.currentTimeMillis();
        attributes = new HashMap<Object,Object>();
    }

    public Event(long moment, String type, Map<Object, Object> attributes) {
        this.location = moment;
        this.type = type;
        this.attributes = attributes;
    }
    public Event(String type, Map<Object, Object> attributes) {
        this.location = System.currentTimeMillis();
        this.type = type;
        this.attributes = attributes;
    }
    //Aqui estamos permitiendo escapar a la referencia. En un principio no debería ser asi
    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public long getMoment() {
        return location;
    }

    public String getType() {
        return type;
    }



}
