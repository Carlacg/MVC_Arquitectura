package Controlador;

import Modelo.*;
import Vista.GestorCandidatos;
import Vista.VentanaPrincipal;
import java.util.ArrayList;

public class ControladorVotos extends Controlador {

    private final String nombre = this.getClass().getName();
    ControladorSesion controldorSesion = new ControladorSesion((AdminCandidatos) super.getModelo(), 0);
    VentanaPrincipal ventanaPrincipal;
    GestorCandidatos gestor;
    

    public ControladorVotos(AdminCandidatos modelo, int idEvento){
        super(modelo, idEvento);
        this.ventanaPrincipal = VentanaPrincipal.getInstance(this, controldorSesion);
        this.gestor = GestorCandidatos.getInstance(this, controldorSesion);
    }

    public void realizarVotacion(int id){
        AdminCandidatos.getInstance().agregarVoto(id);
    }

    public void desplegarVentana(){
        ventanaPrincipal.inicializaVentana((ArrayList<Candidato>) modelo.getDatos());
        ventanaPrincipal.setVisible(true);
        gestor.inicializaVentana((ArrayList<Candidato>) modelo.getDatos());
    }

    
    @Override
    public void actualizar(Object o) {
        desplegarVentana();
    }

}
