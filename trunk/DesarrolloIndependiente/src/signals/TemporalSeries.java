/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 * Representa una serie temporal.
 * identifier es una cadena de texto que representa a la serie temporal.
 * agent identifica al agente que produce los datos, si estos son parámetros fisiológicos que proceden del paciente tendrá el valor 'simulated'
 * data es el buffer circular que contiene los datos
 * frequency tiene la frecuencia de muestreo de la señal (Hz)
 * units es una cadena de texto que contiene las unidades del parámetro representado por la serie temporal
 *
 * dudas?? La frecuencia es por float? o habría q introducir un parámetro que fuera la cantidad de datos que se añaden cada vez que se muestrea
 * falta validar el identifier con los espacios y tal
 * @author USUARIO
 */
public class TemporalSeries extends Series {
    //Parametros serie temporal

    private CircularBuffer data;
    private float frequency;
    private String units;
    private int oldsample;
    private int newsample;
    private long samplescounter;

    /**
     * 
     * Cambiar los métodos que tienen ademas del array la cantidad
     * @param identifier
     * @param agent
     * @param frequency
     * @param units
     */
    public TemporalSeries(String identifier, String agent, long timeinit, float frequency, String units) {
        super(identifier, agent, timeinit);
        this.frequency = frequency;
        this.units = units.trim();
        this.oldsample = -1;
        this.newsample = -1;
        this.samplescounter=0;
        if (3600 * 6 * this.frequency > 100000) {
            this.data = new CircularBuffer(100000);
        } else {
            this.data = new CircularBuffer(3600 * 6);
        }

    }

    public long getSamplescounter() {
        return samplescounter;
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Devuelve la muestra más nueva
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    public int getNewsample() {
        if (data.getIndexnext() == 0) {
            if (data.isFull()) {
                return (data.getSize() - 1);
            } else {
                return -1;
            }
        }

        newsample = (data.getIndexnext() - 1);
        return newsample;
    }

    /**
     * Indice de la muestra mas antigua
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    public int getOldsample() {
        if (data.getIndexold() == 0 && data.getIndexnext() == 0 && data.isFull() == false) {
            return -1;
        }
        oldsample = (data.getIndexold());
        return oldsample;
    }

    public String getUnits() {
        return units;
    }

    public float[] read(int posSrc, int sizetoread) {
        return this.data.read(posSrc, sizetoread);
    }

    boolean write(float[] datatowrite, int sizetowrite) {
        if( this.data.write(datatowrite, sizetowrite))
        {
            this.samplescounter=this.samplescounter+sizetowrite;
            return true;
        }
        return false;
    }
}
