package Modelo;

import Cache.*;
import Controlador.DAO.DAOCandidatos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jcs.access.exception.CacheException;

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
//        agregarCandidatos(1, "Romario");
//        agregarCandidatos(2, "David");
//        agregarCandidatos(3, "Kevin");
        
    }

    public final void inicializarEventos() {
        int numeroDeEventos = 3;
        for (int i = 0; i < numeroDeEventos; i++) {
            eventos.add(new Evento(i));
        }
    }

    public void agregarCandidatos(int id, String nombre) {
//        try {
//            contador++;
//            Candidato candidatoNuevo = new Candidato(id, nombre);
//            cache.put(candidatoNuevo);
//            daoCandidato.addElement(candidatoNuevo);
//            notificarObservadoresEvento(0);
//        } catch (DuplicatedObjectException ex) {
//
//        } catch (SQLException ex) {
//            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
//        }
        contador++;
        Candidato candidatoNuevo = new Candidato(id, nombre);
        try {
            daoCandidato.addElement(candidatoNuevo);
            notificarObservadoresEvento(0);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarVoto(int id) {
//        if (existenciaCandidato(id)) {
//            try {
//                Candidato candidato = (Candidato) cache.get(id);
//                candidato.agregarVoto();
//                cache.put(candidato);
//                String condicion = daoCandidato.obtenerCondicionElemento(candidato);
//                daoCandidato.updateElement(candidato, condicion);
//                notificarObservadoresEvento(0);
//            } catch (StrangeObjectException | DuplicatedObjectException | SQLException ex) {
//            }
//        } else {
//            try {
//                Candidato candidatoBD = daoCandidato.findElement("candidato", "candidato_id=" + id);
//                candidatoBD.agregarVoto();
//                String condicion2 = daoCandidato.obtenerCondicionElemento(candidatoBD);
//                daoCandidato.updateElement(candidatoBD, condicion2);
//                notificarObservadoresEvento(0);
//                cache.put(candidatoBD);
//            } catch (SQLException | DuplicatedObjectException ex) {
//                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        if (existenciaCandidato(id)) {
            try {
                String condicion = "candidato_id=" + id;
                Candidato candidato = (Candidato) cache.get(id);
                candidato.agregarVoto();
                daoCandidato.updateElement(candidato, condicion);
                System.out.println("Se votó por el candidato " + candidato.getNombre());
            } catch (SQLException | StrangeObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No se encontró al candidato en el caché");
            System.out.println("Lo buscaré en la BD");
            String condicion = "candidato_id=" + id;
            try {
                Candidato candidato = daoCandidato.findElement("candidato", condicion);
                cache.put(candidato);
                agregarVoto(candidato.getId());
            } catch (SQLException ex) {
                System.out.println("No existe el candidato");
            } catch (DuplicatedObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notificarObservadoresEvento(0);

    }

    public void eliminarCandidatos(int id) {
        try {
            Candidato candidato = daoCandidato.findElement("candidato", "candidato_id=" + id);
            daoCandidato.deleteElement(candidato);
            if (id != contador) {
                String condicion = "candidato_id=" + String.valueOf(contador);
                Candidato candidatoTemp = daoCandidato.findElement("candidato", condicion);
                candidatoTemp.setId(id);
                daoCandidato.updateElement(candidatoTemp, condicion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (existenciaCandidato(id)) {

            try {
                cache.delete(id);
                if (id != contador) {
                    Candidato candidatoTemp = (Candidato) cache.get(contador);
                    candidatoTemp.setId(id);
                    cache.put(candidatoTemp);
                    cache.delete(contador);
                }

            } catch (StrangeObjectException | DuplicatedObjectException ex) {
                Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        contador--;
        notificarObservadoresEvento(0);
    }

    private boolean existenciaCandidato(int id) {
        return cache.existenciaDeObjeto(id);
    }

    public void vaciarCache() {
        try {
            cache.clean();
        } catch (CacheException ex) {
            Logger.getLogger(AdminCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getDatos() {
        ArrayList<Candidato> candidatos = new ArrayList();
        for (Candidato candidato : daoCandidato.getAllFromTable("candidato")) {
            candidatos.add(candidato);
        }
        setDatos(candidatos);
        return super.datos;
    }
}
