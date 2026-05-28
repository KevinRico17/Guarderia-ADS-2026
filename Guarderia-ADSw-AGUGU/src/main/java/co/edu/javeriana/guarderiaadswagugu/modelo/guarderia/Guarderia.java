package co.edu.javeriana.guarderiaadswagugu.modelo.guarderia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Guarderia implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private List<Localidad> localidades;

    public Guarderia(String nombre) {
        this.nombre = nombre;
        this.localidades = new ArrayList<>();
    }

    public void agregarLocalidad(Localidad localidad) { localidades.add(localidad); }

    public Sucursal buscarSucursalMasCercana(String direccionReferencia) {
        // Lógica simplificada: retorna la primera sucursal disponible
        // En implementación real se usaría geolocalización
        for (Localidad l : localidades) {
            for (Sucursal s : l.getSucursales()) {
                return s;
            }
        }
        return null;
    }

    public double getTotalIngresosGeneral() {
        return localidades.stream().mapToDouble(Localidad::getTotalIngresos).sum();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Localidad> getLocalidades() { return localidades; }
    public void setLocalidades(List<Localidad> localidades) { this.localidades = localidades; }
}
