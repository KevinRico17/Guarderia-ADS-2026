package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carne implements Serializable {
    private int id;
    private List<Dosis> dosis;

    public Carne(int id) {
        this.id = id;
        this.dosis = new ArrayList<>();
    }

    public void agregarDosis(Dosis d) {
        dosis.add(d);
    }

    public List<Dosis> getDosisPorEnfermedad(String enfermedad) {
        List<Dosis> resultado = new ArrayList<>();
        for (Dosis d : dosis) {
            if (d.getEnfermedad().equalsIgnoreCase(enfermedad)) resultado.add(d);
        }
        return resultado;
    }

    public boolean tieneVacuna(String enfermedad) {
        return dosis.stream().anyMatch(d -> d.getEnfermedad().equalsIgnoreCase(enfermedad));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Dosis> getDosis() { return dosis; }
    public void setDosis(List<Dosis> dosis) { this.dosis = dosis; }
}
