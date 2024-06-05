package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Paciente;

import java.util.List;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo o);

    Odontologo buscarOdontologoPorId(Integer id);

    List<Odontologo> buscarTodosLosOdontologos();
    void actualizarOdontologo(Odontologo odontolo);
    void eliminarOdontologo(Integer id);
}
