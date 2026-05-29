module co.edu.javeriana.guarderiaadswagugu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens co.edu.javeriana.guarderiaadswagugu to javafx.fxml;
    opens co.edu.javeriana.guarderiaadswagugu.controllers to javafx.fxml;

    opens co.edu.javeriana.guarderiaadswagugu.modelo.ninos to javafx.base, com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.empleados to javafx.base, com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.guarderia to javafx.base, com.google.gson;
    opens co.edu.javeriana.guarderiaadswagugu.modelo.usuarios to javafx.base, com.google.gson;

    exports co.edu.javeriana.guarderiaadswagugu;
    exports co.edu.javeriana.guarderiaadswagugu.controllers;
}