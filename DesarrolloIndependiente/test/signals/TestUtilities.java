package signals;

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
}
