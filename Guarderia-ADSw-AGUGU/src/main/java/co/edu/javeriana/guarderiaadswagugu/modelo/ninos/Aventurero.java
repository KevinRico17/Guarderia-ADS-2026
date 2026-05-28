package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aventurero extends Nino {
    private String marcaLeche;
    private int cantidadOnzas;
    private boolean lecheMaterna;
    private List<Integer> codigosRecipientes;
    private int codigoJuguete;
    private String nombreJuguete;
    private int edadCaminataEnMeses;
    private boolean usaCaminador;
    private Caminador caminador;

    public Aventurero(String nombre, LocalDate fechaNacimiento, int registroCivil,
                      String marcaPañal, String etapaPañal,
                      String marcaLeche, int cantidadOnzas, boolean lecheMaterna,
                      int codigoJuguete, String nombreJuguete, int edadCaminataEnMeses, boolean usaCaminador) {
        super(nombre, fechaNacimiento, registroCivil, marcaPañal, etapaPañal);
        this.marcaLeche = marcaLeche;
        this.cantidadOnzas = cantidadOnzas;
        this.lecheMaterna = lecheMaterna;
        this.codigosRecipientes = new ArrayList<>();
        this.codigoJuguete = codigoJuguete;
        this.nombreJuguete = nombreJuguete;
        this.edadCaminataEnMeses = edadCaminataEnMeses;
        this.usaCaminador = usaCaminador;
    }

    @Override
    public String getTipoNino() { return "Aventurero (6-18 meses)"; }

    public String getMarcaLeche() { return marcaLeche; }
    public void setMarcaLeche(String marcaLeche) { this.marcaLeche = marcaLeche; }

    public int getCantidadOnzas() { return cantidadOnzas; }
    public void setCantidadOnzas(int cantidadOnzas) { this.cantidadOnzas = cantidadOnzas; }

    public boolean isLecheMaterna() { return lecheMaterna; }
    public void setLecheMaterna(boolean lecheMaterna) { this.lecheMaterna = lecheMaterna; }

    public List<Integer> getCodigosRecipientes() { return codigosRecipientes; }
    public void setCodigosRecipientes(List<Integer> codigosRecipientes) { this.codigosRecipientes = codigosRecipientes; }

    public int getCodigoJuguete() { return codigoJuguete; }
    public void setCodigoJuguete(int codigoJuguete) { this.codigoJuguete = codigoJuguete; }

    public String getNombreJuguete() { return nombreJuguete; }
    public void setNombreJuguete(String nombreJuguete) { this.nombreJuguete = nombreJuguete; }

    public int getEdadCaminataEnMeses() { return edadCaminataEnMeses; }
    public void setEdadCaminataEnMeses(int edadCaminataEnMeses) { this.edadCaminataEnMeses = edadCaminataEnMeses; }

    public boolean isUsaCaminador() { return usaCaminador; }
    public void setUsaCaminador(boolean usaCaminador) { this.usaCaminador = usaCaminador; }

    public Caminador getCaminador() { return caminador; }
    public void setCaminador(Caminador caminador) { this.caminador = caminador; }
}
