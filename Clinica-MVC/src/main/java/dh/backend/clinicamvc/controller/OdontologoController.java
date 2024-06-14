package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourcesNotFoundException;
import dh.backend.clinicamvc.service.IOdontologoService;

import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Odontologo>  registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
         Odontologo OdontologoARetornar =  odontologoService.registrarOdontologo(odontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(OdontologoARetornar);
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
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(id);
        if(odontologo.isPresent()){
            return ResponseEntity.ok(odontologo.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    * http://localhost:8080/odontologos
    * */
    @PutMapping(produces = "application/json")
    public ResponseEntity<String>  actualizarOdontologo(@RequestBody Odontologo Odontologo){
        /* Validar si existe el Odontologo que mandan*/
        Optional<Odontologo> odontologoOptional = odontologoService.buscarOdontologoPorId(Odontologo.getId());

        if(odontologoOptional.isPresent()){
            odontologoService.actualizarOdontologo(Odontologo);
            return  ResponseEntity.ok("{\"messages\":  \"Odontologo actualizado\"}");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /*
     * http://localhost:8080/odontologos/3
     * */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String>  borrarOdontologo(@PathVariable Integer id) throws ResourcesNotFoundException {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("{\"messages\":  \"Odontologo eliminado\"}");

    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorApellido(@PathVariable String apellido){

        List<Odontologo> listaOdontologos = odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size() > 0){
            return ResponseEntity.ok(listaOdontologos);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public  ResponseEntity <List<Odontologo>> buscarOdontologoPorNOmbre(@PathVariable String nombre){
        List<Odontologo> listaOdontologos = odontologoService.bucarPorNombre(nombre);
        if(listaOdontologos.size() > 0){
            return ResponseEntity.ok(listaOdontologos);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
