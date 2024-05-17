package sortingnetworks;

public class Comparator {
    public static void compareAndSwap(int[] array, int i, int j) {
        if (array[i] > array[j]) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
