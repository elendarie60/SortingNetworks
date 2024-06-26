package sortingnetworks;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;

public class SortingNetworks {
     private double yOffset = 30;
    private double yInterval = 30;
    private List<Comparator> comparators;

    public SortingNetworks(double yOffset, double yInterval, double xPosition, int numElements) {
        //this.comparators = new ArrayList<>();
       // int paddedN = numElements % 2 == 0 ? numElements : numElements + 1; // Adăugăm un element suplimentar dacă e impar
       // generateBitonicComparators(yOffset, yInterval, xPosition, paddedN);
        comparators = new ArrayList<>();
        int padded;
            padded = numElements;
        for (int i = 0; i <= padded - 1; i++) {
            for (int j = i + 1; j < padded; j++) {
                // Verificăm dacă este necesar să adăugăm un comparator în funcție de poziția elementelor în secvența bitonică
                int dir = (i < j) ? 1 : 0;
                comparators.add(new Comparator(i, j, yOffset, yInterval, xPosition-60, dir));
            }
            xPosition = xPosition+40;
        }
    }

    private void generateBitonicComparators(double yOffset, double yInterval, double xPosition, int numElements) {
        // Generăm comparatoare pentru fiecare pereche de elemente care trebuie comparate în rețeaua bitonică
        for (int i = 0; i < numElements - 1; i++) {
            for (int j = i + 1; j < numElements; j++) {
                // Verificăm dacă este necesar să adăugăm un comparator în funcție de poziția elementelor în secvența bitonică
                int dir = (i < j) ? 1 : 0;
                comparators.add(new Comparator(i, j, yOffset, yInterval, xPosition, dir));
            }
        }
    }

    private void bitonicSort(int low, int cnt, int dir, double yOffset, double yInterval, double xPosition) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonicSort(low, k, 1, yOffset, yInterval, xPosition);
            bitonicSort(low + k, k, 0, yOffset, yInterval, xPosition);
            bitonicMerge(low, cnt, dir, yOffset, yInterval, xPosition);
        }
    }

    private void bitonicMerge(int low, int cnt, int dir, double yOffset, double yInterval, double xPosition) {
        if (cnt > 1) {
            int k = cnt / 2;
            // Ne asigurăm că nu generăm comparatoare în afara limitelor array-ului
            if (low + k <= comparators.size()) {
                for (int i = low; i < low + k; i++) {
                    comparators.add(new Comparator(i, i + k, yOffset, yInterval, xPosition, dir));
                }
            }
            bitonicMerge(low, k, dir, yOffset, yInterval, xPosition);
            bitonicMerge(low + k, k, dir, yOffset, yInterval, xPosition);
        }
    }

    public void sort(int[] array) {
        for (Comparator comparator : comparators) {
            comparator.compareAndSwap(array);
        }
    }

    public List<Comparator> getComparators() {
        return comparators;
    }
}
