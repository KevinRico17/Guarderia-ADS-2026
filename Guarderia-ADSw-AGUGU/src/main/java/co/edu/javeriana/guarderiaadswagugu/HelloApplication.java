package co.edu.javeriana.guarderiaadswagugu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("AguguTarjetero.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1320, 820);
        stage.setTitle("ADSw-AGUGU – Sistema de Guardería");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
