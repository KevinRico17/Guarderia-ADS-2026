package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NinoDAO {

    private static final String ARCHIVO = "ninos.json";
    private final Gson gson;

    public NinoDAO(){
        gson = construirGson();
    }


    //CRUD principal

    //Guarda (reemplaza) toda la lista de niños en ninos.json
    public void guardarTodos(List<Nino> ninos){

        try(Writer writer = new FileWriter(ARCHIVO)){
            gson.toJson(ninos, writer);

        }catch(IOException e){

            throw new RuntimeException("Error al guardar ninos.json: " + e.getMessage(), e);
        }
    }

    //Lee todos los niños desde ninos.json. Retorna lista vacía si no existe el archivo
    public List<Nino> listar(){

        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return new ArrayList<>();
        }

        try(Reader reader = new FileReader(archivo)){

            Type tipoLista = new TypeToken<List<Nino>>() {}.getType();
            List<Nino> ninos = gson.fromJson(reader, tipoLista);
            return ninos != null ? ninos : new ArrayList<>();

        }catch(IOException e){

            throw new RuntimeException("Error al leer ninos.json: " + e.getMessage(), e);
        }
    }

    //Agrega un niño nuevo y persiste
    public void agregar(Nino nino){

        List<Nino> ninos = listar();
        ninos.add(nino);
        guardarTodos(ninos);
    }

    //Actualiza el niño cuyo registroCivil coincide
    public void actualizar(Nino ninoActualizado){

        List<Nino> ninos = listar();

        for(int i = 0; i < ninos.size(); i++){

            if(ninos.get(i).getRegistroCivil() == ninoActualizado.getRegistroCivil()){
                ninos.set(i, ninoActualizado);
                guardarTodos(ninos);
                return;
            }
        }
        throw new RuntimeException("Niño con RC " + ninoActualizado.getRegistroCivil() + " no encontrado.");
    }

    //Elimina el niño con el registro civil dado
    public void eliminar(int registroCivil){

        List<Nino> ninos = listar();
        boolean removido = ninos.removeIf(n -> n.getRegistroCivil() == registroCivil);

        if(!removido){
            throw new RuntimeException("Niño con RC " + registroCivil + " no encontrado.");
        }

        guardarTodos(ninos);
    }


    //Búsquedas

    //Busca un niño por su número de registro civil. Retorna null si no existe
    public Nino buscarPorRegistroCivil(int registroCivil){

        return listar().stream()
                .filter(n -> n.getRegistroCivil() == registroCivil)
                .findFirst()
                .orElse(null);
    }

    //Retorna todos los niños que no tienen ninguna dosis registrada
    public List<Nino> filtrarSinVacuna(List<Nino> ninos){

        List<Nino> pendientes = new ArrayList<>();

        for(Nino n : ninos){

            if(n.getCarne() == null || n.getCarne().getDosis().isEmpty()){
                pendientes.add(n);
            }
        }
        return pendientes;
    }


    //Exportaciones

    //Exporta la dieta de un niño como archivo de texto
    //Nombre del archivo: dieta_RC.txt
    public void exportarDieta(Nino nino){

        String nombreArchivo = "dieta_" + nino.getRegistroCivil() + ".txt";

        try(PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))){

            pw.println("=== DIETA PERSONALIZADA ===");
            pw.println("Niño: " + nino.getNombre());
            pw.println("Registro Civil: " + nino.getRegistroCivil());
            pw.println();

            if(nino.getDieta() != null){

                pw.println("--- Alimentos PERMITIDOS ---");
                nino.getDieta().getAlimentosPermitidos()
                    .forEach(a -> pw.println("  + " + a.getNombre() + " | " + a.getCantidad()));
                pw.println();

                pw.println("--- Alimentos PROHIBIDOS ---");
                nino.getDieta().getAlimentosProhibidos()
                    .forEach(a -> pw.println("  x " + a.getNombre() + " | Razón: " + a.getRazon()));
            }
        }catch(IOException e){

            throw new RuntimeException("Error al exportar dieta: " + e.getMessage(), e);
        }
    }

    //Exporta el carné de vacunas de un niño como JSON
    //Nombre del archivo: carne_RC.json
    public void exportarCarne(Nino nino){

        String nombreArchivo = "carne_" + nino.getRegistroCivil() + ".json";

        try(Writer writer = new FileWriter(nombreArchivo)){
            gson.toJson(nino.getCarne(), writer);

        }catch(IOException e){

            throw new RuntimeException("Error al exportar carné: " + e.getMessage(), e);
        }
    }


    //Configuración Gson con soporte de polimorfismo y LocalDate

    private Gson construirGson(){

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(Nino.class, new NinoAdapter())
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, ctx) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    //Adaptador personalizado para serializar/deserializar la jerarquía de Nino
    //Agrega un campo "__tipo" al JSON para poder reconstruir la subclase correcta
    private static class NinoAdapter implements JsonSerializer<Nino>, JsonDeserializer<Nino>{

        private static final String CAMPO_TIPO = "__tipo";

        @Override
        public JsonElement serialize(Nino nino, Type type, JsonSerializationContext ctx){

            JsonObject obj = ctx.serialize(nino, nino.getClass()).getAsJsonObject();
            obj.addProperty(CAMPO_TIPO, nino.getClass().getSimpleName());
            return obj;
        }

        @Override
        public Nino deserialize(JsonElement json, Type type, JsonDeserializationContext ctx)
                throws JsonParseException{

            JsonObject obj = json.getAsJsonObject();
            String tipoNino = obj.has(CAMPO_TIPO) ? obj.get(CAMPO_TIPO).getAsString() : "";

            return switch(tipoNino){
                case "Acostadito" -> ctx.deserialize(obj, Acostadito.class);
                case "Aventurero" -> ctx.deserialize(obj, Aventurero.class);
                case "Trotamundo" -> ctx.deserialize(obj, Trotamundo.class);
                case "Jugueton"   -> ctx.deserialize(obj, Jugueton.class);
                case "Parvulo"    -> ctx.deserialize(obj, Parvulo.class);
                default -> throw new JsonParseException("Tipo de niño desconocido: " + tipoNino);
            };
        }
    }
}
