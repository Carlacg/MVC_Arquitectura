package Vista;

import Modelo.Candidato;
import Modelo.Modelo;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;

public class Barras extends Vista {

    private ArrayList<Candidato> candidatos;
    private final GraficaDeBarras graficaBarras;

    public Barras(Modelo modelo, int idEvento) {
        super(modelo, idEvento);
        this.candidatos = new ArrayList();
        this.graficaBarras = GraficaDeBarras.getInstance("Votaciones");
    }

    @Override
    public void actualizar(Object datos) {
        super.actualizar(datos);
        this.candidatos = (ArrayList<Candidato>) datos;
        desplegar();
        activar();
    }

    @Override
    public void desplegar() {
    }

    @Override
    public void activar() {
        this.graficaBarras.setDataSet(this.candidatos);
        this.graficaBarras.setVisible(false);
        this.graficaBarras.pack();
        graficaBarras.setVisible(true);
    }

}
