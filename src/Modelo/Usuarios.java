package Modelo;

import Cache.InterfazCache;
import static Modelo.AdminUsuarios.contador;
import Shiro.Rol;
import Shiro.Usuario;
import java.util.ArrayList;

public class Usuarios extends Usuario implements InterfazCache {

    private int id = 500;

    public Usuarios(int id, String Nombre_Usuario, String Password, ArrayList<Rol> roles) {
        super(Nombre_Usuario, Password, roles);
        this.id = id;
    }

    public Usuarios(String Nombre_Usuario, String Password, ArrayList<Rol> roles) {
        super(Nombre_Usuario, Password, roles);
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
        return id + super.getNombre_Usuario();
    }

    
}
