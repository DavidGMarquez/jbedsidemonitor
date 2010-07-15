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
 * Me quedan dudas a la hora de devolver los atributos yo creo que de esta forma es correcto pero tendría que comprobarlo
 * @author USUARIO
 */
public final class Event {
    private final long location;
    private final String type;
    private final Map<Object,Object> attributes;

    public Event(long moment, String type, Map<Object, Object> attributes) {
        this.location = moment;
        this.type = type.trim();
        this.attributes = new HashMap<Object,Object>(attributes);
    }
    public Map<Object, Object> getAttributes() {
        return new HashMap<Object,Object>(this.attributes);
    }

    public long getMoment() {
        return location;
    }

    public String getType() {
        return type;
    }



}
