package Controlador.DAO;

import DAO.DAOBD;
import Modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUsuarios extends DAOBD<Usuario> {

    @Override
    public String obtenerCondicionElemento(Usuario elemento) {
        int idUsuario = elemento.getId();
        String condicion = "usuario_id = " + idUsuario;

        return condicion;

    }

    @Override
    public Usuario obtenerElementoDeTabla(ResultSet resultado) {
        try {
            return new Usuario(resultado.getInt("usuario_id"),
                    resultado.getString("nombre"), resultado.getString("password"),
                    resultado.getString("rol"));
        } catch (SQLException ex) {
            System.out.println("ERROR EN LA LEÍDA DE LA BD.");
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean updateElement(Usuario elemento, String condicion) throws SQLException {
        this.establishConnection();
        Statement sentencia = this.getConnection().createStatement();
        int actualizaUsuario = sentencia.
                executeUpdate("UPDATE " + (elemento.getClass().getSimpleName().toLowerCase()) + " SET "
                        + "`usuario_id` = '" + elemento.getId() + "'"
                        + ",`nombre` = '" + elemento.getNombre_Usuario() + "'"
                        + ",`password` = '" + elemento.getPassword() + "'"
                        + ",`rol` = '" + elemento.getRoles() + "'"
                        + " WHERE " + condicion);
        sentencia.close();
        sentencia = null;
        this.closeConnection();

        return (actualizaUsuario != 0);
    }

    @Override
    public Usuario findElement(String nombreTabla, String condicion) throws SQLException {
        this.establishConnection();
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + condicion;
        Statement sentencia = this.getConnection().createStatement();
        ResultSet busquedaCliente = sentencia.executeQuery(query);
        busquedaCliente.next();
        Usuario unUsuario = new Usuario(busquedaCliente.getInt("usuario_id"),
                busquedaCliente.getString("nombre"),
                busquedaCliente.getString("password"),
                busquedaCliente.getString("rol"));
        sentencia.close();
        sentencia = null;
        this.closeConnection();

        return unUsuario;
    }

}
