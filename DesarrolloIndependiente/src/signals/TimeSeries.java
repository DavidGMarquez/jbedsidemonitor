/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 * Representa una serie temporal.
 * identifier es una cadena de texto que representa a la serie temporal.
 * agent identifica al agente que produce los datos, si estos son parámetros fisiológicos que proceden del paciente tendrá el valor 'simulated'
 * buffer es el buffer circular que contiene los datos
 * frequency tiene la frecuencia de muestreo de la señal (Hz)
 * units es una cadena de texto que contiene las unidades del parámetro representado por la serie temporal
 *
 * @duda habría que añadir un campo que sea el numero de muestras de cada muestreo?
 * @author USUARIO
 */
public class TimeSeries extends Series {
    //Parametros serie temporal

    private CircularBuffer buffer;
    private float frequency;
    private String units;

    /**
     * 
     * Cambiar los métodos que tienen ademas del array la cantidad
     * @param identifier
     * @param agent
     * @param frequency
     * @param units
     */

    //@comentario número de lneas ejecutables de la clase original: 29; nmero de atributos: 6
    //nmero de líneas ejecutables de la clase modificada: 14; número de atributos: 3
    public TimeSeries(String identifier, String agent, long origin, float frequency, String units) {
        super(identifier, agent, origin);
        this.frequency = frequency;
        this.units = units.trim();
        if (3600 * 6 * this.frequency > 100000) {
            this.buffer = new CircularBuffer(100000);
        } else {
            this.buffer = new CircularBuffer(3600 * 6);
        }
    }

    public long getSamplescounter() {
        return buffer.getSize();
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Devuelve la muestra más nueva
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    //@comentario compara la complejidad de tu matodo original
    //con el metodo getLastData()del Buffer, que hace lo mismo
    //implementar la funcionalidad en el lugar adecuado ahorra mucho trabajo
    public float getNewsample() {
        if(buffer.isEmpty()) return -1;
        return buffer.getLastData();
    }

    /**
     * Indice de la muestra mas antigua
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    //@comentario Compara el código original con este
    public int getOldsample() {
        if(buffer.isEmpty()) return -1;
        return buffer.getIndexold();
    }

    public String getUnits() {
        return units;
    }

    public float[] read(int posSrc, int sizetoread) {
        return this.buffer.read(posSrc, sizetoread);
    }
//@comentario originalmente tenia cuatro lineas
    boolean write(float[] datatowrite, int sizetowrite) {
         return this.buffer.write(datatowrite);
    }
}
