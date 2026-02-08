package ups.algoritmos.tarea4.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ups.algoritmos.tarea4.modelo.LogicaMatriz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

public class MainViewController {

    @FXML private TextField txtBuscar, txtGiftCard;
    @FXML private TextArea txtConsola;
    @FXML private Label lblTiempoGenerico;
    @FXML private Label lblTiempoOptimizado;

    private LogicaMatriz gestorMatriz;
    private int[] preciosFarmacia = new Random().ints(100000, 1, 501).toArray();
    private boolean datosListos = false;

    @FXML
    public void initialize() {
        gestorMatriz = new LogicaMatriz(1000, 1000);
        log("Sistema listo. Genere la matriz para comenzar.");
    }

    // ACTIVIDAD 1: MATRIZ
    @FXML
    void generarMatriz(ActionEvent event) {
        long inicio = System.nanoTime();
        gestorMatriz.generarDatosAleatorios();
        datosListos = true;
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        log("Matriz 1000x1000 generada exitosamente.");

        mostrarAlerta("Proceso Completado",
                "Se generaron 1,000,000 de datos aleatorios.\nTiempo total: "
                        + String.format("%.2f", tiempoMs) + " ms.");
    }

    @FXML
    public void buscarSecuencial(ActionEvent event) {
        if (!verificarDatos()) return;
        int valor = leerNumero();
        if (valor == Integer.MIN_VALUE) return;
        int negativo = -valor;

        int[] datos = gestorMatriz.getCopiaVector();

        long inicio = System.nanoTime();

        int idxPos = gestorMatriz.busquedaSecuencial(datos, valor);
        int idxNeg = gestorMatriz.busquedaSecuencial(datos, negativo);

        long fin = System.nanoTime();

        reportarBusqueda("Secuencial", valor, idxPos, idxNeg, fin - inicio);
    }

    @FXML
    public void buscarBinaria(ActionEvent event) {
        if (!verificarDatos()) return;

        if (!confirmarOrdenamientoAutomatico()) return;

        int valor = leerNumero();
        int negativo = -valor;

        int[] datosOrdenados = gestorMatriz.getCopiaVector();
        Arrays.sort(datosOrdenados);

        long inicio = System.nanoTime();

        int idxPos = gestorMatriz.busquedaBinaria(datosOrdenados, valor);
        int idxNeg = gestorMatriz.busquedaBinaria(datosOrdenados, negativo);

        long fin = System.nanoTime();

        reportarBusqueda("Binaria", valor, idxPos, idxNeg, fin - inicio);
    }

    @FXML
    public void buscarInterpolacion(ActionEvent event) {
        if (!verificarDatos()) return;
        if (!confirmarOrdenamientoAutomatico()) return;

        int valor = leerNumero();
        int negativo = -valor;

        int[] datosOrdenados = gestorMatriz.getCopiaVector();
        Arrays.sort(datosOrdenados);

        long inicio = System.nanoTime();

        int idxPos = gestorMatriz.busquedaInterpolacion(datosOrdenados, valor);
        int idxNeg = gestorMatriz.busquedaInterpolacion(datosOrdenados, negativo);

        long fin = System.nanoTime();

        reportarBusqueda("Interpolación", valor, idxPos, idxNeg, fin - inicio);
    }

    @FXML
    public void ordenarBubble(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();
        log("Ejecutando Bubble Sort, espere...");

        long tiempo = medirTiempo(() -> gestorMatriz.bubbleSort(copia));
        log("Bubble Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    @FXML
    public void ordenarInsertion(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();
        log("Ejecutando Insertion Sort, espere...");

        long tiempo = medirTiempo(() -> gestorMatriz.insertionSort(copia));
        log("Insertion Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    @FXML
    public void ordenarMerge(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();

        long tiempo = medirTiempo(() -> gestorMatriz.mergeSort(copia, 0, copia.length - 1));
        log("Merge Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    @FXML
    public void ordenarShell(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();

        long tiempo = medirTiempo(() -> gestorMatriz.shellSort(copia));
        log("Shell Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    @FXML
    public void ordenarRadix(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();

        long tiempo = medirTiempo(() -> gestorMatriz.radixSort(copia));
        log("Radix Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    @FXML
    public void ordenarCounting(ActionEvent event) {
        if (!verificarDatos()) return;
        int[] copia = gestorMatriz.getCopiaVector();

        long tiempo = medirTiempo(() -> gestorMatriz.countingSort(copia));
        log("Counting Sort finalizado en: " + tiempo + " ns (" + (tiempo/1_000_000) + " ms)");
    }

    // ACTIVIDAD 2: OPTIMIZACIÓN

    @FXML
    public void ejecutarGenerico(ActionEvent event) {
        int target = obtenerTargetUsuario();
        if (target == -1) return;

        long inicio = System.nanoTime();
        boolean encontrado = false;

        // Algoritmo O(n^2) - BUSQUEDA EN TODO EL ARRAY
        for (int i = 0; i < preciosFarmacia.length; i++) {
            for (int j = i + 1; j < preciosFarmacia.length; j++) {
                if (preciosFarmacia[i] + preciosFarmacia[j] == target) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) break;
        }

        long fin = System.nanoTime();
        double ms = (fin - inicio) / 1_000_000.0;

        String estado = encontrado ? " (ENCONTRADO)" : " (NO EXISTE)";
        lblTiempoGenerico.setText(String.format("%.4f ms", ms) + estado);
    }

    @FXML
    public void ejecutarOptimizado(ActionEvent event) {
        int target = obtenerTargetUsuario();
        if (target == -1) return;

        long inicio = System.nanoTime();
        boolean encontrado = false;

        // Algoritmo O(n)
        HashSet<Integer> complementos = new HashSet<>();
        for (int precio : preciosFarmacia) {
            if (complementos.contains(precio)) {
                encontrado = true;
                break;
            }
            complementos.add(target - precio);
        }

        long fin = System.nanoTime();
        double ms = (fin - inicio) / 1_000_000.0;

        String estado = encontrado ? " (ENCONTRADO)" : " (NO EXISTE)";
        lblTiempoOptimizado.setText(String.format("%.4f ms", ms) + estado);
    }

    // ------------ METODOS AUXILIARES ----------------------------- //
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean verificarDatos() {
        if (!datosListos) {
            log("ERROR: Primero debe generar la matriz.");
            mostrarAlerta("Error", "Primero genere la matriz de datos.");
            return false;
        }
        return true;
    }

    private int leerNumero() {
        try {
            return Integer.parseInt(txtBuscar.getText());
        } catch (NumberFormatException e) {
            log("Error: Ingrese un número válido para buscar.");
            return Integer.MIN_VALUE;
        }
    }

    private void reportarBusqueda(String metodo, int valor, int idxPos, int idxNeg, long nanos) {
        double ms = nanos / 1_000_000.0;
        int cols = 1000;

        StringBuilder sb = new StringBuilder();
        sb.append(">> Búsqueda ").append(metodo).append(" (x y -x):\n");

        if (idxPos != -1) {
            int f = idxPos / cols;
            int c = idxPos % cols;
            sb.append("   Valor ").append(valor).append(": Encontrado en [").append(f).append("][").append(c).append("]\n");
        } else {
            sb.append("   Valor ").append(valor).append(": No encontrado\n");
        }

        if (idxNeg != -1) {
            int f = idxNeg / cols;
            int c = idxNeg % cols;
            sb.append("   Valor ").append(-valor).append(": Encontrado en [").append(f).append("][").append(c).append("]\n");
        } else {
            sb.append("   Valor ").append(-valor).append(": No encontrado\n");
        }        sb.append("   Tiempo Total: ").append(String.format("%.4f", ms)).append(" ms\n");

        sb.append("   Tiempo Total: ").append(String.format("%.4f", ms)).append(" ms\n");
        log(sb.toString());
    }

    private long medirTiempo(Runnable algoritmo) {
        long inicio = System.nanoTime();
        algoritmo.run();
        return System.nanoTime() - inicio;
    }

    private boolean confirmarOrdenamientoAutomatico() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Requisito de Algoritmo");
        alert.setHeaderText("Se requieren datos ordenados.");
        alert.setContentText("Para realizar esta búsqueda, el sistema ordenará automáticamente los datos.\n\n"
                + "Nota: El tiempo de ordenamiento NO se incluirá en la medición final, solo el tiempo de búsqueda.\n\n"
                + "¿Desea continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private int obtenerTargetUsuario() {
        try {
            int val = Integer.parseInt(txtGiftCard.getText());
            /*
            * este metodo delimita el rango de valores que puede tener la giftcard
            * para propositos de testing se deja comentado
            if (val < 2 || val > 1000) {
                mostrarAlerta("Advertencia", "Valor fuera de rango probable ($2 - $1000)");
                return -1;
            } */
            return val;
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Entrada", "Por favor ingrese un número entero válido para la GiftCard.");
            return -1;
        }
    }

    private void log(String msg) {
        txtConsola.appendText(msg + "\n");
    }
}
