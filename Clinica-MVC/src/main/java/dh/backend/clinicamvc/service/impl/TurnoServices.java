package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.dao.impl.OdontologoDaoH2;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.model.Turno;
import dh.backend.clinicamvc.service.ITurnoServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoServices implements ITurnoServices {

    private IDAO<Turno> turnoIDao;
    private IDAO<Paciente> pacienteIDao;
    private IDAO<Odontologo> odontologoIDAO;

    public TurnoServices(IDAO<Turno> turnoIDao, IDAO<Paciente> pacienteIDao, IDAO<Odontologo> odontologoIDAO) {
        this.turnoIDao = turnoIDao;
        this.pacienteIDao = pacienteIDao;
        this.odontologoIDAO = odontologoIDAO;
    }

    @Override
    public Turno registrarTurno(Turno turno) {
        Paciente p = pacienteIDao.buscarPorId(turno.getPaciente().getId());
        Odontologo o = odontologoIDAO.buscarPorId(turno.getOdontologo().getId());
        Turno turnoARegistrar = new Turno();
        Turno turnoADevolver = null;
        if(p != null && o != null){
            turnoARegistrar.setPaciente(p);
            turnoARegistrar.setOdontologo(o);
            turnoARegistrar.setFecha(turno.getFecha());
            turnoADevolver = turnoIDao.registrar(turnoARegistrar);
        }
        return turnoADevolver;
    }

    @Override
    public Turno buscarTurnoPorId(Integer idTurno) {
        return turnoIDao.buscarPorId(idTurno);
    }

    @Override
    public List<Turno> buscarTodosLosTurnoss() {
        return turnoIDao.buscarTodos();
    }

    @Override
    public void actualizarTurno(Turno turno) {
        Integer idTurno = turno.getId();
        Integer idPaciente = turno.getPaciente().getId();
        Integer idOdontologo = turno.getOdontologo().getId();

        Paciente p = pacienteIDao.buscarPorId(idPaciente);
        Odontologo o = odontologoIDAO.buscarPorId(idTurno);
        Turno turnoAModificar = new Turno();
        if(p != null & o != null){
            turnoAModificar.setId(turno.getId());
            turnoAModificar.setPaciente(p);
            turnoAModificar.setOdontologo(o);
            turnoAModificar.setFecha(turno.getFecha());
            turnoIDao.actualizar(turnoAModificar);
        }

    }

    @Override
    public void eliminarTurno(Integer id) {
        turnoIDao.eliminar(id);
    }
}
