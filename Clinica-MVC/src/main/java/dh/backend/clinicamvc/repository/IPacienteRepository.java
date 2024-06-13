package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository <Paciente, Integer> {
    //Buscarf paciente por DNI
    @Query("SELECT p FROM Paciente p WHERE LOWER(t.dni) = LOWER(:dni)")
    Paciente findByDNI(@Param("dni") String dni);

    //BUscar oaciente por Domicilio Provincia
    @Query("SELECT p FROM Paciente p WHERE p.domicilio.provincia = :provincia ")
    List<Paciente> findByProvincia(@Param("provincia") String provincia);


}
