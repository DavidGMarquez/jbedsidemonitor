/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class NoTemporalSeries extends Series{

    //Parametros serie temporal
    private ArrayList<Event> events;
    private ArrayList<String> imputs;
    private long firstevent;
    private long lastevent;
    private String units;
    //Añadir mapa para controlar la cnatidad
    private Map<String,Integer> tpyeevents;
 public NoTemporalSeries(String identifier, String agent,long timeinit, ArrayList<String> imputs, String units) {
            super(identifier, agent, timeinit);
            this.imputs = imputs;
            this.units = units;
            this.firstevent = -1;
            this.lastevent = -1;
            this.tpyeevents=new HashMap<String,Integer>();
            this.events=new ArrayList<Event>();


    }
 public Event getEvent(int index)
 {
     return this.events.get(index);
 }
 public boolean addEvent(Event even){
     if(this.events.add(even)){
         if(this.events.size()==1)
             this.firstevent=even.getMoment();
     return true;
     }
     else
         return false;
 }
}
