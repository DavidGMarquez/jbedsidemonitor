package signals;

import java.util.ArrayList;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;

/**
 * Representa una serie temporal.
 * identifier es una cadena de texto que representa a la serie temporal.
 * agent identifica al agente que produce los datos, si estos son parámetros fisiológicos que proceden del paciente tendrá el valor 'simulated'
 * buffer es el buffer circular que contiene los datos
 * frequency tiene la frecuencia de muestreo de la señal (Hz)
 * units es una cadena de texto que contiene las unidades del parámetro representado por la serie temporal
 *
 */
public class TimeSeries extends Series {
    //Parametros serie temporal

    private CircularBuffer buffer;
    private final int defaultBufferSize = 100000;
    private float frequency;
    private String units;

    public TimeSeries(String identifier, String agent, long origin, float frequency, String units) {
        super(identifier, agent, origin,units);
        this.frequency = frequency;
        if (3600 * 6 * this.frequency > defaultBufferSize) {
            this.buffer = new CircularBuffer(defaultBufferSize);
        } else {
            this.buffer = new CircularBuffer((int) Math.ceil(3600 * 6 * this.frequency));
        }
    }

    public int getSamplescounter() {
        return buffer.getSize();
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Devuelve la muestra más nueva
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    public int getIndexNewsample() {
        if (buffer.isEmpty()) {
            return -1;
        }
        return (buffer.getIndexNextWrite() - 1) % this.getCapacity();
    }


    public int getCapacity() {
        return this.buffer.getCapacity();
    }

    public float[] read(int posSrc, int sizetoread) {
        try {
            return this.buffer.read(posSrc, sizetoread);
        } catch (IllegalReadException e) {
            //@debug       System.out.println("Excepcion capacity" + e.getBufferCapacity() + "lastSample" + e.getLastSampleWrite() + "dataToRead" + e.getNumDataToRead() + "pos StartReading" + e.getPosStartReading());
            throw new IllegalReadException(e, this.getIdentifier());
        }
    }

    public ConsecutiveSamplesAvailableInfo write(float[] datatowrite, int indexInitToWrite) {
        //@debug  System.out.println("<<-->>" + this.getIdentifier() + "Escribiendo en" + indexInitToWrite + "Cantidad " + datatowrite.length);
        try {
            return this.buffer.write(datatowrite, indexInitToWrite);
        } catch (TooMuchDataToWriteException e) {
            throw new TooMuchDataToWriteException(e, this.getIdentifier());
        } catch (IllegalWriteException e) {
            throw new IllegalWriteException(e, this.getIdentifier());
        }
    }

    public int getLastSampleWrite() {
        return buffer.getLastSampleWrite();
    }

    public ConsecutiveSamplesAvailableInfo getConsecutiveSamplesAvailableInfo() {
        return this.buffer.getConsecutiveSamplesAvailableInfo();
    }
    @Override
    public String toString(){
        String stringToReturn="Identifier:"+this.getIdentifier()+"\n";
        stringToReturn=stringToReturn.concat("Agent:"+this.getAgent()+"\n");
        stringToReturn=stringToReturn.concat("Frequency:"+this.getFrequency()+"\n");
        stringToReturn=stringToReturn.concat("Capacity:"+this.getCapacity()+"\n");
        stringToReturn=stringToReturn.concat("Units:"+this.getUnits()+"\n");
        stringToReturn=stringToReturn.concat("Origin:"+this.getOrigin()+"\n");
        stringToReturn=stringToReturn.concat("SeriesIsGeneratedFrom:");
        ArrayList<String> seriesIsGeneratedFrom = this.getSeriesIsGeneratedFrom();
        for(String serie:seriesIsGeneratedFrom){
            stringToReturn.concat(" "+serie);
        }
        stringToReturn.concat("\n");
        return stringToReturn;
    }
}
