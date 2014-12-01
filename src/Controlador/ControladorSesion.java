package Controlador;

import Modelo.AdminUsuarios;
import Modelo.Modelo;

public class ControladorSesion extends Controlador {

    private final String nombreClase = this.getClass().getName();

    public ControladorSesion(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    public boolean logIn(String nombre, String clave) {
        return AdminUsuarios.getInstance().logIn(nombre, clave);
    }

    public void logOut() {
        AdminUsuarios.getInstance().logOut();
    }

    public void agregarCuenta(String nombre, String clave) {
        AdminUsuarios.getInstance().registrarCuenta(nombre, clave);
    }

    @Override
    public void actualizar(Object o) {
    }

}
