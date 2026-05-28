package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Actividad implements Serializable {
    private String nombre;
    private double costoBase;
    private String descripcion;
    private List<Empleado> empleadosResponsables;
    private List<Inscripcion> inscripciones;
    private List<String> recursosNecesarios;

    public Actividad(String nombre, double costoBase, String descripcion) {
        this.nombre = nombre;
        this.costoBase = costoBase;
        this.descripcion = descripcion;
        this.empleadosResponsables = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
        this.recursosNecesarios = new ArrayList<>();
    }

    public Inscripcion inscribirNino(Nino nino, boolean acompanadoPorPadre) {
        Inscripcion inscripcion = new Inscripcion(nino, acompanadoPorPadre, costoBase);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public double getTotalRecaudado() {
        return inscripciones.stream().mapToDouble(Inscripcion::calcularCosto).sum();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Empleado> getEmpleadosResponsables() { return empleadosResponsables; }
    public void setEmpleadosResponsables(List<Empleado> empleadosResponsables) { this.empleadosResponsables = empleadosResponsables; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) { this.inscripciones = inscripciones; }

    public List<String> getRecursosNecesarios() { return recursosNecesarios; }
    public void setRecursosNecesarios(List<String> recursosNecesarios) { this.recursosNecesarios = recursosNecesarios; }
}
