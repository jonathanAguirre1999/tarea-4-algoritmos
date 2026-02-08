module ups.algoritmos.tarea4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ups.algoritmos.tarea4 to javafx.fxml;
    opens ups.algoritmos.tarea4.controlador to javafx.fxml;

    exports ups.algoritmos.tarea4;
}