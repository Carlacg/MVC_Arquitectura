package Controlador;

import Cache.DuplicatedObjectException;
import Cache.FileConfigurationException;
import Cache.StrangeObjectException;
import Modelo.AdminUsuarios;
import Modelo.Modelo;
import Modelo.Usuarios;

public class ControladorSesion extends Controlador {
    
    private final String nombreClase = this.getClass().getName();

    public ControladorSesion(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    public boolean logIn(Usuarios usuario) throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        return AdminUsuarios.getInstance().logIn(usuario);
    }

    public void logOut() throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        AdminUsuarios.getInstance().logOut();
    }

    public void agregarCuenta(Usuarios usuario) throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        AdminUsuarios.getInstance().registrarUsuario(usuario);
    }

}
