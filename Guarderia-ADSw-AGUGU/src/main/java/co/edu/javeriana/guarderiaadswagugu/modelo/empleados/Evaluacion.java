package co.edu.javeriana.guarderiaadswagugu.modelo.empleados;

import java.io.Serializable;
import java.time.LocalDate;

public class Evaluacion implements Serializable {
    private LocalDate fecha;
    private int puntaje; // 0-100
    private String observaciones;
    private double ajusteSalario;

    public Evaluacion(LocalDate fecha, int puntaje, String observaciones) {
        this.fecha = fecha;
        this.puntaje = puntaje;
        this.observaciones = observaciones;
        this.ajusteSalario = calcularAjuste();
    }

    private double calcularAjuste() {
        if (puntaje >= 90) return 0.10;      // 10% aumento
        else if (puntaje >= 75) return 0.05; // 5% aumento
        else if (puntaje >= 60) return 0.0;  // sin cambio
        else return -0.05;                    // 5% reducción
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public double getAjusteSalario() { return ajusteSalario; }

    @Override
    public String toString() {
        return fecha + " - Puntaje: " + puntaje + " | " + observaciones;
    }
}
