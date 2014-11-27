package Modelo;

import Cache.*;
import Shiro.*;
import java.awt.Window;
import java.util.ArrayList;

public class AdminUsuarios extends Modelo {

    private static AdminUsuarios instancia;
    private final ShiroApi shiro;
    private final DreamTeamCache cache;
    public final ArrayList<Usuarios> users;
    private static final int USUARIOS_CACHE = 1000;
    public static int contador = 1;

    private AdminUsuarios() throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        this.cache = DreamTeamCache.getInstance();
        cache.configLoad();
        this.shiro = new ShiroApi();
        inicializarUsuarios();
        inicializarEventos();
        this.users = obtenerLista();
        super.datos = users;
    }

    public static AdminUsuarios getInstance() throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        if (instancia == null) {
            instancia = new AdminUsuarios();
        }
        return instancia;
    }

    public final void inicializarEventos() {
        int numeroDeEventos = 3;
        for (int i = 0; i < numeroDeEventos; i++) {
            eventos.add(new Evento(i));
        }
    }

    private void inicializarUsuarios() throws DuplicatedObjectException {
        Usuarios administrador = new Usuarios(500, "Carla", "123", null);
        registrarUsuario(administrador);
        cache.put(administrador);
    }

    public boolean logIn(Usuarios usuario) {
        return shiro.logIn(usuario);
    }

    public void registrarUsuario(Usuarios usuario) throws DuplicatedObjectException {
        ++contador;
        shiro.agregarCuenta(usuario);
        cache.put(usuario);
    }

    public void logOut() {
        shiro.logOut();
        cerrarVentanas();
    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

    public final ArrayList obtenerLista() throws StrangeObjectException {
        ArrayList elementos = new ArrayList<>();
        //Se recorre la cache para agregar los usuarios que tenga dentro:
        for (int i = 500; i < USUARIOS_CACHE; i++) {
            Object elemento = cache.get(i);
            //si devuelve null, se deja de recorrer la cache
            if (elemento == null) {
                break;
            }
            elementos.add(elemento);
        }
        return elementos;
    }
}
