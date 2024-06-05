package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.model.Turno;

import java.util.List;

public interface ITurnoServices {
    Turno registrarTurno(Turno turno);

    Turno buscarTurnoPorId(Integer idTurno);

    List<Turno> buscarTodosLosTurnoss();

    void actualizarTurno(Turno turno);
    void eliminarTurno(Integer id);
}
