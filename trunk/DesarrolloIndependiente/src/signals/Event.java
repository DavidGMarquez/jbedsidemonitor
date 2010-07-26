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
//@comentario ahora implementa la clase Comparable y los eventos se ordenan de acuerdo con
//su localizacion
public final class Event implements Comparable<Event> {

    private final long location;
    private final String type;
    private final Map<String, String> attributes;
    //@hacer cambiar a String String y buscar un metodo para copia, y comprobar en los test, y cambiar para que devuelva null en get
    //@hacer habría que sobreescribir .hashCode

    public Event(long location, String type, Map<String, String> attributes) {
        this.location = location;
        this.type = type.trim();
        if(attributes!=null)

        this.attributes = new HashMap<String, String>(attributes);
        else
            this.attributes=null;
    }

    public Map<String, String> getAttributes() {
        return new HashMap<String, String>(this.attributes);
    }

    public long getMoment() {
        return location;
    }

    public String getType() {
        return type;
    }

    public int compareTo(Event o) {
        //return ((int) (location - o.location)+(int) (type.hashCode()-o.type.hashCode()));
        if(location-o.location==0)
        {
         return type.compareToIgnoreCase(o.type);
        }
        else
        {
        return (int) (location - o.location);
        }
    }
    //@duda supongo que esto será para luego ordenar
    //pero esta bien que digamso que dos eventos son iguales
    //porque sean en el mismo instante aun cuando pueden ser de distintos tipos?
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event e = (Event) o;
        return e.location == location && e.type.equalsIgnoreCase(type);
    }
    public int hashCode(){
        int result=17;
        result=37*result+type.hashCode();
        result=37*result+((int)(location^(location>>>32)));
        return result;
    }
}
