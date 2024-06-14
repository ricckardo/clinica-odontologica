package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo o) throws BadRequestException;
    Optional<Odontologo> buscarOdontologoPorId(Integer id);
    List<Odontologo> buscarTodosLosOdontologos();
    void actualizarOdontologo(Odontologo odontolo);
    void eliminarOdontologo(Integer id) throws ResourcesNotFoundException;

    //Metodos con HQL - Hibernate - JPA
    List<Odontologo> buscarPorApellido (String apellido);
    List<Odontologo> bucarPorNombre (String nombre);
}
