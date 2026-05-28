package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;

public class Competencia implements Serializable {
    private String nombre;
    private int edadDesarrolloEnMeses;
    private int valoracion; // valoración cuantitativa del pedagogo

    public Competencia(String nombre, int edadDesarrolloEnMeses, int valoracion) {
        this.nombre = nombre;
        this.edadDesarrolloEnMeses = edadDesarrolloEnMeses;
        this.valoracion = valoracion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdadDesarrolloEnMeses() { return edadDesarrolloEnMeses; }
    public void setEdadDesarrolloEnMeses(int edadDesarrolloEnMeses) { this.edadDesarrolloEnMeses = edadDesarrolloEnMeses; }

    public int getValoracion() { return valoracion; }
    public void setValoracion(int valoracion) { this.valoracion = valoracion; }
}
