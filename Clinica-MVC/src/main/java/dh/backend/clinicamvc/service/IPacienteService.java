package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente registrarPacientes(Paciente paciente);

    Optional<Paciente> buscarPacientesPorId(Integer idPaciente);

    List<Paciente> buscarTodosLosPacientes();

    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id);
}
