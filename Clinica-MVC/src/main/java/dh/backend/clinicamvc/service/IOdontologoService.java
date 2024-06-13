package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo o);
    Optional<Odontologo> buscarOdontologoPorId(Integer id);
    List<Odontologo> buscarTodosLosOdontologos();
    void actualizarOdontologo(Odontologo odontolo);
    void eliminarOdontologo(Integer id);

    //Metodos con HQL - Hibernate - JPA
    List<Odontologo> buscarPorApellido (String apellido);
    List<Odontologo> bucarPorNombre (String nombre);
}
