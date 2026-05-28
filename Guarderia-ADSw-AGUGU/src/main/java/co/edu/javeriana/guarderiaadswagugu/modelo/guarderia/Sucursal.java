package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;


import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.Nino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sucursal implements Serializable {
    private String nombre;
    private String direccion;
    private String localidad;
    private List<Nino> ninos;
    private List<Nino> listaEspera;
    private List<Empleado> empleados;
    private Map<String, Integer> cupos; // tipoNino -> capacidad maxima
    private double ingresos;
    private double gastos;

    public Sucursal(String nombre, String direccion, String localidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.ninos = new ArrayList<>();
        this.listaEspera = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.cupos = new HashMap<>();
        inicializarCupos();
    }

    private void inicializarCupos() {
        cupos.put("Acostadito", 10);
        cupos.put("Aventurero", 12);
        cupos.put("Trotamundo", 15);
        cupos.put("Jugueton", 15);
        cupos.put("Parvulo", 20);
    }

    public boolean hayCupo(String tipoNino) {
        int capacidad = cupos.getOrDefault(tipoNino, 0);
        long actuales = ninos.stream()
            .filter(n -> n.getTipoNino().contains(tipoNino))
            .count();
        return actuales < capacidad;
    }

    public boolean inscribirNino(Nino nino) {
        String tipo = nino.getTipoNino().split(" ")[0];
        if (hayCupo(tipo)) {
            ninos.add(nino);
            return true;
        }
        listaEspera.add(nino);
        return false;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public List<Nino> getNinos() { return ninos; }
    public void setNinos(List<Nino> ninos) { this.ninos = ninos; }

    public List<Nino> getListaEspera() { return listaEspera; }
    public void setListaEspera(List<Nino> listaEspera) { this.listaEspera = listaEspera; }

    public List<Empleado> getEmpleados() { return empleados; }
    public void setEmpleados(List<Empleado> empleados) { this.empleados = empleados; }

    public Map<String, Integer> getCupos() { return cupos; }
    public void setCupos(Map<String, Integer> cupos) { this.cupos = cupos; }

    public double getIngresos() { return ingresos; }
    public void setIngresos(double ingresos) { this.ingresos = ingresos; }

    public double getGastos() { return gastos; }
    public void setGastos(double gastos) { this.gastos = gastos; }

    @Override
    public String toString() { return nombre + " - " + direccion; }
}
