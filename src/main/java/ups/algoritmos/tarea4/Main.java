package ups.algoritmos.tarea4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/ups/algoritmos/tarea4/vista/main_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Tarea 4 - Algoritmos y Complejidad");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}