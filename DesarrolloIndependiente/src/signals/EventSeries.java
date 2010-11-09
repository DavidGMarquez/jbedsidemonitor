package signals;

import java.util.*;

public class EventSeries extends Series {

    //Parametros serie temporal
    private SortedSet<Event> events;
    private String units;

    public EventSeries(String identifier, String agent, long timeinit, ArrayList<String> imputs, String units) {
        super(identifier, agent, timeinit);
        super.setSeriesIsGeneratedFrom(new ArrayList<String>(imputs));
        this.units = units.trim();
        this.events = new TreeSet<Event>();
    }

    public Set<String> getEventTypes() {
        final HashSet<String> eventTypes = new HashSet<String>();
        for (Event event : events) {
            eventTypes.add(event.getType());
        }
        return eventTypes;
    }

    public long getFirstevent() {

        return events.first().getLocation();
    }

    public long getLastevent() {
        return events.last().getLocation();
    }

    public String getUnits() {
        return units;
    }

    public SortedSet<Event> getEvents(long firstInstantToInclude, long lastInstantToInclude) {
        Event eventFrom = new Event(firstInstantToInclude, "", null);
        Event eventTo = new Event(lastInstantToInclude + 1, "", null);
        return new TreeSet<Event>(events.subSet(eventFrom, eventTo));
    }

    public int getNumberOfEvents() {
        return this.events.size();
    }

    public ArrayList<Event> getEventsCopy() {
        return new ArrayList<Event>(this.events);
    }

    public boolean addEvent(Event event) {
        return this.events.add(event);
    }

    public boolean deleteEventsAtLocation(long location) {
        return events.removeAll(this.getEvents(location, location));
    }

    public boolean deleteEvent(Event eventToDelete) {
        return events.remove(eventToDelete);
    }
    //@hacer borrar un rango, borrar por tipo, borrar todo
}
