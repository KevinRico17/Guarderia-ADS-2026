package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trotamundo extends Nino {
    private List<String> rutinasEjercicio;
    private List<String> autorizaciones; // actividades y salidas autorizadas

    public Trotamundo(String nombre, LocalDate fechaNacimiento, int registroCivil,
                      String marcaPañal, String etapaPañal) {
        super(nombre, fechaNacimiento, registroCivil, marcaPañal, etapaPañal);
        this.rutinasEjercicio = new ArrayList<>();
        this.autorizaciones = new ArrayList<>();
    }

    public void agregarRutina(String rutina) { rutinasEjercicio.add(rutina); }
    public void agregarAutorizacion(String actividad) { autorizaciones.add(actividad); }

    @Override
    public String getTipoNino() { return "Trotamundo (18-24 meses)"; }

    public List<String> getRutinasEjercicio() { return rutinasEjercicio; }
    public void setRutinasEjercicio(List<String> rutinasEjercicio) { this.rutinasEjercicio = rutinasEjercicio; }

    public List<String> getAutorizaciones() { return autorizaciones; }
    public void setAutorizaciones(List<String> autorizaciones) { this.autorizaciones = autorizaciones; }
}
