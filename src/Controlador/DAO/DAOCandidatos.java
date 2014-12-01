package Controlador.DAO;

import DAO.DAOBD;
import Modelo.Candidato;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCandidatos extends DAOBD<Candidato> {

    @Override
    public String obtenerCondicionElemento(Candidato elemento) {
        int idCandidato = elemento.getId();
        String condicion = "candidato_id = " + idCandidato;
        try {
            this.closeConnection(this.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(DAOCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return condicion;
    }

    @Override
    public Object obtenerElementoDeTabla(ResultSet resultado) {
        try {
            return new Candidato(resultado.getInt("candidato_id"),
                    resultado.getString("nombre"), resultado.getInt("num_votos"));
        } catch (SQLException ex) {
            System.out.println("ERROR EN LA LEÍDA DE LA BD.");
            ex.printStackTrace();
        }
        try {
            this.closeConnection(this.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(DAOCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateElement(Candidato elemento, String condicion) throws SQLException {
        this.establishConnection();
        Statement sentencia = this.getConnection().createStatement();
        int actualizaCandidato = sentencia.
                executeUpdate("UPDATE " + (elemento.getClass().getSimpleName().toLowerCase()) + " SET "
                        + "`candidato_id` = '" + elemento.getId() + "'"
                        + ",`nombre` = '" + elemento.getNombre() + "'"
                        + ",`num_votos` = '" + elemento.getNumVotos() + "'"
                        + " WHERE " + condicion);
        this.closeConnection(this.getConnection());
        return (actualizaCandidato != 0);
    }


    @Override
    public Candidato findElement(String nombreTabla, String condicion) throws SQLException {
        this.establishConnection();
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + condicion;
        Statement sentenciaBuscaliente = this.getConnection().createStatement();
        ResultSet busquedaCliente = sentenciaBuscaliente.executeQuery(query);
        busquedaCliente.next();
        Candidato unCandidato = new Candidato(busquedaCliente.getInt("candidato_id"),
                busquedaCliente.getString("nombre"),
                busquedaCliente.getInt("num_votos"));
        this.closeConnection(this.getConnection());
        return unCandidato;
    }

}
