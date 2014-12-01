package Controlador.DAO;

import DAO.DAOBD;
import Modelo.Usuarios;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUsuarios extends DAOBD<Usuarios> {

    @Override
    public String obtenerCondicionElemento(Usuarios elemento) {
        int idUsuario = elemento.getId();
        String condicion = "usuario_id = " + idUsuario;
        return condicion;
    }

    @Override
    public Usuarios obtenerElementoDeTabla(ResultSet resultado) {
        try {
            return new Usuarios(resultado.getInt("usuario_id"),
                    resultado.getString("nombre"), resultado.getString("password"),
                    resultado.getString("rol"));
        } catch (SQLException ex) {
            System.out.println("ERROR EN LA LEÍDA DE LA BD.");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateElement(Usuarios elemento, String condicion) throws SQLException {
        this.establishConnection();
        Statement sentencia = this.getConnection().createStatement();
        int actualizaUsuario = sentencia.
                executeUpdate("UPDATE " + (elemento.getClass().getSimpleName().toLowerCase()) + " SET "
                        + "`usuario_id` = '" + elemento.getId() + "'"
                        + ",`nombre` = '" + elemento.getNombre_Usuario() + "'"
                        + ",`password` = '" + elemento.getPassword() + "'"
                        + ",`rol` = '" + elemento.getRoles() + "'"
                        + " WHERE " + condicion);
        return (actualizaUsuario != 0);
    }

    @Override
    public Usuarios findElement(String nombreTabla, String condicion) throws SQLException {
        this.establishConnection();
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + condicion;
        Statement sentenciaBuscaliente = this.getConnection().createStatement();
        ResultSet busquedaCliente = sentenciaBuscaliente.executeQuery(query);
        busquedaCliente.next();
        Usuarios unUsuario = new Usuarios(busquedaCliente.getInt("usuario_id"),
                busquedaCliente.getString("nombre"),
                busquedaCliente.getString("password"),
                busquedaCliente.getString("rol"));
        return unUsuario;
    }

}
