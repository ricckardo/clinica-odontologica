package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Turno;
import dh.backend.clinicamvc.service.ITurnoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    /*Inyeccion de dependencias*/
    private ITurnoServices turnoService;

    /*Inyeccion de dependencias a la clase Services*/

    public TurnoController(ITurnoServices turnoService) {
        this.turnoService = turnoService;
    }


    @PostMapping
    public ResponseEntity <Turno> registrarTurno(@RequestBody Turno turno){
        Turno turnoADevolver = turnoService.registrarTurno(turno);

        if(turnoADevolver == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoADevolver);
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodosLosTurnoss());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
        Turno turno = turnoService.buscarTurnoPorId(id);
        if(turno != null){
            return ResponseEntity.ok(turno);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("{\"messages\":  \"Turno actualizado\"}");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id){
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"messages\":  \"Turno eliminado\"}");
    }
}
