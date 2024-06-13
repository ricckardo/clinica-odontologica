package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private IPacienteRepository pacienteRepository;
    private IOdontologoRepository odontologoRepository;
    private ITurnoRepository turnoRepository;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    /*Objeto de mapeo*/
    private ModelMapper modelMapper;

    public TurnoService(IPacienteRepository pacienteRepository, IOdontologoRepository odontologoRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> p = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> o = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if(p.isPresent() && o.isPresent()){
            turnoARegistrar.setPaciente(p.get());
            turnoARegistrar.setOdontologo(o.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            turnoADevolver = mapToResponseDTO(turnoGuardado);

            //Armar el turno a devolver
            // Conformado por PacienteResponseDto & OdontologoResponseDto

//            PacienteResponseDto pacienteResponseDTO= new PacienteResponseDto();
//            pacienteResponseDTO.setId(turnoGuardado.getPaciente().getId());
//            pacienteResponseDTO.setNombre(turnoGuardado.getPaciente().getNombre());
//            pacienteResponseDTO.setApellido(turnoGuardado.getPaciente().getApellido());
//            pacienteResponseDTO.setDni(turnoGuardado.getPaciente().getDni());
//
//            OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto();
//            odontologoResponseDto.setId(turnoGuardado.getOdontologo().getId());
//            odontologoResponseDto.setNumeroMatricula(turnoGuardado.getOdontologo().getNumeroMatricula());
//            odontologoResponseDto.setNombre(turnoGuardado.getOdontologo().getNombre());
//            odontologoResponseDto.setApellido(turnoGuardado.getOdontologo().getApellido());

            //Objeto creado
//            turnoADevolver = new TurnoResponseDto();
//            turnoADevolver.setId(turnoGuardado.getId());
//            turnoADevolver.setPaciente(pacienteResponseDTO);
//            turnoADevolver.setOdontologo(odontologoResponseDto);
//            turnoADevolver.setFecha(String.valueOf(turnoGuardado.getFecha()));
//
//            turnoADevolver = new TurnoResponseDto();
//            turnoADevolver.setId(turnoGuardado.getId());
//            turnoADevolver.setPaciente(pacienteResponseDTO);
//            turnoADevolver.setOdontologo(odontologoResponseDto);
//            turnoADevolver.setFecha(String.valueOf(turnoGuardado.getFecha()));

        }
        LOGGER.info("Turno registrado: " + turnoADevolver);
        return turnoADevolver;
    }

    @Override
    public TurnoResponseDto buscarTurnoPorId(Integer idTurno) {
        Optional<Turno> turnoEncontradoOptional = turnoRepository.findById(idTurno);
        /*Validar si existe el turno bajo la API de JPA*/
        if(turnoEncontradoOptional.isPresent()){
            TurnoResponseDto turnoADevolver = mapToResponseDTO(turnoEncontradoOptional.get());
            LOGGER.info("Turno encontrado por id: " + turnoADevolver);
            return turnoADevolver;
        }else{
            return null;
        }

    }

    @Override
    public List<TurnoResponseDto> buscarTodosLosTurnos() {
        List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
        List<Turno> listaTurnosADevolver = turnoRepository.findAll();
        TurnoResponseDto turnoADevolver = null;
        for (Turno t: listaTurnosADevolver) {
            turnoADevolver = mapToResponseDTO(t);
            listaTurnosResponseDto.add(turnoADevolver);
        }
        LOGGER.info("Lista de turnos generada: " + listaTurnosResponseDto);
        return listaTurnosResponseDto;
    }

    @Override
    public void actualizarTurno(Integer idTurno,TurnoRequestDto turnoRequestDto) {


        Integer idPaciente = turnoRequestDto.getPaciente_id();
        Integer idOdontologo = turnoRequestDto.getOdontologo_id();

        /*Buscando dependencias bajo el Framework de Hibernate - JPA*/
        Optional<Paciente> p = pacienteRepository.findById(idPaciente);
        Optional<Odontologo> o = odontologoRepository.findById(idOdontologo);

        /*Solo para saber si existe , si existe lo modifica si no no lo modifica*/
        Optional<Turno> turnoABuscarPorId = turnoRepository.findById(idTurno);

        LOGGER.info("Paciente encontrado :" +  (p.isPresent() ? true : false) );
        LOGGER.info("Odontologo encontrado :" +  (o.isPresent() ? true : false) );
        LOGGER.info("Turno encontrado :" +  (turnoABuscarPorId.isPresent() ? true : false) );


        Turno turnoAModificar = new Turno();

        if(p.isPresent() && o.isPresent() && turnoABuscarPorId.isPresent()){

            turnoAModificar.setId(idTurno);
            turnoAModificar.setPaciente(p.get());
            turnoAModificar.setOdontologo(o.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            LOGGER.info("Turno a modificar "+ turnoAModificar);
            turnoRepository.save(turnoAModificar);
        }

    }

    @Override
    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }


    /**
     * Busqueda de metodos HQL
     */

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listaTurnos = turnoRepository.buscarTurnoEntreFechas(startDate,endDate);
        List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno t: listaTurnos) {
            turnoAuxiliar = mapToResponseDTO(t);
            listaTurnosResponseDto.add(turnoAuxiliar);
        }
        LOGGER.info("Tamaño de la lista de turnos reponse dto´s "+ listaTurnosResponseDto.size());
        return listaTurnosResponseDto;
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoPosteriorFecha(LocalDate startDate) {
        List<Turno> listaTurnoPosteriorFecha = turnoRepository.findByStartDateTurnosAfter(startDate);
        List<TurnoResponseDto> listaTurnosResponseDto = new ArrayList<>();
        for (Turno t: listaTurnoPosteriorFecha) {
            listaTurnosResponseDto.add( mapToResponseDTO(t));
        }
        LOGGER.info("Tamaño de la lista de turnos posteriores a la fecha "+ startDate + " " + listaTurnosResponseDto.size());
        return listaTurnosResponseDto;
    }


    /*Metodo de mapeo*/
    private TurnoResponseDto mapToResponseDTO( Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        LOGGER.info("Mapeando tipos de datos: " + turnoResponseDto);
        return turnoResponseDto;
    }



}
