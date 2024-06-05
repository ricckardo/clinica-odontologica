package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Domicilio;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdontologoDaoH2 implements IDAO<Odontologo> {

    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";

    private static String SQL_SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static String SQL_UPDATE = "UPDATE ODONTOLOGOS SET NRO_MATRICULA=?, NOMBRE=?, APELLIDO=? WHERE ID=?";
    private static String SQL_DELETE = "DELETE FROM ODONTOLOGOS WHERE ID=?";


    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRetornado = null;
//        OdontologoDaoH2 odontologoDaoH2 = new OdontologoDaoH2();

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            //Odontologo odontologoAGuardar = odontologoDaoH2.registrar(odontologo);
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,odontologo.getNumeroMatricula());
            pst.setString(2,odontologo.getNombre());
            pst.setString(3,odontologo.getApellido());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            while(rs.next()){
                Integer idEncontrado =  rs.getInt(1);
                odontologoRetornado = new Odontologo(idEncontrado,odontologo.getNumeroMatricula(),odontologo.getNombre(),odontologo.getApellido());
            }
            LOGGER.info("Odontologo a retornar :" + odontologoRetornado.toString() );
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {

            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Ocurrio Rollback" + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    LOGGER.info("SQL Exception : " + ex.getMessage());
                }
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("Ocurrio un erro al cerrar la conexion : " + e.getMessage());
                e.printStackTrace();
            }

        }

        return odontologoRetornado;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoRetornado= null;


        try {
            connection = H2Connection.getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL_SELECT_ID);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                odontologoRetornado = crearOdontologo(rs);
            }
            LOGGER.info("Paciente encontrado : " + odontologoRetornado.toString());


        }catch (Exception e){
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

        return odontologoRetornado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> listaOdontologos = new ArrayList<>();
        Connection connection = null;
        Odontologo odontologoRetornado = null ;
        try {
            connection = H2Connection.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_SELECT_ALL);

            while(rs.next()){
                odontologoRetornado = crearOdontologo(rs);
                listaOdontologos.add(odontologoRetornado);
                LOGGER.info("Odontologo creado : [" + listaOdontologos.size() + "] = " + odontologoRetornado.toString());
            }

        } catch (Exception e) {
            LOGGER.info("Ocurrio un error en la SQL_SELECT_ALL "+e.getMessage());
            e.printStackTrace();

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info("Ocurrio un erro al cerrar la conexion : " + e.getMessage());
                e.printStackTrace();
            }

        }
        return listaOdontologos;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setInt(1, odontologo.getNumeroMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.setInt(4, odontologo.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("Odontologo actualizado ");
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

            LOGGER.info("Odontologo eliminado ");
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



    private Odontologo crearOdontologo(ResultSet rs) throws SQLException {
        Integer idDevuleto = rs.getInt(1);
        Integer numeroMatricula = rs.getInt(2);
        String nombre = rs.getString(3);
        String apellido = rs.getString(4);
        Odontologo odontologoRetornado = new Odontologo(idDevuleto,numeroMatricula,nombre,apellido);
        return odontologoRetornado;
    }
}
