package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Acostadito extends Nino {
    private String marcaLeche;
    private int cantidadOnzas;
    private boolean lecheMaterna;
    private List<Integer> codigosRecipientes;

    public Acostadito(String nombre, LocalDate fechaNacimiento, int registroCivil,
                      String marcaPañal, String etapaPañal,
                      String marcaLeche, int cantidadOnzas, boolean lecheMaterna) {
        super(nombre, fechaNacimiento, registroCivil, marcaPañal, etapaPañal);
        this.marcaLeche = marcaLeche;
        this.cantidadOnzas = cantidadOnzas;
        this.lecheMaterna = lecheMaterna;
        this.codigosRecipientes = new ArrayList<>();
    }

    public void agregarCodigoRecipiente(int codigo) {
        codigosRecipientes.add(codigo);
    }

    @Override
    public String getTipoNino() { return "Acostadito (0-6 meses)"; }

    public String getMarcaLeche() { return marcaLeche; }
    public void setMarcaLeche(String marcaLeche) { this.marcaLeche = marcaLeche; }

    public int getCantidadOnzas() { return cantidadOnzas; }
    public void setCantidadOnzas(int cantidadOnzas) { this.cantidadOnzas = cantidadOnzas; }

    public boolean isLecheMaterna() { return lecheMaterna; }
    public void setLecheMaterna(boolean lecheMaterna) { this.lecheMaterna = lecheMaterna; }

    public List<Integer> getCodigosRecipientes() { return codigosRecipientes; }
    public void setCodigosRecipientes(List<Integer> codigosRecipientes) { this.codigosRecipientes = codigosRecipientes; }
}
