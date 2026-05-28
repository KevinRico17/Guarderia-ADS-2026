package co.edu.javeriana.guarderiaadswagugu.modelo.empleados;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Jornada;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empleado implements Serializable {
    private String nombre;
    private int cedula;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefonoFijo;
    private String telefonoCelular;
    private String titulo;
    private int tarjetaProfesional;
    private String cargo;
    private int experienciaAnios;
    private boolean realizoInvestigacion;
    private double salario;
    private List<Jornada> jornadas;
    private String sucursalId;
    private List<Evaluacion> evaluaciones;

    public Empleado(String nombre, int cedula, LocalDate fechaNacimiento,
                    String direccion, String titulo, int tarjetaProfesional,
                    String cargo, int experienciaAnios, boolean realizoInvestigacion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.titulo = titulo;
        this.tarjetaProfesional = tarjetaProfesional;
        this.cargo = cargo;
        this.experienciaAnios = experienciaAnios;
        this.realizoInvestigacion = realizoInvestigacion;
        this.evaluaciones = new ArrayList<>();
        this.jornadas = new ArrayList<>();
        calcularSalario();
    }

    public void calcularSalario() {
        double base = 1500000;
        base += experienciaAnios * 200000;
        if (realizoInvestigacion) base += 500000;
        this.salario = base;
    }

    public void agregarEvaluacion(Evaluacion evaluacion) {
        evaluaciones.add(evaluacion);
    }

    public Evaluacion getUltimaEvaluacion() {
        if (evaluaciones.isEmpty()) return null;
        return evaluaciones.get(evaluaciones.size() - 1);
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefonoFijo() { return telefonoFijo; }
    public void setTelefonoFijo(String telefonoFijo) { this.telefonoFijo = telefonoFijo; }

    public String getTelefonoCelular() { return telefonoCelular; }
    public void setTelefonoCelular(String telefonoCelular) { this.telefonoCelular = telefonoCelular; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(int tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public int getExperienciaAnios() { return experienciaAnios; }
    public void setExperienciaAnios(int experienciaAnios) { this.experienciaAnios = experienciaAnios; }

    public boolean isRealizoInvestigacion() { return realizoInvestigacion; }
    public void setRealizoInvestigacion(boolean realizoInvestigacion) { this.realizoInvestigacion = realizoInvestigacion; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public List<Jornada> getJornadas() { return jornadas; }
    public void setJornadas(List<Jornada> jornadas) { this.jornadas = jornadas; }
    public void agregarJornada(Jornada jornada) { this.jornadas.add(jornada); }
    public void eliminarJornada(Jornada jornada) { this.jornadas.remove(jornada); }

    public String getSucursalId() { return sucursalId; }
    public void setSucursalId(String sucursalId) { this.sucursalId = sucursalId; }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }

    @Override
    public String toString() { return nombre + " - " + cargo + " (CC: " + cedula + ")"; }
}