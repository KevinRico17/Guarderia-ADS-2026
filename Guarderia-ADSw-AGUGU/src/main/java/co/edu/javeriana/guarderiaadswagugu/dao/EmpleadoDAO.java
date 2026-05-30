package co.edu.javeriana.guarderiaadswagugu.dao;

import co.edu.javeriana.guarderiaadswagugu.modelo.empleados.Empleado;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private static final String ARCHIVO = "empleados.json";
    private final Gson gson;

    public EmpleadoDAO(){
        gson = construirGson();
    }


    //CRUD principal

    //Guarda (reemplaza) toda la lista de empleados en empleados.json
    public void guardarTodos(List<Empleado> empleados){

        try(Writer writer = new FileWriter(ARCHIVO)){
            gson.toJson(empleados, writer);

        }catch(IOException e){

            throw new RuntimeException("Error al guardar empleados.json: " + e.getMessage(), e);
        }
    }

    //Lee todos los empleados desde empleados.json. Retorna lista vacía si no existe
    public List<Empleado> listar(){

        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return new ArrayList<>();
        }

        try(Reader reader = new FileReader(archivo)){

            Type tipoLista = new TypeToken<List<Empleado>>() {}.getType();
            List<Empleado> empleados = gson.fromJson(reader, tipoLista);
            return empleados != null ? empleados : new ArrayList<>();

        }catch(IOException e){

            throw new RuntimeException("Error al leer empleados.json: " + e.getMessage(), e);
        }
    }

    //Agrega un empleado nuevo y persiste
    public void agregar(Empleado empleado){

        List<Empleado> empleados = listar();
        empleados.add(empleado);
        guardarTodos(empleados);
    }

    //Actualiza el empleado cuya cédula coincide
    //Se usa después de registrar una evaluación o cambiar salario
    public void actualizar(Empleado empleadoActualizado){

        List<Empleado> empleados = listar();

        for(int i = 0; i < empleados.size(); i++){

            if(empleados.get(i).getCedula() == empleadoActualizado.getCedula()){
                empleados.set(i, empleadoActualizado);
                guardarTodos(empleados);
                return;
            }
        }
        throw new RuntimeException("Empleado con CC " + empleadoActualizado.getCedula() + " no encontrado.");
    }

    //Elimina el empleado con la cédula dada
    public void eliminar(int cedula){

        List<Empleado> empleados = listar();
        boolean removido = empleados.removeIf(e -> e.getCedula() == cedula);

        if(!removido){
            throw new RuntimeException("Empleado con CC " + cedula + " no encontrado.");
        }

        guardarTodos(empleados);
    }


    //Búsquedas

    //Busca un empleado por cédula. Retorna null si no existe
    public Empleado buscarPorCedula(int cedula){

        return listar().stream()
                .filter(e -> e.getCedula() == cedula)
                .findFirst()
                .orElse(null);
    }

    //Retorna todos los empleados de una sucursal específica
    public List<Empleado> buscarPorSucursal(String nombreSucursal){

        List<Empleado> resultado = new ArrayList<>();

        for(Empleado e : listar()){

            if(nombreSucursal.equalsIgnoreCase(e.getSucursalId())){
                resultado.add(e);
            }
        }
        return resultado;
    }


    //Exportaciones (Servicio 4)

    //Exporta las evaluaciones de un empleado como JSON
    //Nombre del archivo: evaluacion_CC.json
    public void exportarEvaluacion(Empleado empleado){

        String nombreArchivo = "evaluacion_" + empleado.getCedula() + ".json";

        try(Writer writer = new FileWriter(nombreArchivo)){
            gson.toJson(empleado.getEvaluaciones(), writer);

        }catch(IOException e){

            throw new RuntimeException("Error al exportar evaluación: " + e.getMessage(), e);
        }
    }


    //Configuración Gson

    private Gson construirGson(){

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, ctx) -> LocalDate.parse(json.getAsString()))
                .create();
    }
}
