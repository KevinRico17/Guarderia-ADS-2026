package co.edu.javeriana.guarderiaadswagugu.modelo.ninos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jugueton extends Nino {
    private List<Competencia> competencias;

    public Jugueton(String nombre, LocalDate fechaNacimiento, int registroCivil,
                    String marcaPañal, String etapaPañal) {
        super(nombre, fechaNacimiento, registroCivil, marcaPañal, etapaPañal);
        this.competencias = new ArrayList<>();
    }

    public void agregarCompetencia(Competencia competencia) {
        competencias.add(competencia);
    }

    @Override
    public String getTipoNino() { return "Jugueton/Aprendiz (24-36 meses)"; }

    public List<Competencia> getCompetencias() { return competencias; }
    public void setCompetencias(List<Competencia> competencias) { this.competencias = competencias; }
}
