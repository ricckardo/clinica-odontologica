package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/*Inteccion de dependencias con Service*/
@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    /*Inyeccion de dependencias por constructor*/
    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente registrarPacientes(Paciente paciente){
        LOGGER.info("Registrando : " + paciente );
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientesPorId(Integer idPaciente){
        LOGGER.info("Busqueda de Paciente por id : " + pacienteRepository.findById(idPaciente).isPresent());
        return pacienteRepository.findById(idPaciente);
    }

    @Override
    public List<Paciente> buscarTodosLosPacientes(){
        LOGGER.info("Lista de Pacientes encontrados : " + pacienteRepository.findAll().size());
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        /* Aqui se pisa el objeto actual, por detras JPA lo pisa y lo actualiza completamente */
        LOGGER.info("Actualizando : " + paciente );
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        LOGGER.info("Borrando Paciente con id: " + id  );
        pacienteRepository.deleteById(id);
    }

    /**
     * Busqueda de metodos HQL
     */
    @Override
    public Paciente buscarPacienteporDNI(String dni) {
        LOGGER.info("Buscar Paciente por DNI: " + dni  );
        return pacienteRepository.findByDNI(dni);
    }

    @Override
    public List<Paciente> buscarPacienteDomicilioPorProvincia(String provincia) {
        LOGGER.info("Borrando Paciente por provincia: " + provincia  );
        pacienteRepository.findByProvincia(provincia);
        return null;
    }


}
