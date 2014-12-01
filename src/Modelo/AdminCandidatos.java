package Modelo;

import Cache.*;
import Controlador.DAO.DAOCandidatos;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminCandidatos extends Modelo {
    
    private static AdminCandidatos instancia;
    private final DreamTeamCache cache;
    private int contador = 0;
    private final int primerCandidato = 1;
    private DAOCandidatos daoCandidato;
    
    private AdminCandidatos() {
        this.cache = DreamTeamCache.getInstance();
        this.daoCandidato = new DAOCandidatos();
        try {
            cache.configLoad();
            inicializarCandidatos();
            inicializarEventos();
        } catch (FileConfigurationException ex) {
            System.out.println("Error al iniciar la caché");
            ex.printStackTrace();
        }
    }
    
    public static AdminCandidatos getInstance() {
        if (instancia == null) {
            instancia = new AdminCandidatos();
        }
        return instancia;
    }
    
    private void inicializarCandidatos() {
        daoCandidato.getAllFromTable("candidato");
        for (Candidato candidato : daoCandidato.getAllFromTable("candidato")) {
            contador++;
            try {
                cache.put(candidato);
            } catch (DuplicatedObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            daoCandidato.addElement(candidatoNuevo);
            notificarObservadoresEvento(0);
        } catch (DuplicatedObjectException ex) {
            System.out.println("Error al agregar candidato a la cache");
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarVoto(int id) {
        try {
            Candidato candidato = (Candidato) cache.get(id);
            candidato.agregarVoto();
            cache.put(candidato);
            String condicion = daoCandidato.obtenerCondicionElemento(candidato);
            daoCandidato.updateElement(candidato, condicion);
            notificarObservadoresEvento(0);
        } catch (StrangeObjectException | DuplicatedObjectException | SQLException ex) {
            System.out.println("Error al agregar información al candidato");
            ex.printStackTrace();
        }
    }
    
    public void eliminarCandidatos(int id) {
        try {
            
            if (cache.get(id + 1) == null) {
                cache.delete(id);
                Candidato candidatoAEliminar = (Candidato) daoCandidato.
                        findElement("candidato", "candidato_id = " + id);
                daoCandidato.deleteElement(candidatoAEliminar);
            } else {
                Candidato candTemp = (Candidato) cache.get(id);
                cache.delete(id);
                Candidato candRemp = (Candidato) cache.get(contador);
                String condicion = daoCandidato.obtenerCondicionElemento(candTemp);
                candRemp.setId(id);
                cache.put(candRemp);
                cache.delete(contador);
                daoCandidato.updateElement(candRemp, condicion);
                
                candRemp.setId(contador);
                daoCandidato.deleteElement(candRemp);
            }
            contador--;
            notificarObservadoresEvento(0);
        } catch (StrangeObjectException | DuplicatedObjectException ex) {
            System.out.println("Error al eliminar candidato de la cache");
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Object getDatos() {
        try {
            setDatos(cache.getList(primerCandidato, contador));
        } catch (StrangeObjectException ex) {
            System.out.println("Error al obtener elementos de la cache");
            ex.printStackTrace();
        }
        return super.datos;
    }
}
