package Modelo;

import Cache.*;
import java.util.ArrayList;

public class AdminCandidatos extends Modelo {

    private static AdminCandidatos instancia;
    public final ArrayList<Candidato> cands;
    private final DreamTeamCache cache;
//    private static final int CANDIDATOS_CACHE = 500;
    int contador = 0;

    private AdminCandidatos() throws FileConfigurationException, DuplicatedObjectException, StrangeObjectException {
        this.cache = DreamTeamCache.getInstance();
        cache.configLoad();
        inicializarCandidatos();
        this.cands = obtenerLista();
        super.datos = cands;
        inicializarEventos();

    }

    public static AdminCandidatos getInstance() throws FileConfigurationException, DuplicatedObjectException, StrangeObjectException {
        if (instancia == null) {
            instancia = new AdminCandidatos();
        }
        return instancia;
    }

    private void inicializarCandidatos() throws DuplicatedObjectException, StrangeObjectException {
        agregarCandidatos(1, "Romario");

        agregarCandidatos(2, "David");

        agregarCandidatos(3, "Victor");
    }

    public final void inicializarEventos() {
        int numeroDeEventos = 3;
        for (int i = 0; i < numeroDeEventos; i++) {
            eventos.add(new Evento(i));
        }
    }

    public void agregarCandidatos(int id, String nombre) throws DuplicatedObjectException, StrangeObjectException {
        contador++;
        Candidato candidatoNuevo = new Candidato(id, nombre);
        cache.put(candidatoNuevo);
        super.datos = obtenerLista();
        notificarObservadoresEvento(0);
    }

    public void agregarVoto(int id) throws DuplicatedObjectException, StrangeObjectException {
        Candidato candidato = (Candidato) cache.get(id);
        candidato.agregarVoto();
        cache.put(candidato);
        notificarObservadoresEvento(0);
    }

    public void eliminarCandidatos(int id) throws StrangeObjectException, DuplicatedObjectException {
        cache.delete(id);
        super.datos = obtenerLista();
        contador--;
        notificarObservadoresEvento(0);
    }

    public final ArrayList obtenerLista() throws StrangeObjectException, DuplicatedObjectException {
        boolean nulo = false;
        ArrayList elementos = new ArrayList<>();
        //Se recorre la cache para agregar los candidatos que tenga dentro:
        for (int i = 1; i <= contador; i++) {
            Object elemento = cache.get(i);
            if (elemento == null) {
                nulo = true;
            } else {
                if (nulo) {
                    Candidato temp = (Candidato) elemento;
                    temp.setId(i - 1);
                    cache.put(temp);
                    elementos.add((Object)temp);
                } else {
                    elementos.add(elemento);
                }
            }
        }
        return elementos;
    }

}
