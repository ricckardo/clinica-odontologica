package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Domicilio;
import dh.backend.clinicamvc.model.Paciente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*Inyeccion de dependencias
* posteriormente sera Repositiry*/

@Component
public class PacienteDaoH2 implements IDAO<Paciente> {

    private static Logger LOGGER = LoggerFactory.getLogger(PacienteDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO PACIENTES VALUES (DEFAULT,?,?,?,?,?)";
    private static String SQL_SELECT_ID = "SELECT * FROM PACIENTES WHERE ID=?";
    private static String SQL_SELECT_ALL = "SELECT * FROM PACIENTES";

    private static  String SQL_UPDATE = "UPDATE PACIENTES SET NOMBRE=?,APELLIDO=?, DNI=?, FECHA_INGRESO=?,ID_DOMICILIO=? WHERE ID=?";
    private static  String SQL_DELETE = "DELETE PACIENTES WHERE ID=? ";
    @Override
    public Paciente registrar(Paciente paciente)  {
        Connection connection = null;
        Paciente pacienteRetornado= null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            Domicilio domicilioGuardado = domicilioDaoH2.registrar(paciente.getDomicilio());

            PreparedStatement pst = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,paciente.getNombre());
            pst.setString(2,paciente.getApellido());
            pst.setString(3,paciente.getDni());
            pst.setDate(4, Date.valueOf( paciente.getFechaIngreso() ));
            pst.setInt(5,domicilioGuardado.getId());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            while(rs.next()){
                Integer id = rs.getInt(1);
                pacienteRetornado = new Paciente(id, paciente.getNombre(),paciente.getApellido(),paciente.getDni(),paciente.getFechaIngreso(),domicilioGuardado);
            }
            LOGGER.info("Paciente a retornar : " + pacienteRetornado.toString());

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
        return pacienteRetornado;
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        Connection connection = null;
        Paciente pacienteRetornado= null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL_SELECT_ID);
            pst.setInt(1,id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Integer idDevuleto = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String dni = rs.getString(4);
                LocalDate fecha = rs.getDate(5).toLocalDate();
                Integer idDomicilio = rs.getInt(6);
                Domicilio domicilioEncontrado = domicilioDaoH2.buscarPorId(idDomicilio);
                pacienteRetornado = new Paciente(idDevuleto,nombre,apellido,dni,fecha,domicilioEncontrado);
            }
            LOGGER.info("Paciente encontrado : " + pacienteRetornado);


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

        return pacienteRetornado;
    }

    @Override
    public List<Paciente> buscarTodos() {
        List<Paciente> listaPacientes = new ArrayList<>();
        Connection connection = null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        try {
            connection = H2Connection.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_SELECT_ALL);

            while (rs.next()){

                Integer idDevuleto = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String dni = rs.getString(4);
                LocalDate fecha = rs.getDate(5).toLocalDate();
                Integer idDomicilio = rs.getInt(6);
                Domicilio domicilioEncontrado = domicilioDaoH2.buscarPorId(idDomicilio);
                Paciente paciente = new Paciente(idDevuleto,nombre,apellido,dni,fecha,domicilioEncontrado);
                listaPacientes.add(paciente);
                LOGGER.info("Paciente lista ["+ listaPacientes.size()+"] = " + paciente.toString());
            }

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
        return listaPacientes;
    }

    @Override
    public void actualizar(Paciente paciente) {
        Connection connection = null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            domicilioDaoH2.actualizar(paciente.getDomicilio());

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setString(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, paciente.getDomicilio().getId());
            preparedStatement.setInt(6, paciente.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("paciente actualizado ");

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
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("paciente eliminado ");

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


}
