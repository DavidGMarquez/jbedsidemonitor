/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * No habría que devolver el evento al pedir uno sino una copia.
 * Primer evento es el primero en tiempo? o el primero de la lista? igual para el útlimo
 * Los unicos metodos que modifican deberían ser addEvent y deleteEvent
 * @author USUARIO
 */
public class NoTemporalSeries extends Series {

    //Parametros serie temporal
    private ArrayList<Event> events;
    private ArrayList<String> imputs;
    private long firstevent;
    private long lastevent;
    private String units;
    private Map<String, Long> typeevents;

    public NoTemporalSeries(String identifier, String agent, long timeinit, ArrayList<String> imputs, String units) {
        super(identifier, agent, timeinit);
        this.imputs = new ArrayList<String>(imputs);
        this.units = units.trim();
        this.firstevent = Long.MAX_VALUE;
        this.lastevent = Long.MIN_VALUE;
        this.typeevents = new HashMap<String, Long>();
        this.events = new ArrayList<Event>();


    }


    public ArrayList<String> getImputs() {
        return new ArrayList<String>(imputs);
    }

    public Map<String, Long> getTypeevents() {
        return new HashMap<String, Long>(typeevents);
    }


    public long getFirstevent() {
        return firstevent;
    }

    public long getLastevent() {
        return lastevent;
    }

    public String getUnits() {
        return units;
    }

    public Event getEvent(int index) {
        Event e=this.events.get(index);
        return e;
    }
    public int getSizeEvents() {
        return this.events.size();
    }
        public ArrayList<Event> getEventsCopy() {
        return new ArrayList<Event>(this.events);
    }


    public boolean addEvent(Event even) {
        if (this.events.add(even)) {
            if (even.getMoment() < this.firstevent) {
                this.firstevent = even.getMoment();
            }
            if (even.getMoment() > this.lastevent) {
                this.lastevent = even.getMoment();
            }
            Long numberObjects;
            numberObjects = this.typeevents.get(even.getType());
            if (numberObjects != null) {
                this.typeevents.put(even.getType(), new Long(numberObjects.longValue() + 1));
            }
            else
            {
                this.typeevents.put(even.getType(), new Long(1));
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEvent(int index) {
        Event e=this.events.get(index);
        this.events.remove(index);
         Long numberObjects;
            numberObjects = this.typeevents.get(e.getType());
            if (numberObjects != null) {
                if(numberObjects>1)
                {
                this.typeevents.put(e.getType(), new Long(numberObjects.longValue() - 1));
                }
                else
                {
                    this.typeevents.remove(e.getType());
                }
            }
            if(this.firstevent==e.getMoment())
            {
                this.firstevent = Long.MAX_VALUE;
                for(int i=0;i<this.events.size();i++)
                {
                    if(this.firstevent>this.events.get(i).getMoment())
                        this.firstevent=this.events.get(i).getMoment();
                }
            }
            if(this.lastevent==e.getMoment())
            {
                this.lastevent = Long.MIN_VALUE;
                for(int i=0;i<this.events.size();i++)
                {
                    if(this.lastevent<this.events.get(i).getMoment())
                        this.lastevent=this.events.get(i).getMoment();
                }
            }
        return true;
    }
}
