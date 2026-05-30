package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Guarderia;
import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.*;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuarderiaDAO {

    private static final String ARCHIVO_DAT = "guarderia.dat";
    private final Gson gson;

    public GuarderiaDAO(){
        gson = construirGson();
    }


    //Serialización binaria (.dat)

    //Serializa el objeto Guarderia completo a guarderia.dat
    //Retorna el timestamp del momento de guardado
    public String serializar(Guarderia guarderia){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DAT))){

            oos.writeObject(guarderia);
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return timestamp;

        }catch(IOException e){

            throw new RuntimeException("Error al serializar el sistema: " + e.getMessage(), e);
        }
    }

    //Deserializa el estado del sistema desde guarderia.dat (por defecto) o desde la ruta indicada
    //Retorna el objeto Guarderia reconstruido
    public Guarderia deserializar(String ruta){

        String archivoDestino = (ruta != null && !ruta.isBlank()) ? ruta : ARCHIVO_DAT;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDestino))){

            return (Guarderia) ois.readObject();

        }catch(IOException | ClassNotFoundException e){

            throw new RuntimeException("Error al deserializar el sistema desde '" + archivoDestino + "': " + e.getMessage(), e);
        }
    }

    //Sobrecarga que usa guarderia.dat por defecto
    public Guarderia deserializar(){
        return deserializar(ARCHIVO_DAT);
    }


    //Backup JSON

    //Genera un backup completo del sistema en JSON
    //Nombre del archivo: backup_yyyyMMdd_HHmmss.json
    //Retorna la ruta del archivo generado
    public String generarBackupJSON(Guarderia guarderia){

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = "backup_" + timestamp + ".json";

        try(Writer writer = new FileWriter(nombreArchivo)){

            gson.toJson(guarderia, writer);
            return nombreArchivo;

        }catch(IOException e){

            throw new RuntimeException("Error al generar backup JSON: " + e.getMessage(), e);
        }
    }


    //Configuración Gson

    private Gson construirGson(){

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(Nino.class, new NinoAdapter())
                .registerTypeHierarchyAdapter(Empleado.class, new EmpleadoAdapter())
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, ctx) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    private static class NinoAdapter implements JsonSerializer<Nino>, JsonDeserializer<Nino>{

        private static final String CAMPO_TIPO = "__tipo";

        @Override
        public JsonElement serialize(Nino nino, Type type, JsonSerializationContext ctx){

            JsonObject obj = ctx.serialize(nino, nino.getClass()).getAsJsonObject();
            obj.addProperty(CAMPO_TIPO, nino.getClass().getSimpleName());
            return obj;
        }

        @Override
        public Nino deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException{

            JsonObject obj = json.getAsJsonObject();
            String t = obj.has(CAMPO_TIPO) ? obj.get(CAMPO_TIPO).getAsString() : "";

            return switch(t){
                case "Acostadito" -> ctx.deserialize(obj, Acostadito.class);
                case "Aventurero" -> ctx.deserialize(obj, Aventurero.class);
                case "Trotamundo" -> ctx.deserialize(obj, Trotamundo.class);
                case "Jugueton"   -> ctx.deserialize(obj, Jugueton.class);
                case "Parvulo"    -> ctx.deserialize(obj, Parvulo.class);
                default -> throw new JsonParseException("Tipo desconocido: " + t);
            };
        }
    }

    private static class EmpleadoAdapter implements JsonSerializer<Empleado>, JsonDeserializer<Empleado>{

        @Override
        public JsonElement serialize(Empleado emp, Type type, JsonSerializationContext ctx){
            return ctx.serialize(emp, Empleado.class);
        }

        @Override
        public Empleado deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException{
            return ctx.deserialize(json, Empleado.class);
        }
    }
}
