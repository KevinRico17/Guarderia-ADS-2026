package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.guarderia.Sucursal;
import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import co.edu.javeriana.guarderiaadswagugu.modelo.ninos.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SucursalDAO {

    private static final String ARCHIVO = "sucursales.json";
    private final Gson gson;

    public SucursalDAO(){
        gson = construirGson();
    }


    //CRUD principal

    //Guarda (reemplaza) toda la lista de sucursales
    public void guardarTodos(List<Sucursal> sucursales){

        try(Writer writer = new FileWriter(ARCHIVO)){
            gson.toJson(sucursales, writer);

        }catch(IOException e){

            throw new RuntimeException("Error al guardar sucursales.json: " + e.getMessage(), e);
        }
    }

    //Lee todas las sucursales. Retorna lista vacía si no existe el archivo
    public List<Sucursal> listar(){

        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return new ArrayList<>();
        }

        try(Reader reader = new FileReader(archivo)){

            Type tipoLista = new TypeToken<List<Sucursal>>() {}.getType();
            List<Sucursal> sucursales = gson.fromJson(reader, tipoLista);
            return sucursales != null ? sucursales : new ArrayList<>();

        }catch(IOException e){

            throw new RuntimeException("Error al leer sucursales.json: " + e.getMessage(), e);
        }
    }

    //Agrega una sucursal nueva
    public void agregar(Sucursal sucursal){

        List<Sucursal> sucursales = listar();
        sucursales.add(sucursal);
        guardarTodos(sucursales);
    }

    //Actualiza la sucursal cuyo nombre coincide
    public void actualizar(Sucursal sucursalActualizada){

        List<Sucursal> sucursales = listar();

        for(int i = 0; i < sucursales.size(); i++){

            if(sucursales.get(i).getNombre().equalsIgnoreCase(sucursalActualizada.getNombre())){
                sucursales.set(i, sucursalActualizada);
                guardarTodos(sucursales);
                return;
            }
        }
        throw new RuntimeException("Sucursal '" + sucursalActualizada.getNombre() + "' no encontrada.");
    }


    //Búsquedas

    //Retorna todas las sucursales de una localidad
    public List<Sucursal> buscarPorLocalidad(String localidad){

        List<Sucursal> resultado = new ArrayList<>();

        for(Sucursal s : listar()){

            if(s.getLocalidad().equalsIgnoreCase(localidad)){
                resultado.add(s);
            }
        }
        return resultado;
    }


    //Exportaciones Servicio 5

    //Genera resumen TXT con ingresos, gastos y balance de las sucursales
    //Nombre del archivo: consolidado_localidad.txt
    public void exportarResumenTXT(String localidad, List<Sucursal> sucursales){

        String nombreArchivo = "consolidado_" + localidad.replaceAll("\\s+", "_") + ".txt";

        try(PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))){

            pw.println("=== CONSOLIDADO DE INGRESOS Y GASTOS ===");
            pw.println("Localidad: " + localidad);
            pw.println("Fecha: " + LocalDate.now());
            pw.println();
            pw.printf("%-25s %15s %15s %15s%n", "Sucursal", "Ingresos", "Gastos", "Balance");
            pw.println("-".repeat(72));

            double totalIngresos = 0, totalGastos = 0;

            for(Sucursal s : sucursales){

                double balance = s.getIngresos() - s.getGastos();
                pw.printf("%-25s %15.2f %15.2f %15.2f%n",
                        s.getNombre(), s.getIngresos(), s.getGastos(), balance);
                totalIngresos += s.getIngresos();
                totalGastos   += s.getGastos();
            }
            pw.println("-".repeat(72));
            pw.printf("%-25s %15.2f %15.2f %15.2f%n",
                    "TOTAL", totalIngresos, totalGastos, totalIngresos - totalGastos);

        }catch(IOException e){

            throw new RuntimeException("Error al exportar resumen TXT: " + e.getMessage(), e);
        }
    }

    //Exporta el consolidado como CSV
    //Nombre del archivo: consolidado_localidad.csv
    public void exportarCSV(String localidad, List<Sucursal> sucursales){

        String nombreArchivo = "consolidado_" + localidad.replaceAll("\\s+", "_") + ".csv";

        try(PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))){

            pw.println("sucursal,ingresos,gastos,balance");

            for(Sucursal s : sucursales){

                double balance = s.getIngresos() - s.getGastos();
                pw.printf("%s,%.2f,%.2f,%.2f%n",
                        s.getNombre(), s.getIngresos(), s.getGastos(), balance);
            }
        }catch(IOException e){

            throw new RuntimeException("Error al exportar CSV: " + e.getMessage(), e);
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
