package Modelo;

import Cache.*;
import Shiro.*;
import java.awt.Window;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUsuarios extends Modelo {

    private static AdminUsuarios instancia;
    private final ShiroApi shiro;
    private final DreamTeamCache cache;
    public static int contador = 499;

    private AdminUsuarios() throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        this.cache = DreamTeamCache.getInstance();
        cache.configLoad();
        this.shiro = new ShiroApi();
        inicializarUsuarios();
        inicializarEventos();
    }

    public static AdminUsuarios getInstance() {
        if (instancia == null) {
            try {
                instancia = new AdminUsuarios();
            } catch (FileConfigurationException | StrangeObjectException | DuplicatedObjectException ex) {
                Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instancia;
    }

    public final void inicializarEventos() {
        int numeroDeEventos = 3;
        for (int i = 0; i < numeroDeEventos; i++) {
            eventos.add(new Evento(i));
        }
    }

    private void inicializarUsuarios() {
        registrarUsuario("Carla", "123", "admin");
    }

    public boolean logIn(String nombre, String clave) {
        return shiro.logIn(nombre, clave);
    }

    public void registrarUsuario(String nombre, String clave, String rol) {
        try {
            ++contador;
            shiro.agregarCuenta(nombre, clave, rol);
            Usuarios usuario = new Usuarios(contador, nombre, clave, rol);
            cache.put(usuario);

        } catch (DuplicatedObjectException ex) {
            Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public boolean getRol(String rol) {
        return shiro.hasPermission(rol);
    }

    public final ArrayList obtenerLista() {
        ArrayList elementos = new ArrayList<>();
        //Se recorre la cache para agregar los usuarios que tenga dentro:
        for (int i = 500; i <= contador; i++) {
            try {
                Object elemento = cache.get(i);

                elementos.add(elemento);
            } catch (StrangeObjectException ex) {
                Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return elementos;
    }

    @Override
    public Object getDatos() {
        setDatos(obtenerLista());
        return super.datos;
    }

}
