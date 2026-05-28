package co.edu.javeriana.guarderiaadswagugu.modelo.empleados;

import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Especialista extends Empleado {

    public enum Especialidad {
        PEDIATRA,       // gestiona vacunas
        NUTRICIONISTA,  // gestiona dietas
        PROFESOR,       // gestiona actividades
        PSICOLOGO,      // seguimiento cognitivo
        TERAPEUTA       // terapia de lenguaje y ocupacional
    }

    private Especialidad especialidad;
    private List<Nino> ninosACargo;

    public Especialista(String nombre, int cedula, LocalDate fechaNacimiento,
                        String direccion, String titulo, int tarjetaProfesional,
                        String cargo, int experienciaAnios, boolean realizoInvestigacion,
                        Especialidad especialidad) {
        super(nombre, cedula, fechaNacimiento, direccion, titulo, tarjetaProfesional,
                cargo, experienciaAnios, realizoInvestigacion);
        this.especialidad = especialidad;
        this.ninosACargo = new ArrayList<>();
    }

    public void asignarNino(Nino nino) { ninosACargo.add(nino); }
    public void removerNino(Nino nino) { ninosACargo.remove(nino); }

    public boolean puedeGestionarVacunas() { return especialidad == Especialidad.PEDIATRA; }
    public boolean puedeGestionarDietas() { return especialidad == Especialidad.NUTRICIONISTA; }
    public boolean puedeGestionarActividades() { return especialidad == Especialidad.PROFESOR; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public List<Nino> getNinosACargo() { return ninosACargo; }
    public void setNinosACargo(List<Nino> ninosACargo) { this.ninosACargo = ninosACargo; }
}
