package dh.backend.clinicamvc.service.test;

import dh.backend.clinicamvc.dao.impl.PacienteDaoH2;
import dh.backend.clinicamvc.model.Domicilio;
import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.impl.PacienteService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PacienteServiceTest {

    public static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    PacienteService ps = new PacienteService( new PacienteDaoH2());
    /*Ejecutar esto antes que nada*/
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/Clinica-MVC;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado(){
        Paciente p = new Paciente("Raw","Alejandro","1234567",LocalDate.of(2024,4,12),new Domicilio("Calle False",465,"Sprinmgfild","Montana"));
        Paciente PacienteDesdeLaBD = ps.registrarPacientes(p);
        assertNotNull(PacienteDesdeLaBD);
    }

    @Test
    @DisplayName("Testear que un paciente por ID")
    void testPacientePorID(){
        Integer id = 1;
        Paciente pencontrado = ps.buscarPacientesPorId(id);
        assertEquals(id,pencontrado.getId());


    }

    @Test
    @DisplayName("Testear busqueda de todos los pacientes")
    void testTodosPacientes(){
        Integer id = 1;
        Paciente paciente = new Paciente("Lalotronics","Pedorro","1234567",LocalDate.of(2024,4,12),new Domicilio("Calle False",465,"Sprinmgfild","Montana"));
        ps.registrarPacientes(paciente);
        List<Paciente> listaPacientes = ps.buscarTodosLosPacientes();

        assertTrue(listaPacientes.size()!=0);
        



    }

}