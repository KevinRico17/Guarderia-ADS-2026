package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.io.Serializable;
import java.time.LocalDate;

public class Caminador implements Serializable {
    private int codigo;
    private String marca;
    private String color;
    private String estado; // "OPTIMO", "DAÑADO", "EN_REPARACION"
    private LocalDate fechaAdquisicion;

    public Caminador(int codigo, String marca, String color, String estado, LocalDate fechaAdquisicion) {
        this.codigo = codigo;
        this.marca = marca;
        this.color = color;
        this.estado = estado;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaAdquisicion() { return fechaAdquisicion; }
    public void setFechaAdquisicion(LocalDate fechaAdquisicion) { this.fechaAdquisicion = fechaAdquisicion; }
}
