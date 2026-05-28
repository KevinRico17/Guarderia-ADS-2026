package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;

public class Alimento implements Serializable {
    private String nombre;
    private String cantidad;
    private boolean permitido;
    private String razonProhibicion; // alergia, religiosa, etc.

    public Alimento(String nombre, String cantidad, boolean permitido, String razonProhibicion) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.permitido = permitido;
        this.razonProhibicion = razonProhibicion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public boolean isPermitido() { return permitido; }
    public void setPermitido(boolean permitido) { this.permitido = permitido; }

    public String getRazonProhibicion() { return razonProhibicion; }
    public void setRazonProhibicion(String razonProhibicion) { this.razonProhibicion = razonProhibicion; }

    @Override
    public String toString() {
        return nombre + " - " + cantidad + (permitido ? " (Permitido)" : " (Prohibido: " + razonProhibicion + ")");
    }
}
