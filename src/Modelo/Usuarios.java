package Modelo;

import Cache.InterfazCache;
import static Modelo.AdminUsuarios.contador;

public class Usuarios implements InterfazCache {

    private static final long serialVersionUID = -5372772868069600498L;

    private final String nombre_Usuario;
    private final String password;
    private final String roles;
    private int id = 499;

    public Usuarios(int id, String Nombre_Usuario, String Password, String roles) {
        this.nombre_Usuario = Nombre_Usuario;
        this.password = Password;
        this.roles = roles;
        this.id = id;
    }

    public Usuarios(String Nombre_Usuario, String Password, String roles) {
        this.nombre_Usuario = Nombre_Usuario;
        this.password = Password;
        this.roles = roles;
        this.id = id + contador;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + this.nombre_Usuario;
    }

}
