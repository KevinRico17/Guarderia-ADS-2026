package co.edu.javeriana.guarderiaadswagugu.modelo.usuarios;

import java.io.Serializable;

public class Usuario implements Serializable {
    public enum Rol {
        ADMIN_SUCURSAL,
        ADMIN_LOCALIDAD,
        ESPECIALISTA,
        ACUDIENTE
    }

    private String username;
    private String password;
    private Rol rol;
    private String entidadId; // id de la sucursal, localidad o cedula según el rol

    public Usuario(String username, String password, Rol rol, String entidadId) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.entidadId = entidadId;
    }

    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean puedeInscribirNino() {
        return rol == Rol.ADMIN_SUCURSAL;
    }

    public boolean puedeGestionarDieta() {
        return rol == Rol.ESPECIALISTA || rol == Rol.ADMIN_SUCURSAL;
    }

    public boolean puedeVerConsolidado() {
        return rol == Rol.ADMIN_LOCALIDAD || rol == Rol.ADMIN_SUCURSAL;
    }

    public boolean puedeEvaluarEmpleados() {
        return rol == Rol.ADMIN_SUCURSAL;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getEntidadId() { return entidadId; }
    public void setEntidadId(String entidadId) { this.entidadId = entidadId; }
}
