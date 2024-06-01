package sortingnetworks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingNetworks {
    /*public static List<Integer> sort(int[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
    }
        int n = array.length;
        /*if (n <= 16) {
            // Utilizăm rețeaua de sortare pentru tablouri mici
            int[][] comparators = generateSortingNetwork(n);
            for (int[] comparator : comparators) {
                compareAndSwap(array, comparator[0], comparator[1]);
            }
        } else {
            // Utilizăm un algoritm de sortare eficient pentru tablouri mari
            java.util.Arrays.sort(array);
        }
        // Convertim array-ul sortat într-o listă și o returnăm
        List<Integer> sortedList = new ArrayList<>();
        for (int num : array) {
            sortedList.add(num);
        }
        return sortedList;
    }*/

    private static List<Comparator> comparators;
    public static List<Comparator> getComparators() {
        return comparators;
    }

    public SortingNetworks(double yOffset, double yInterval, double xPosition) {
        comparators = new ArrayList<>();
        // Define the comparators for the sorting network (example for a 4-element network)
        comparators.add(new Comparator(0, 1, yOffset, yInterval, xPosition));
        comparators.add(new Comparator(2, 3, yOffset, yInterval, xPosition));
        comparators.add(new Comparator(0, 2, yOffset, yInterval, xPosition + 50)); // Adjust xPosition for better visibility
        comparators.add(new Comparator(1, 3, yOffset, yInterval, xPosition + 50));
        comparators.add(new Comparator(1, 2, yOffset, yInterval, xPosition + 100));
    }

    public void sort(int[] array) {
        bitonicSort(array, 0, array.length, true);
    }

    private void bitonicSort(int[] array, int low, int count, boolean ascending) {
        if (count > 1) {
            int k = count / 2;
            bitonicSort(array, low, k, true);
            bitonicSort(array, low + k, k, false);
            bitonicMerge(array, low, count, ascending);
        }
    }

    private void bitonicMerge(int[] array, int low, int count, boolean ascending) {
        if (count > 1) {
            int k = count / 2;
            for (int i = low; i < low + k; i++) {
                compareAndSwap(array, i, i + k, ascending);
            }
            bitonicMerge(array, low, k, ascending);
            bitonicMerge(array, low + k, k, ascending);
        }
    }

    public void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public void mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private static void compareAndSwap(int[] array, int i, int j, boolean ascending) {
        if (ascending == (array[i] > array[j])) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
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
        //sort(arr);

        // Convertim vectorul sortat înapoi într-un șir de caractere
        StringBuilder sortedNumbers = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sortedNumbers.append(arr[i]);
            if (i < arr.length - 1) {
                sortedNumbers.append(", ");
            }
        }
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
