module co.edu.javeriana.guarderiaadswagugu {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.javeriana.guarderiaadswagugu to javafx.fxml;
    exports co.edu.javeriana.guarderiaadswagugu;
}