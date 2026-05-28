package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Jornada;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public abstract class Nino implements Serializable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private int registroCivil;
    private String marcaPañal;
    private String etapaPañal;
    private Carne carne;
    private Dieta dieta;
    private List<Jornada> jornadas;

    public Nino(String nombre, LocalDate fechaNacimiento, int registroCivil,
                String marcaPañal, String etapaPañal) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.registroCivil = registroCivil;
        this.marcaPañal = marcaPañal;
        this.etapaPañal = etapaPañal;
        this.carne = new Carne(registroCivil);
        this.dieta = new Dieta(registroCivil);
        this.jornadas = new ArrayList<>();
    }

    public int getEdadEnMeses() {
        return Period.between(fechaNacimiento, LocalDate.now()).getMonths()
                + Period.between(fechaNacimiento, LocalDate.now()).getYears() * 12;
    }

    public abstract String getTipoNino();

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getRegistroCivil() { return registroCivil; }
    public void setRegistroCivil(int registroCivil) { this.registroCivil = registroCivil; }

    public String getMarcaPañal() { return marcaPañal; }
    public void setMarcaPañal(String marcaPañal) { this.marcaPañal = marcaPañal; }

    public String getEtapaPañal() { return etapaPañal; }
    public void setEtapaPañal(String etapaPañal) { this.etapaPañal = etapaPañal; }

    public Carne getCarne() { return carne; }
    public void setCarne(Carne carne) { this.carne = carne; }

    public Dieta getDieta() { return dieta; }
    public void setDieta(Dieta dieta) { this.dieta = dieta; }

    public List<Jornada> getJornadas() { return jornadas; }
    public void setJornadas(List<Jornada> jornadas) { this.jornadas = jornadas; }
    public void agregarJornada(Jornada jornada) { this.jornadas.add(jornada); }
    public void eliminarJornada(Jornada jornada) { this.jornadas.remove(jornada); }

    @Override
    public String toString() {
        return nombre + " (" + getTipoNino() + ") - RC: " + registroCivil;
    }
}