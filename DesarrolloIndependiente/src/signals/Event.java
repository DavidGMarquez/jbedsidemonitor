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
 */
public final class Event implements Comparable<Event> {

    private final long location;
    private final String type;
    private final Map<String, String> attributes;

    public Event(long location, String type, Map<String, String> attributes) {
        this.location = location;
        this.type = type.trim();
        if (attributes != null) {
            this.attributes = new HashMap<String, String>(attributes);
        } else {
            this.attributes = new HashMap<String, String>();
        }
    }

    public Map<String, String> getCopyOfAttributes() {
        return new HashMap<String, String>(this.attributes);
    }

    public long getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Event o) {
        if (location - o.location == 0) {
            return type.compareToIgnoreCase(o.type);
        } else {
            return (int) (location - o.location);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event e = (Event) o;
        return e.location == location && e.type.equalsIgnoreCase(type);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + type.hashCode();
        result = 37 * result + ((int) (location ^ (location >>> 32)));
        return result;
    }
    public Event copy(){
        return new Event(this.location, this.type, this.attributes);
    }
}
