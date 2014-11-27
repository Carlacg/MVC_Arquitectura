package Controlador;

import Cache.*;
import Modelo.*;
import Vista.VentanaPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorVotos extends Controlador {

    private final String nombre = this.getClass().getName();
    ControladorSesion controldorSesion = new ControladorSesion((AdminCandidatos) super.getModelo(), 0);
    VentanaPrincipal ventanaPrincipal;

    public ControladorVotos(AdminCandidatos modelo, int idEvento) throws FileConfigurationException, StrangeObjectException, DuplicatedObjectException {
        super(modelo, idEvento);
        this.ventanaPrincipal = VentanaPrincipal.getInstance(this, controldorSesion);
    }

    public void realizarVotacion(int id) throws DuplicatedObjectException, StrangeObjectException, FileConfigurationException {
        AdminCandidatos.getInstance().agregarVoto(id);
    }

    public void desplegarVentana() throws FileConfigurationException, DuplicatedObjectException, StrangeObjectException {
        ventanaPrincipal.inicializaVentana((ArrayList<Candidato>) modelo.getDatos());
        ventanaPrincipal.setVisible(true);
    }

    @Override
    public void actualizar(Object o) {
        super.actualizar(o);
        try {
            desplegarVentana();
        } catch (FileConfigurationException | DuplicatedObjectException | StrangeObjectException ex) {
            Logger.getLogger(ControladorVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
