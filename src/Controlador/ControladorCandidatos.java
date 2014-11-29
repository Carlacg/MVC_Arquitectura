package Controlador;

import Modelo.AdminCandidatos;
import Modelo.Modelo;

public class ControladorCandidatos extends Controlador {

    private String nombre = this.getClass().getName();

    public ControladorCandidatos(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    public void agregarCandidato(int id, String nombreCandidato){
        AdminCandidatos.getInstance().agregarCandidatos(id, nombreCandidato);
    }

    public void eliminarCandidato(int id){
        AdminCandidatos.getInstance().eliminarCandidatos(id);
    }

    @Override
    public void actualizar(Object o) {
    }

}
