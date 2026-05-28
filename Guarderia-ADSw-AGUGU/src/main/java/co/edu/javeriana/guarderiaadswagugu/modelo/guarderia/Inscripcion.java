package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.io.Serializable;

public class Inscripcion implements Serializable {
    private Nino nino;
    private boolean acompanadoPorPadre;
    private double costoBase;

    public Inscripcion(Nino nino, boolean acompanadoPorPadre, double costoBase) {
        this.nino = nino;
        this.acompanadoPorPadre = acompanadoPorPadre;
        this.costoBase = costoBase;
    }

    public double calcularCosto() {
        if (acompanadoPorPadre) return costoBase * 0.75; // 25% descuento
        return costoBase;
    }

    public Nino getNino() { return nino; }
    public void setNino(Nino nino) { this.nino = nino; }

    public boolean isAcompanadoPorPadre() { return acompanadoPorPadre; }
    public void setAcompanadoPorPadre(boolean acompanadoPorPadre) { this.acompanadoPorPadre = acompanadoPorPadre; }

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }
}
