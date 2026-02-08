package ups.algoritmos.tarea4.modelo;

import java.util.Arrays;
import java.util.Random;

public class LogicaMatriz {

    private int[][] matriz;
    private int[] vectorPlano;
    private int filas, cols;

    // Constructor
    public LogicaMatriz(int filas, int cols) {
        this.filas = filas;
        this.cols = cols;
        this.matriz = new int[filas][cols];
        this.vectorPlano = new int[filas * cols];
    }

    // GENERACIÓN DE DATOS
    public void generarDatosAleatorios() {
        Random rand = new Random();
        int k = 0;
        int min = -1_000_000;
        int max = 1_000_000;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                int valor = rand.nextInt((max - min) + 1) + min;
                matriz[i][j] = valor;
                vectorPlano[k++] = valor;
            }
        }
    }

    // Metodo auxiliar para clonar el vector
    public int[] getCopiaVector() {
        return Arrays.copyOf(vectorPlano, vectorPlano.length);
    }

    // ALGORITMOS DE BÚSQUEDA

    // Búsqueda Secuencial (O(n))
    public int busquedaSecuencial(int[] datos, int valor) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] == valor) return i;
        }
        return -1;
    }

    // Búsqueda Binaria (O(log n))
    // REQUIERE ARREGLO ORDENADO
    public int busquedaBinaria(int[] datos, int valor) {
        int inicio = 0;
        int fin = datos.length - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            if (datos[medio] == valor) return medio;
            if (datos[medio] < valor) inicio = medio + 1;
            else fin = medio - 1;
        }
        return -1;
    }

    // Búsqueda Interpolación (O(log log n))
    // REQUIERE ORDENAMIENTO Y DISTRIBUCIÓN UNIFORME
    public int busquedaInterpolacion(int[] datos, int x) {
        int lo = 0, hi = (datos.length - 1);
        while (lo <= hi && x >= datos[lo] && x <= datos[hi]) {
            if (lo == hi) {
                if (datos[lo] == x) return lo;
                return -1;
            }
            // Fórmula de interpolación
            int pos = lo + (((hi - lo) / (datos[hi] - datos[lo])) * (x - datos[lo]));

            if (datos[pos] == x) return pos;
            if (datos[pos] < x) lo = pos + 1;
            else hi = pos - 1;
        }
        return -1;
    }

    // ALGORITMOS DE ORDENAMIENTO (LENTOS)

    // Bubble Sort
    public void bubbleSort(int[] datos) {
        int n = datos.length;
        // ESTE ES UN SEGURO PARA EVITAR CONGELAMIENTO DEL SISTEMA
        if (n > 20000) n = 20000;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (datos[j] > datos[j + 1]) {
                    int temp = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = temp;
                }
            }
        }
    }

    // Insertion Sort
    public void insertionSort(int[] datos) {
        int n = datos.length;
        // MISMO SEGURO QUE EN BUBBLE
        if (n > 20000) n = 20000;

        for (int i = 1; i < n; ++i) {
            int key = datos[i];
            int j = i - 1;
            while (j >= 0 && datos[j] > key) {
                datos[j + 1] = datos[j];
                j = j - 1;
            }
            datos[j + 1] = key;
        }
    }

    // ALGORITMOS DE ORDENAMIENTO (RÁPIDOS)

    // Merge Sort
    public void mergeSort(int[] datos, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(datos, l, m);
            mergeSort(datos, m + 1, r);
            merge(datos, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Shell Sort
    public void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
    }

    // Radix Sort (Soporta negativos usando offset)
    public void radixSort(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int i : arr) min = Math.min(min, i);

        // Si hay negativos se desplazan todos los datos para que sea positivo
        if (min < 0) {
            for (int i = 0; i < arr.length; i++) arr[i] -= min;
        }

        int max = Integer.MIN_VALUE;
        for (int i : arr) max = Math.max(max, i);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countSortParaRadix(arr, exp);

        // Se devuelve los negativos a su valor original
        if (min < 0) {
            for (int i = 0; i < arr.length; i++) arr[i] += min;
        }
    }

    private void countSortParaRadix(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) count[(arr[i] / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        for (int i = 0; i < n; i++) arr[i] = output[i];
    }

    // Counting Sort (Versión simple con Offset para negativos)
    public void countingSort(int[] arr) {
        int max = Arrays.stream(arr).max().getAsInt();
        int min = Arrays.stream(arr).min().getAsInt();
        int range = max - min + 1;

        int[] count = new int[range];
        int[] output = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
}
