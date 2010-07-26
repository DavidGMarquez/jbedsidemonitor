/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.*;

/**
 * @author USUARIO
 */
public class EventSeries extends Series {

    //Parametros serie temporal
    private SortedSet<Event> events;//@comentario utiliza la coleccion adecuada para cada trabajo
    private ArrayList<String> seriesIsGeneratedFrom;
    private String units;

    public EventSeries(String identifier, String agent, long timeinit, ArrayList<String> imputs, String units) {
        super(identifier, agent, timeinit);
        this.seriesIsGeneratedFrom = new ArrayList<String>(imputs);
        this.units = units.trim();
        this.events = new TreeSet<Event>();
    }


    public ArrayList<String> getSeriesIsGeneratedFrom() {
        return new ArrayList<String>(seriesIsGeneratedFrom);
    }

    //visto lo visto, creo que es preferible generar esta informacion Cada vez que se pida
    //sera menos eficiente, pero es mucho más facil
    //respecto a la cuenta del numero de eventos de cada tipo, no tengo claro
    //que sea util Y no merece la pena ensuciar el codigo con ella. Si alguien lo necesita que lo calcule
    public Set<String> getEventTypes() {
        final HashSet<String> eventTypes = new HashSet<String>();
        for (Event event : events) {
            eventTypes.add(event.getType());
        }
        return eventTypes;
    }

    public long getFirstevent() {

        return events.first().getMoment();
    }

    public long getLastevent() {
        return events.last().getMoment();
    }

    public String getUnits() {
        return units;
    }
    
    public SortedSet<Event> getEvents(long firstInstantToInclude, long lastInstantToInclude) {
        Event eventFrom = new Event (firstInstantToInclude,"",null);
        Event eventTo = new Event (lastInstantToInclude+1,"",null);
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
//@duda lo mismo que antes. No puede ser que haya dos eventos en el mismo isntante
    //y solo queramos tratar uno de ellos?
    public boolean deleteEventsAtLocation(long location) {
        return events.removeAll(this.getEvents(location, location));
    }

    public boolean deleteEvent(Event eventToDelete) {
        return events.remove(eventToDelete);
    }
    //@hacer borrar un rango, borrar por tipo, borrar todo
}
