package co.edu.javeriana.guarderiaadswagugu.modelo.empleados;

import java.time.LocalDate;

public class Administrador extends Empleado {
    private String tipoAdmin; // de la sucursal o de una localidad
    private String entidadACargo; // nombre de la sucursal o localidad

    public Administrador(String nombre, int cedula, LocalDate fechaNacimiento,
                         String direccion, String titulo, int tarjetaProfesional,
                         int experienciaAnios, boolean realizoInvestigacion,
                         String tipoAdmin, String entidadACargo) {
        super(nombre, cedula, fechaNacimiento, direccion, titulo, tarjetaProfesional,
              "ADMINISTRADOR", experienciaAnios, realizoInvestigacion);
        this.tipoAdmin = tipoAdmin;
        this.entidadACargo = entidadACargo;
    }

    public void registrarEvaluacion(Empleado empleado, Evaluacion evaluacion) {
        empleado.agregarEvaluacion(evaluacion);
        double nuevoSalario = empleado.getSalario() * (1 + evaluacion.getAjusteSalario());
        empleado.setSalario(nuevoSalario);
    }

    public String getTipoAdmin() { return tipoAdmin; }
    public void setTipoAdmin(String tipoAdmin) { this.tipoAdmin = tipoAdmin; }

    public String getEntidadACargo() { return entidadACargo; }
    public void setEntidadACargo(String entidadACargo) { this.entidadACargo = entidadACargo; }
}
