package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Parvulo extends Nino {
    private List<String> actividadesAprestamiento; // pintura, picado, moldeado, corte...
    private int nivelAprestamiento;
    private boolean tieneProfesorIdiomas;
    private String idioma; // inglés, francés, alemán, mandarín

    public Parvulo(String nombre, LocalDate fechaNacimiento, int registroCivil,
                   String marcaPañal, String etapaPañal, int nivelAprestamiento) {
        super(nombre, fechaNacimiento, registroCivil, marcaPañal, etapaPañal);
        this.nivelAprestamiento = nivelAprestamiento;
        this.actividadesAprestamiento = new ArrayList<>();
        this.tieneProfesorIdiomas = false;
        this.idioma = null;
    }

    public void contratarProfesorIdiomas(String idioma) {
        this.tieneProfesorIdiomas = true;
        this.idioma = idioma;
    }

    @Override
    public String getTipoNino() { return "Párvulo (36 meses - 5 años)"; }

    public List<String> getActividadesAprestamiento() { return actividadesAprestamiento; }
    public void setActividadesAprestamiento(List<String> actividadesAprestamiento) { this.actividadesAprestamiento = actividadesAprestamiento; }

    public int getNivelAprestamiento() { return nivelAprestamiento; }
    public void setNivelAprestamiento(int nivelAprestamiento) { this.nivelAprestamiento = nivelAprestamiento; }

    public boolean isTieneProfesorIdiomas() { return tieneProfesorIdiomas; }
    public void setTieneProfesorIdiomas(boolean tieneProfesorIdiomas) { this.tieneProfesorIdiomas = tieneProfesorIdiomas; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
}
