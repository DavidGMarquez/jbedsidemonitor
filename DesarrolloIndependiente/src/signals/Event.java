/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Event as a Immutable object
 * Represents an event.
 * As an immutable class can not be modified and is thread-safe
 * location is a long that represents the time of the event
 * type is a String that represent the type of the event
 * attributes is a Map that collect de pair of the Object that represent de attributes of the event
 *
 * @author USUARIO
 */
//@@comentario ahora implementa la clase Comparable y los eventos se ordenan de acuerdo con
//su localizacion
public final class Event implements Comparable<Event> {

    private final long location;
    private final String type;
    private final Map<Object, Object> attributes;

    public Event(long location, String type, Map<Object, Object> attributes) {
        this.location = location;
        this.type = type.trim();
        //@duda añado esto para que me permita inicializar eventos sin atributos
        if(attributes!=null)
        this.attributes = new HashMap<Object, Object>(attributes);
        else
            this.attributes=null;
    }

    public Map<Object, Object> getAttributes() {
        return new HashMap<Object, Object>(this.attributes);
    }

    public long getMoment() {
        return location;
    }

    public String getType() {
        return type;
    }

    public int compareTo(Event o) {
        return (int) (location - o.location);
    }
    //@duda supongo que esto será para luego ordenar
    //pero esta bien que digamso que dos eventos son iguales
    //porque sean en el mismo instante aun cuando pueden ser de distintos tipos?
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event e = (Event) o;
        return e.location == location;
    }
}
