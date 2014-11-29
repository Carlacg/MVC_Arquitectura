package Vista;

import Modelo.AdminCandidatos;
import Modelo.Candidato;
import Modelo.Modelo;
import java.util.ArrayList;

public class Pastel extends Vista{
    private final GraficaDePastel graficaPastel;
    private ArrayList<Candidato> candidatos;
    
    public Pastel(Modelo modelo, int idEvento){
        super(modelo, idEvento);
        this.candidatos= AdminCandidatos.getInstance().obtenerLista();
        graficaPastel = GraficaDePastel.getInstance("Votaciones");
    }

    @Override
    public void actualizar(Object datos) {
        super.actualizar(datos);
        this.candidatos= (ArrayList<Candidato>) datos;
        activar();
    }

    @Override
    public void desplegar(){
    }


    @Override
    public void activar() {
        this.graficaPastel.setDataSet(this.candidatos);
        this.graficaPastel.setVisible(false);
        this.graficaPastel.pack();
        graficaPastel.setVisible(true);
    }

    
}
