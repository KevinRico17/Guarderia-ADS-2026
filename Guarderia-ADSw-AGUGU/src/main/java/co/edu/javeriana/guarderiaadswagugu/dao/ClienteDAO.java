package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Cliente;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String ARCHIVO = "clientes.json";
    private final Gson gson;

    public ClienteDAO(){
        gson = construirGson();
    }


    //CRUD principal

    //Guarda (reemplaza) toda la lista de clientes en clientes.json
    public void guardarTodos(List<Cliente> clientes){

        try(Writer writer = new FileWriter(ARCHIVO)){
            gson.toJson(clientes, writer);

        }catch(IOException e){

            throw new RuntimeException("Error al guardar clientes.json: " + e.getMessage(), e);
        }
    }

    //Lee todos los clientes desde clientes.json. Retorna lista vacía si no existe
    public List<Cliente> listar(){

        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return new ArrayList<>();
        }

        try(Reader reader = new FileReader(archivo)){

            Type tipoLista = new TypeToken<List<Cliente>>() {}.getType();
            List<Cliente> clientes = gson.fromJson(reader, tipoLista);
            return clientes != null ? clientes : new ArrayList<>();

        }catch(IOException e){

            throw new RuntimeException("Error al leer clientes.json: " + e.getMessage(), e);
        }
    }

    //Agrega un cliente nuevo y persiste
    public void agregar(Cliente cliente){

        List<Cliente> clientes = listar();
        clientes.add(cliente);
        guardarTodos(clientes);
    }

    //Actualiza el cliente cuyo nombre coincide
    public void actualizar(Cliente clienteActualizado){

        List<Cliente> clientes = listar();

        for(int i = 0; i < clientes.size(); i++){

            if(clientes.get(i).getNombre().equalsIgnoreCase(clienteActualizado.getNombre())){
                clientes.set(i, clienteActualizado);
                guardarTodos(clientes);
                return;
            }
        }
        throw new RuntimeException("Cliente '" + clienteActualizado.getNombre() + "' no encontrado.");
    }

    //Elimina el cliente con el nombre dado
    public void eliminar(String nombreCliente){

        List<Cliente> clientes = listar();
        boolean removido = clientes.removeIf(c -> c.getNombre().equalsIgnoreCase(nombreCliente));

        if(!removido) throw new RuntimeException("Cliente '" + nombreCliente + "' no encontrado.");

        guardarTodos(clientes);
    }


    //Búsquedas

    //Busca un cliente por el nombre del acudiente
    //Retorna null si no existe (familia nueva)
    public Cliente buscarPorNombreAcudiente(String nombreAcudiente){

        return listar().stream()
                .filter(c -> c.getAcudientes().stream()
                        .anyMatch(a -> a.getNombre().equalsIgnoreCase(nombreAcudiente)))
                .findFirst()
                .orElse(null);
    }

    //Busca un cliente por el nombre de la familia
    public Cliente buscarPorNombre(String nombreCliente){

        return listar().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreCliente))
                .findFirst()
                .orElse(null);
    }


    //Configuración Gson

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
