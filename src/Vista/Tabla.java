package Vista;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Modelo.Candidato;
import Modelo.Modelo;
import java.util.ArrayList;


/**
 *
 * @author Carlos
 */
public class Tabla extends Vista {

    private VentanaTabla ventana;
    private ArrayList<Candidato> candidatos;

    public Tabla(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
        ventana = VentanaTabla.getInstance();
    }

    @Override
    public void actualizar(Object o) {
        super.actualizar(o);
        this.candidatos = (ArrayList<Candidato>) o;
        activar();
    }

    @Override
    public void desplegar() {

    }

    @Override
    public void activar() {
        ventana.llenaTabla(this.candidatos);
        ventana.setVisible(true);
    }
}
