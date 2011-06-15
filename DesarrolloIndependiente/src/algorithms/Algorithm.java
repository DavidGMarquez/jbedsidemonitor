package algorithms;

import javax.swing.JFrame;
import signals.ReadResult;
import signals.Series;

public interface Algorithm {
    //@pendiente revisar si falta algo de la especificación de la memoria inicial.
    //Veo que es dificil sincronizar las señales. Es decir si tienes dos señales, saber exactamente
    //como las puedes situar en el tiempo cada una de ellas para saber donde estas trabajando.
    public String getIdentifier();

    public Series getSignalToWrite();

    public AlgorithmNotifyPolice getNotifyPolice();

    public boolean execute(ReadResult readResult);

    public boolean hasConfigurationGui();

    public void showConfigurationGui(JFrame jframe);
    //Hacer metodos de interfaz para acceder y para poder saber lo que quiere, señales, señales que escribe, cada caunto
}
