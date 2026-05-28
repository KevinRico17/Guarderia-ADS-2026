package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {

    private String nombre;
    private List<Acudiente> acudientes;
    private List<Nino> ninos;

    public Cliente(String nombre) {
        this.nombre = nombre;
        this.acudientes = new ArrayList<>();
        this.ninos = new ArrayList<>();
    }


    public boolean esFamiliaCliente() {
        return ninos.size() > 1;
    }

    public double calcularPension() {
        double salarioMaximo = acudientes.stream()
                .mapToDouble(Acudiente::getSalario)
                .max()
                .orElse(0);
        return salarioMaximo * 0.15;
    }

    public void agregarAcudiente(Acudiente acudiente) {
        acudientes.add(acudiente);
    }

    public void agregarNino(Nino nino) {
        ninos.add(nino);
    }

    public void removerNino(Nino nino) {
        ninos.remove(nino);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Acudiente> getAcudientes() { return acudientes; }
    public void setAcudientes(List<Acudiente> acudientes) { this.acudientes = acudientes; }

    public List<Nino> getNinos() { return ninos; }
    public void setNinos(List<Nino> ninos) { this.ninos = ninos; }

    @Override
    public String toString() {
        return nombre + (esFamiliaCliente() ? " (Familia Cliente)" : "") + " - " + ninos.size() + " niño(s)";
    }
}
