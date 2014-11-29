package Modelo;

import Cache.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminCandidatos extends Modelo {

    private static AdminCandidatos instancia;
    private final DreamTeamCache cache;
    int contador = 0;

    private AdminCandidatos() throws FileConfigurationException, DuplicatedObjectException, StrangeObjectException {
        this.cache = DreamTeamCache.getInstance();
        cache.configLoad();
        inicializarCandidatos();
        inicializarEventos();

    }

    public static AdminCandidatos getInstance() {
        if (instancia == null) {
            try {
                instancia = new AdminCandidatos();
            } catch (FileConfigurationException | DuplicatedObjectException | StrangeObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instancia;
    }

    private void inicializarCandidatos() {
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

    public void agregarCandidatos(int id, String nombre) {
        try {
            contador++;
            Candidato candidatoNuevo = new Candidato(id, nombre);
            cache.put(candidatoNuevo);
            notificarObservadoresEvento(0);
        } catch (DuplicatedObjectException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarVoto(int id) {
        try {
            Candidato candidato = (Candidato) cache.get(id);
            candidato.agregarVoto();
            cache.put(candidato);
            notificarObservadoresEvento(0);
        } catch (StrangeObjectException | DuplicatedObjectException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarCandidatos(int id) {
        try {
            if (cache.get(id + 1) == null) {
                cache.delete(id);
            } else {
                cache.delete(id);
                Candidato temp = (Candidato) cache.get(contador);
                temp.setId(id);
                cache.put(temp);
                cache.delete(contador);
            }
            contador--;
            notificarObservadoresEvento(0);
        } catch (StrangeObjectException | DuplicatedObjectException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final ArrayList obtenerLista() {
        ArrayList elementos = new ArrayList<>();
        //Se recorre la cache para agregar los candidatos que tenga dentro:
        for (int i = 1; i <= contador; i++) {
            try {
                Object elemento = cache.get(i);
                elementos.add(elemento);
            } catch (StrangeObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return elementos;
    }

    @Override
    public Object getDatos() {
        setDatos(obtenerLista());
        return super.datos;
    }
}
