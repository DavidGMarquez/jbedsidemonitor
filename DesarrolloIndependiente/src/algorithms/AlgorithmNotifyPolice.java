/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class AlgorithmNotifyPolice {
        protected Map<String, Long> timeSeriesTheshold;
        protected Map<String, Long> eventSeriesTheshold;
        //1 All 2 Solo una
        int notifyPolice;

    public Map<String, Long> getEventSeriesTheshold() {
        return eventSeriesTheshold;
    }

    public int getNotifyPolice() {
        return notifyPolice;
    }

    public Map<String, Long> getTimeSeriesTheshold() {
        return timeSeriesTheshold;
    }


}
