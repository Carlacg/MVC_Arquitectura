package Modelo;

import Cache.InterfazCache;

public class Usuario implements InterfazCache {

    private static final long serialVersionUID = -5372772868069600498L;

    private final String nombre_Usuario;
    private final String password;
    private final String roles;
    private int id;

    public Usuario(int id, String Nombre_Usuario, String Password, String roles) {
        this.nombre_Usuario = Nombre_Usuario;
        this.password = Password;
        this.roles = roles;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNombre_Usuario() {
        return nombre_Usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }


    @Override
    public String toString() {
        return getId() + ", " + "'" + getNombre_Usuario() + "'" + ", '" + getPassword() + "', '" + getRoles() + "'";
    }

}
