package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dieta implements Serializable {
    private int id;
    private List<Alimento> alimentos;

    public Dieta(int id) {
        this.id = id;
        this.alimentos = new ArrayList<>();
    }

    public void agregarAlimento(Alimento alimento) {
        alimentos.add(alimento);
    }

    public void eliminarAlimento(String nombre) {
        alimentos.removeIf(a -> a.getNombre().equalsIgnoreCase(nombre));
    }

    public List<Alimento> getAlimentosPermitidos() {
        List<Alimento> permitidos = new ArrayList<>();
        for (Alimento a : alimentos) {
            if (a.isPermitido()) permitidos.add(a);
        }
        return permitidos;
    }

    public List<Alimento> getAlimentosProhibidos() {
        List<Alimento> prohibidos = new ArrayList<>();
        for (Alimento a : alimentos) {
            if (!a.isPermitido()) prohibidos.add(a);
        }
        return prohibidos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Alimento> getAlimentos() { return alimentos; }
    public void setAlimentos(List<Alimento> alimentos) { this.alimentos = alimentos; }
}
