package Modelo;

import Cache.*;
import Controlador.DAO.DAOUsuarios;
import Shiro.*;
import java.awt.Window;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUsuarios extends Modelo {

    private static AdminUsuarios instancia;
    private final ShiroApi shiro;
    private final DreamTeamCache cache;
    private final int primerUsuario = 500;
    private int contador = 499;
    private DAOUsuarios daoUsuarios;

    private AdminUsuarios() {
        this.cache = DreamTeamCache.getInstance();
        this.daoUsuarios = new DAOUsuarios();
        try {
            cache.configLoad();
        } catch (FileConfigurationException ex) {
            System.out.println("Error al iniciar la caché");
            ex.printStackTrace();
        }
        this.shiro = new ShiroApi();
        inicializarUsuarios();
        inicializarEventos();
        inicializarRoles();
    }

    public static AdminUsuarios getInstance() {
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

    private void inicializarUsuarios() {
        for (Usuarios usuario : daoUsuarios.getAllFromTable("usuarios")) {
            contador++;
            try {
                shiro.agregarCuenta(usuario.getNombre_Usuario(), usuario.getPassword(), usuario.getRoles());
                cache.put(usuario);
            } catch (DuplicatedObjectException ex) {
                Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        registrarUsuario("Carla", "123", "Admin");
    }

    private void inicializarRoles() {
        shiro.agregarRol("Admin", "*");
        shiro.agregarRol("Votante", "Votar");
        shiro.agregarRol("Gestor", "Gestionar candidatos");
    }

    public boolean logIn(String nombre, String clave) {
        return shiro.logIn(nombre, clave);
    }

    public void registrarUsuario(String nombre, String clave, String rol) {
        try {
            ++contador;
            clave = shiro.encriptar(clave);
            Usuarios usuario = new Usuarios(contador, nombre, clave, rol);
            shiro.agregarCuenta(usuario.getNombre_Usuario(), usuario.getPassword(), usuario.getRoles());
            cache.put(usuario);
            daoUsuarios.addElement(usuario);

        } catch (DuplicatedObjectException ex) {
            System.out.println("Error al agregar usuario a la cache");
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Registra una nueva cuenta en el shiro y en la caché, con los datos
     * especificados, pero con el rol de VOTANTE sin opción a otro Rol.
     *
     * @param usuario
     * @param clave
     */
    public void registrarCuenta(String usuario, String clave) {
        contador++;
        String password = shiro.encriptar(clave);

        Usuarios nuevoUsuario = new Usuarios(contador, usuario, password, "Votante");
        try {
            shiro.agregarCuenta(nuevoUsuario.getNombre_Usuario(), nuevoUsuario.getPassword(), nuevoUsuario.getRoles());
            cache.put(nuevoUsuario);
            daoUsuarios.addElement(nuevoUsuario);
        } catch (DuplicatedObjectException ex) {
            System.out.println("Error al agregar usuario a la cache");
            ex.printStackTrace();
        } catch (SQLException ex) {
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
        return shiro.hasRol(rol);
    }

    public boolean getPermiso(String permiso) {
        return shiro.hasPermisos(permiso);
    }

    @Override
    public Object getDatos() {
        try {
            setDatos(cache.getList(primerUsuario, contador));
        } catch (StrangeObjectException ex) {
            System.out.println("Error al obtener elementos de la cache");
            ex.printStackTrace();
        }
        return super.datos;
    }
}
