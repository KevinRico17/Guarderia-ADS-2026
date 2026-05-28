package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Localidad implements Serializable {
    private String nombre;
    private List<Sucursal> sucursales;

    public Localidad(String nombre) {
        this.nombre = nombre;
        this.sucursales = new ArrayList<>();
    }

    public void agregarSucursal(Sucursal sucursal) { sucursales.add(sucursal); }

    public double getTotalIngresos() {
        return sucursales.stream().mapToDouble(Sucursal::getIngresos).sum();
    }

    public double getTotalGastos() {
        return sucursales.stream().mapToDouble(Sucursal::getGastos).sum();
    }

    public int getTotalNinos() {
        return sucursales.stream().mapToInt(s -> s.getNinos().size()).sum();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Sucursal> getSucursales() { return sucursales; }
    public void setSucursales(List<Sucursal> sucursales) { this.sucursales = sucursales; }
}
