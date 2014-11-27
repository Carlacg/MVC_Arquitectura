package Controlador;

import Cache.DuplicatedObjectException;
import Cache.FileConfigurationException;
import Cache.StrangeObjectException;
import Modelo.AdminCandidatos;
import Modelo.Modelo;

public class ControladorCandidatos extends Controlador {

    private String nombre = this.getClass().getName();

    public ControladorCandidatos(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    public void agregarCandidato(int id, String nombreCandidato) throws DuplicatedObjectException, FileConfigurationException, StrangeObjectException {
        AdminCandidatos.getInstance().agregarCandidatos(id, nombreCandidato);
    }

    public void eliminarCandidato(int id) throws FileConfigurationException, DuplicatedObjectException, StrangeObjectException {
        AdminCandidatos.getInstance().eliminarCandidatos(id);
    }

}
