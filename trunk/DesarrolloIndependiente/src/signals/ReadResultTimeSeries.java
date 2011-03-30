package signals;

/**
 *
 * @comentario Esta clase no usa adecuadamente copias defensivas para protegerse de posibles
 * problemas en el array. No obstante, esta clase es simplemente un DTO que nosotros vamos a
 * crear, y luego le pasamos al usuario y el usuario lo hace con él lo que quiere. Lo vamos
 * a dejar asi. Pero hay que ser consciente de esta decisión de diseño.
 */
public class ReadResultTimeSeries extends ReadResultOneSignal{

    private float[] data;
    private int posInitToRead;

    public ReadResultTimeSeries(String identifierOwner,String identifierSignal, float[] data, int posInitToRead) {
        super(identifierOwner,identifierSignal);
        this.data = data;
        this.posInitToRead = posInitToRead;
    }

    public int getPosInitToRead() {
        return posInitToRead;
    }

    public float[] getData() {
        return data;
    }
}
