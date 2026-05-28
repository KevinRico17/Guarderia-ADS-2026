package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import java.io.Serializable;

public class Jornada implements Serializable {
    private String nombre;
    private double costoBase;
    private boolean esExcepcional; // junio-julio y nov-enero
    private boolean esNocturna;

    public Jornada(String nombre, double costoBase, boolean esExcepcional, boolean esNocturna) {
        this.nombre = nombre;
        this.costoBase = costoBase;
        this.esExcepcional = esExcepcional;
        this.esNocturna = esNocturna;
    }

    public double calcularCosto() {
        double costo = costoBase;
        if (esExcepcional) costo *= 1.25; // 25% adicional
        if (esNocturna) costo *= 1.15;    // 15% adicional
        return costo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }

    public boolean isEsExcepcional() { return esExcepcional; }
    public void setEsExcepcional(boolean esExcepcional) { this.esExcepcional = esExcepcional; }

    public boolean isEsNocturna() { return esNocturna; }
    public void setEsNocturna(boolean esNocturna) { this.esNocturna = esNocturna; }
}
