package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/* Clase pasamanos */
public interface ITurnoService {
    TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto);

    TurnoResponseDto buscarTurnoPorId(Integer idTurno);

    List<TurnoResponseDto> buscarTodosLosTurnos();

    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto);
    void eliminarTurno(Integer id);

    //Metodos con HQL - Hibernate - JPA
    List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate);
    List<TurnoResponseDto> buscarTurnoPosteriorFecha(LocalDate startDate);


}
