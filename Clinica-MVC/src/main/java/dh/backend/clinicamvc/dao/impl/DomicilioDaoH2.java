package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Domicilio;

import dh.backend.clinicamvc.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DomicilioDaoH2 implements IDAO<Domicilio> {

    private static Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO DOMICILIOS VALUES (DEFAULT,?,?,?,?)";
    private static String SQL_SELECT_ID = "SELECT * FROM DOMICILIOS WHERE ID = ?";

    private static String SQL_SELECT_ALL = "SELECT * FROM DOMICILIOS";
    private static String SQL_UPDATE = "UPDATE DOMICILIOS SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID=?";
    private static String SQL_DELETE = "DELETE FROM DOMICILIOS WHERE ID=?";

    @Override
    public Domicilio registrar(Domicilio domicilio) {
        Connection connection = null;
        Domicilio domicilioARetornar= null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,domicilio.getCalle());
            pst.setInt(2, domicilio.getNumero());
            pst.setString(3,domicilio.getLocalidad());
            pst.setString(4,domicilio.getProvincia());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            while(rs.next()){
                Integer id = rs.getInt(1);
                    domicilioARetornar = new Domicilio(id,domicilio.getCalle(),domicilio.getNumero(),domicilio.getLocalidad(),domicilio.getProvincia());
            }
            LOGGER.info("Paciente percistido: "+ domicilioARetornar.toString());
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){

            if(connection != null){

                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }

            }

            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return domicilioARetornar;
    }

    @Override
    public Domicilio buscarPorId(Integer id) {
        Connection connection = null;
        Domicilio domicilioEncontrado = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL_SELECT_ID);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                domicilioEncontrado = crearDomicilio(rs);
            }
            LOGGER.info("Domicilio encontrado : "+ domicilioEncontrado.toString());
            return domicilioEncontrado;
        } catch (Exception e){
        LOGGER.info(e.getMessage());
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }
        return null;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        Connection connection = null;
        List<Domicilio> listaDomicilio = new ArrayList<>();
        Domicilio domicilioRetornado = null ;
        try {
            connection = H2Connection.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_SELECT_ALL);

            while(rs.next()){
                domicilioRetornado = crearDomicilio(rs);
                listaDomicilio.add(domicilioRetornado);
                LOGGER.info("Odontologo AGREGADO : [" + listaDomicilio.size() + "] = " + domicilioRetornado.toString());
            }

        } catch (Exception e) {
            LOGGER.info("Ocurrio un error en la Busqueda de Domicilios SQL_SELECT_ALL "+e.getMessage());
            e.printStackTrace();

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("Ocurrio un erro al cerrar la conexion : " + e.getMessage());
                e.printStackTrace();
            }

        }
        return listaDomicilio;
    }

    @Override
    public void actualizar(Domicilio domicilio) {
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());
            preparedStatement.setInt(5, domicilio.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("Domicilio actualizado ");
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("Domicilio eliminado ");
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private Domicilio crearDomicilio(ResultSet rs) throws SQLException {

        Integer idEncontrado = rs.getInt(1);
        String calle = rs.getString(2);
        Integer numero = rs.getInt(3);
        String localidad = rs.getString(4);
        String provincia = rs.getString(5);
        Domicilio domicilioEncontrado = new Domicilio(idEncontrado,calle,numero,localidad,provincia);
        return domicilioEncontrado;
    }
}
