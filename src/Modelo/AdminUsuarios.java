package Modelo;

import Cache.*;
import Controlador.DAO.DAOUsuarios;
import Shiro.*;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUsuarios extends Modelo {

    private static AdminUsuarios instancia;
    private final ShiroApi shiro;
    private final DreamTeamCache cache;
    private int contador = 500;
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
//        inicializarUsuarios();
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

    private void iniciaID(){
        contador = 500;
        for (Usuario usuario : daoUsuarios.getAllFromTable("usuario")) {
            contador++;
        }
    }
    public void inicializarUsuarios() {
        for (Usuario usuario : daoUsuarios.getAllFromTable("usuario")) {
            contador++;
            try {
                shiro.agregarCuenta(usuario.getNombre_Usuario(), usuario.getPassword(), usuario.getRoles());
                cache.put(usuario);
            } catch (DuplicatedObjectException ex) {
                Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        registrarUsuario("Carla", "123", "Admin");
//        registrarUsuario("Carlos", "12", "Votante");
//        registrarUsuario("Alex", "1", "Gestor");
    }

    private void inicializarRoles() {
        shiro.agregarRol("Admin", "*");
        shiro.agregarRol("Votante", "Votar");
        shiro.agregarRol("Gestor", "Gestionar candidatos");
    }

    public boolean logIn(String nombre, String clave) {
        Usuario usuario = null;
        try {
            usuario = daoUsuarios.findElement("usuario", "nombre= '" + nombre + "'");
        } catch (SQLException ex) {
            Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shiro.logIn(usuario.getNombre_Usuario(), clave);
    }

    private boolean existenciaUsuario(int id) {
        return cache.existenciaDeObjeto(id);
    }

    public void registrarUsuario(String nombre, String clave, String rol) {
        try {
            ++contador;
            clave = shiro.encriptar(clave);
            Usuario usuario = new Usuario(contador, nombre, clave, rol);
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
//        contador++;
//        String password = shiro.encriptar(clave);
//
//        Usuario nuevoUsuario = new Usuario(contador, usuario, password, "Votante");
//        try {
//            shiro.agregarCuenta(nuevoUsuario.getNombre_Usuario(), nuevoUsuario.getPassword(), nuevoUsuario.getRoles());
//            cache.put(nuevoUsuario);
//            daoUsuarios.addElement(nuevoUsuario);
//        } catch (DuplicatedObjectException ex) {
//            System.out.println("Error al agregar usuario a la cache");
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
//        }

        iniciaID();
        contador++;
        String password = shiro.encriptar(clave);
        Usuario usuarioNuevo = new Usuario(contador, usuario, password, "Votante");
        try {
            daoUsuarios.addElement(usuarioNuevo);
            cache.put(usuarioNuevo);
            shiro.agregarCuenta(usuarioNuevo.getNombre_Usuario(), usuarioNuevo.getPassword(),
                    usuarioNuevo.getRoles());
        } catch (SQLException | DuplicatedObjectException ex) {
            Logger.getLogger(AdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        notificarObservadoresEvento(0);
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
        ArrayList<Usuario> usuarios = new ArrayList();
        for (Usuario usuario : daoUsuarios.getAllFromTable("usuario")) {
            usuarios.add(usuario);
            shiro.agregarCuenta(usuario.getNombre_Usuario(), usuario.getPassword(), usuario.getRoles());
        }
        return super.datos;
    }
}
