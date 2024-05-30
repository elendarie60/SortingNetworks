package sortingnetworks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingNetworks {
    public static List<Integer> sort(int[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
        }
        int n = array.length;
        /*if (n <= 16) {
            // Utilizăm rețeaua de sortare pentru tablouri mici
            int[][] comparators = generateSortingNetwork(n);
            for (int[] comparator : comparators) {
                compareAndSwap(array, comparator[0], comparator[1]);
            }*/
       // } else {
            // Utilizăm un algoritm de sortare eficient pentru tablouri mari
            java.util.Arrays.sort(array);
       // }
        // Convertim array-ul sortat într-o listă și o returnăm
        List<Integer> sortedList = new ArrayList<>();
        for (int num : array) {
            sortedList.add(num);
        }
        return sortedList;
    }

    private static int[][] generateSortingNetwork(int n) {
        // Implementăm algoritmul de generare a rețelei de sortare
        List<int[]> comparatorsList = new ArrayList<>();
        for (int i = n / 2; i > 0; i /= 2) {
            for (int j = i; j < n - i; j += i * 2) {
                for (int k = 0; k < i; k++) {
                    int[] comparator = {j + k, j + k + i};
                    comparatorsList.add(comparator);
                }
            }
        }
        return comparatorsList.toArray(new int[0][]);
    }

    private static void compareAndSwap(int[] array, int i, int j) {
        if (array[i] > array[j]) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void sort(String numbers) {
        // Convertim șirul de caractere cu numere într-un vector de întregi
        String[] strNumbers = numbers.split(", ");
        int[] arr = new int[strNumbers.length];
        for (int i = 0; i < strNumbers.length; i++) {
            arr[i] = Integer.parseInt(strNumbers[i]);
        }

        // Sortăm vectorul folosind rețeaua de sortare
        sort(arr);

        // Convertim vectorul sortat înapoi într-un șir de caractere
        StringBuilder sortedNumbers = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sortedNumbers.append(arr[i]);
            if (i < arr.length - 1) {
                sortedNumbers.append(", ");
            }
        }

        DatabaseHelper.insertSorting(numbers, sortedNumbers.toString());
    }
    private static void oddEvenMergeSort(int[] arr) {
        int n = arr.length;
        for (int p = 1; p < n; p <<= 1) {
            for (int k = p; k > 0; k >>= 1) {
                for (int j = k % p; j + k < n; j += (k << 1)) {
                    for (int i = 0; i < n; i++) {
                        int ij = i ^ j;
                        if ((ij & p) == 0 && arr[i] > arr[ij]) {
                            int temp = arr[i];
                            arr[i] = arr[ij];
                            arr[ij] = temp;
                        }
                    }
                }
            }
        }
    }
}
