package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /*
    * http://localhost:8080/pacientes
    * */
    @PostMapping
    public ResponseEntity<Paciente>  registrarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteARetornar = pacienteService.registrarPacientes(paciente);
        if(pacienteARetornar==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);
        }
    }

    /*
    * http://localhost:8080/pacientes
    *
    * */
    @GetMapping
    public ResponseEntity<List<Paciente>>  buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    /*
    * http://localhost:8080/pacientes/1
    * */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        Paciente paciente = pacienteService.buscarPacientesPorId(id);
        if(paciente != null){
            return ResponseEntity.ok(paciente);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    * http://localhost:8080/pacientes
    * */
    @PutMapping
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente){
        pacienteService.actualizarPaciente(paciente);
        return  ResponseEntity.ok("{\"messages\":  \"Paciente actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  borrarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"messages\":  \"Paciente eliminado\"}");
    }
}
