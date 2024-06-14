package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.apache.coyote.BadRequestException;
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
    public Paciente registrarPacientes(Paciente paciente) throws BadRequestException {


        if (paciente.getNombre() != null && paciente.getApellido() != null){
            Paciente p = pacienteRepository.save(paciente);
            LOGGER.info("Registrando paciente : " + paciente );
            return p;
        }else{
            LOGGER.info("Paciente NO (Guardado) : " + paciente );
            throw new BadRequestException ("{\"messages\":  \"Pacienten no registrado\"}");
        }

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
    public void eliminarPaciente(Integer id) throws ResourcesNotFoundException {

        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if( optionalPaciente.isPresent()){
            LOGGER.info("Borrando Paciente con id: " + id  );
            pacienteRepository.deleteById(id);
        }else{
            LOGGER.info("Borrando Paciente no fue encotrado con id: " + id  );
            throw new ResourcesNotFoundException("{\"messages\":  \"Paciente no encontrado\"}");
        }

    }

    /**
     * Busqueda de metodos HQL
     */
    @Override
    public Paciente buscarPacienteporDNI(String dni) throws BadRequestException {
        LOGGER.info("Buscar Paciente por DNI: " + dni  );
        Paciente p = pacienteRepository.findByDNI(dni);
        if(p != null){
            return pacienteRepository.findByDNI(dni);
        }else{
            throw new BadRequestException ("{\"messages\":  \"DNI de paciente no encontrado\"}");

        }

    }

    @Override
    public List<Paciente> buscarPacienteDomicilioPorProvincia(String provincia) {
        LOGGER.info("Borrando Paciente por provincia: " + provincia  );
        return pacienteRepository.findByProvincia(provincia);
    }


}
