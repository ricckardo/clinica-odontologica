package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.stereotype.Service;


import java.util.List;

/*Inteccion de dependencias con Service*/
@Service
public class PacienteService implements IPacienteService {
    private IDAO<Paciente> pacienteIDao;

    public PacienteService(IDAO<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente registrarPacientes(Paciente paciente){
        return pacienteIDao.registrar(paciente);
    }

    public Paciente buscarPacientesPorId(Integer idPaciente){
        return pacienteIDao.buscarPorId(idPaciente);
    }

    public List<Paciente> buscarTodosLosPacientes(){
        return pacienteIDao.buscarTodos();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        pacienteIDao.actualizar(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        pacienteIDao.eliminar(id);
    }


}
