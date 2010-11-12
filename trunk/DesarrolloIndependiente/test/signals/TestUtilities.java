package signals;

import java.util.HashSet;
import java.util.LinkedList;

public class TestUtilities {

    public static void showArray(float[] array) {
        System.out.println("-");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        System.out.println("-");
    }

    public static boolean compareArray(float[] array1, float[] array2, int tam) {
        for (int i = 0; i < tam; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static float[] generateArray(int tam) {
        float[] array = new float[tam];
        for (int i = 0; i < tam; i++) {
            array[i] = (float) Math.random() * 100;
        }
        return array;
    }

    public static float[] generateArrayWithConsecutiveIntegers(int from, int tam) {
        float[] array = new float[tam];
        for (int i = 0; i < tam; i++) {
            array[i] = i + from;
        }
        return array;
    }
    public static void SecuentialArray(float[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
    }

    public static void printArray(float[] readFromTimeSeries) {
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            System.out.println(readFromTimeSeries[i]);
        }
    }
    public static void secuentialArray(float[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
    }


    public static void eventosAleatorios(LinkedList<Event> eventos, int numberofevents, int timeinit, int duration) {
        for (int i = 0; i < numberofevents; i++) {
            eventos.add(new Event(timeinit + ((new Double(Math.random() * 99999).longValue()) % duration), "GeneradoAleatorio", null));
        }
    }

    public static void imprimirEventos(LinkedList<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            System.out.println("Event " + i + " time" + events.get(i).getLocation() + " tipo " + events.get(i).getType());
        }
    }

    public static boolean eventosCompararListas(LinkedList<Event> eventos1, LinkedList<Event> eventos2) {
        HashSet hashset1 = new HashSet<Event>(eventos1);
        HashSet hashset2 = new HashSet<Event>(eventos2);
        HashSet hashset3 = new HashSet<Event>(eventos1);
        HashSet hashset4 = new HashSet<Event>(eventos2);
        hashset1.removeAll(hashset2);
        hashset4.removeAll(hashset3);
        if (hashset4.isEmpty() && hashset1.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
