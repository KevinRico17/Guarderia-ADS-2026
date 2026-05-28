module co.edu.javeriana.guarderiaadswagugu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens co.edu.javeriana.guarderiaadswagugu to javafx.fxml;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.ninos to com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.empleados to com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.guarderia to com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.usuarios to com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.dao to com.google.gson;

    exports co.edu.javeriana.guarderiaadswagugu;
}