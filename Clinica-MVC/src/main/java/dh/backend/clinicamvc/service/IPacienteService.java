package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.model.Paciente;

import java.util.List;

public interface IPacienteService {

    Paciente registrarPacientes(Paciente paciente);

    Paciente buscarPacientesPorId(Integer idPaciente);

    List<Paciente> buscarTodosLosPacientes();

    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id);
}
