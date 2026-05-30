package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Actividad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO {

    private static final String ARCHIVO = "actividades.csv";
    private static final String SEPARADOR = ",";
    private static final String SEP_RECURSOS = "|";
    private static final String CABECERA = "nombre,costoBase,descripcion,recursos";


    //CRUD principal

    //Agrega una actividad nueva al CSV
    public void agregar(Actividad actividad){

        boolean archivoNuevo = !new File(ARCHIVO).exists();

        try(PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))){

            if(archivoNuevo){
                pw.println(CABECERA);
            }

            pw.println(toCsv(actividad));

        }catch(IOException e){

            throw new RuntimeException("Error al guardar actividades.csv: " + e.getMessage(), e);
        }
    }

    //Lee todas las actividades del CSV. Retorna lista vacía si no existe
    public List<Actividad> listar(){

        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return new ArrayList<>();
        }

        List<Actividad> actividades = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){

            String linea;
            boolean primeraLinea = true;

            while((linea = br.readLine()) != null){

                if(primeraLinea){
                    primeraLinea = false;
                    continue; //saltar cabecera
                }
                if(!linea.isBlank()){
                    actividades.add(fromCsv(linea));
                }
            }
        }catch(IOException e){

            throw new RuntimeException("Error al leer actividades.csv: " + e.getMessage(), e);
        }
        return actividades;
    }

    //Guarda (reemplaza) toda la lista de actividades en el CSV
    public void guardarTodos(List<Actividad> actividades){

        try(PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))){

            pw.println(CABECERA);

            for(Actividad a : actividades){
                pw.println(toCsv(a));
            }
        }catch(IOException e){

            throw new RuntimeException("Error al guardar actividades.csv: " + e.getMessage(), e);
        }
    }

    //Actualiza la actividad cuyo nombre coincide
    public void actualizar(Actividad actividadActualizada){

        List<Actividad> actividades = listar();
        boolean encontrado = false;

        for(int i = 0; i < actividades.size(); i++){

            if(actividades.get(i).getNombre().equalsIgnoreCase(actividadActualizada.getNombre())){
                actividades.set(i, actividadActualizada);
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            throw new RuntimeException("Actividad '" + actividadActualizada.getNombre() + "' no encontrada.");
        }

        guardarTodos(actividades);
    }

    //Elimina la actividad con el nombre dado
    public void eliminar(String nombreActividad){

        List<Actividad> actividades = listar();
        boolean removido = actividades.removeIf(a -> a.getNombre().equalsIgnoreCase(nombreActividad));

        if(!removido){
            throw new RuntimeException("Actividad '" + nombreActividad + "' no encontrada.");
        }
        guardarTodos(actividades);
    }


    //Búsquedas

    //Busca una actividad por nombre. Retorna null si no existe
    public Actividad buscarPorNombre(String nombre){

        return listar().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }


    //Helpers CSV

    private String toCsv(Actividad a){

        String recursos = String.join(SEP_RECURSOS, a.getRecursosNecesarios());
        return escape(a.getNombre()) + SEPARADOR
             + a.getCostoBase() + SEPARADOR
             + escape(a.getDescripcion()) + SEPARADOR
             + escape(recursos);
    }

    private Actividad fromCsv(String linea){

        String[] partes = linea.split(SEPARADOR, 4);
        String nombre      = unescape(partes[0]);
        double costo       = Double.parseDouble(partes[1].trim());
        String descripcion = partes.length > 2 ? unescape(partes[2]) : "";
        Actividad actividad = new Actividad(nombre, costo, descripcion);

        if(partes.length > 3){

            String recursosStr = unescape(partes[3]);

            if(!recursosStr.isBlank()){

                for(String r : recursosStr.split("\\" + SEP_RECURSOS)){
                    actividad.getRecursosNecesarios().add(r.trim());
                }
            }
        }
        return actividad;
    }

    //Envuelve en comillas si el campo contiene la coma separadora
    private String escape(String valor){

        if(valor == null){
            return "";
        }
        if(valor.contains(SEPARADOR) || valor.contains("\"")){
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    private String unescape(String valor){

        if(valor == null){
            return "";
        }
        valor = valor.trim();

        if(valor.startsWith("\"") && valor.endsWith("\"")){
            valor = valor.substring(1, valor.length() - 1).replace("\"\"", "\"");
        }
        return valor;
    }
}
