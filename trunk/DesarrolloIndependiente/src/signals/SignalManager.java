/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.ArrayList;

/** Singleton Facade
 *
 * @author USUARIO
 */
public class SignalManager {
    ArrayList<TimeSeries> timeSeries;
    ArrayList<EventSeries> eventSeries;
    private SignalManager(){
    timeSeries=new ArrayList<TimeSeries>();
    eventSeries=new ArrayList<EventSeries>();}
     private static final SignalManager INSTANCE= new SignalManager();

    public static SignalManager getInstance(){
        return INSTANCE;
    }
    //@duda quizas haya que hacer una copia de TS
    public boolean addTimeSeries(TimeSeries TS){
        return this.timeSeries.add(TS);
    }
    //@duda quizas haya que hacer una copia de TS
       public boolean addAllTimeSeries(ArrayList<TimeSeries> TS){
        return this.timeSeries.addAll(TS);
    }
    public float[] readFromTimeSeries(int index,int posSrc,int sizeToRead)
    {
        return this.timeSeries.get(index).read(posSrc, sizeToRead);
    }
    public float[] readNewFromTimeSeries(int index,int indexLastRead)
    {
        if(this.timeSeries.get(index).getIndexNewsample()!=-1)

        return this.timeSeries.get(index).read(indexLastRead,(this.timeSeries.get(index).getIndexNewsample()-indexLastRead)+1%this.timeSeries.get(index).getCapacity());
        else
            return new float[0];
    }
    public boolean writeToTimeSeries(int index,float[] dataToWrite)
    {
        return this.timeSeries.get(index).write(dataToWrite);
    }

    
}
