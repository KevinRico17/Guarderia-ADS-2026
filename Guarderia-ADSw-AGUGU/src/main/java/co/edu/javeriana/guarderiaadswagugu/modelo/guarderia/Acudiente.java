package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Acudiente implements Serializable {
    private String nombre;
    private String direccion;
    private String telefonoFijo;
    private String telefonoCelular;
    private String empresaNombre;
    private String empresaDireccion;
    private String empresaTelefono;
    private double salario;
    private String telefonoEmergencia;
    private List<Nino> ninos;

    public Acudiente(String nombre, String direccion, String telefonoFijo,
                     String telefonoCelular, double salario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.salario = salario;
        this.ninos = new ArrayList<>();
    }

    public double calcularPension(double salarioMaximo) {
        return salarioMaximo * 0.15;
    }

    public void agregarNino(Nino nino) { ninos.add(nino); }

    public boolean esFamiliaCliente() { return ninos.size() > 1; }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefonoFijo() { return telefonoFijo; }
    public void setTelefonoFijo(String telefonoFijo) { this.telefonoFijo = telefonoFijo; }

    public String getTelefonoCelular() { return telefonoCelular; }
    public void setTelefonoCelular(String telefonoCelular) { this.telefonoCelular = telefonoCelular; }

    public String getEmpresaNombre() { return empresaNombre; }
    public void setEmpresaNombre(String empresaNombre) { this.empresaNombre = empresaNombre; }

    public String getEmpresaDireccion() { return empresaDireccion; }
    public void setEmpresaDireccion(String empresaDireccion) { this.empresaDireccion = empresaDireccion; }

    public String getEmpresaTelefono() { return empresaTelefono; }
    public void setEmpresaTelefono(String empresaTelefono) { this.empresaTelefono = empresaTelefono; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public String getTelefonoEmergencia() { return telefonoEmergencia; }
    public void setTelefonoEmergencia(String telefonoEmergencia) { this.telefonoEmergencia = telefonoEmergencia; }

    public List<Nino> getNinos() { return ninos; }
    public void setNinos(List<Nino> ninos) { this.ninos = ninos; }
}
