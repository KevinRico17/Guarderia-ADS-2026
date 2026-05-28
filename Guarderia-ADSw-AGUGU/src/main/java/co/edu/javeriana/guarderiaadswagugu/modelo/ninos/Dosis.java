package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;
import java.time.LocalDate;

public class Dosis implements Serializable {
    private String enfermedad;
    private LocalDate fecha;
    private int numeroAplicacion; // 1 = primera vez, 2,3... = refuerzos
    private String laboratorio;
    private long serial;
    private boolean esGratuita;
    private boolean generaMalestar;

    public Dosis(String enfermedad, LocalDate fecha, int numeroAplicacion,
                 String laboratorio, long serial, boolean esGratuita, boolean generaMalestar) {
        this.enfermedad = enfermedad;
        this.fecha = fecha;
        this.numeroAplicacion = numeroAplicacion;
        this.laboratorio = laboratorio;
        this.serial = serial;
        this.esGratuita = esGratuita;
        this.generaMalestar = generaMalestar;
    }

    public boolean esPrimeraVez() { return numeroAplicacion == 1; }

    public String getEnfermedad() { return enfermedad; }
    public void setEnfermedad(String enfermedad) { this.enfermedad = enfermedad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getNumeroAplicacion() { return numeroAplicacion; }
    public void setNumeroAplicacion(int numeroAplicacion) { this.numeroAplicacion = numeroAplicacion; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public long getSerial() { return serial; }
    public void setSerial(long serial) { this.serial = serial; }

    public boolean isEsGratuita() { return esGratuita; }
    public void setEsGratuita(boolean esGratuita) { this.esGratuita = esGratuita; }

    public boolean isGeneraMalestar() { return generaMalestar; }
    public void setGeneraMalestar(boolean generaMalestar) { this.generaMalestar = generaMalestar; }
}
