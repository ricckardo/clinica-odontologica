package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    /*
    * http://localhost:8080/odontologos
    * */
    @PostMapping
    public ResponseEntity<Odontologo>  registrarOdontologo(@RequestBody Odontologo Odontologo){
        Odontologo OdontologoARetornar = odontologoService.registrarOdontologo(Odontologo);
        if(OdontologoARetornar==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(OdontologoARetornar);
        }
    }

    /*
    * http://localhost:8080/odontologos
    * */
    @GetMapping
    public ResponseEntity<List<Odontologo>>  buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodosLosOdontologos());
    }

    /*
    * http://localhost:8080/odontologos/3
    * */
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id){
        Odontologo Odontologo = odontologoService.buscarOdontologoPorId(id);
        if(Odontologo != null){
            return ResponseEntity.ok(Odontologo);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    * http://localhost:8080/odontologos
    * */
    @PutMapping
    public ResponseEntity<String>  actualizarOdontologo(@RequestBody Odontologo Odontologo){
        odontologoService.actualizarOdontologo(Odontologo);
        return  ResponseEntity.ok("{\"messages\":  \"Odontologo actualizado\"}");
    }

    /*
     * http://localhost:8080/odontologos/3
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  borrarOdontologo(@PathVariable Integer id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"messages\":  \"Odontologo eliminado\"}");
    }
}
