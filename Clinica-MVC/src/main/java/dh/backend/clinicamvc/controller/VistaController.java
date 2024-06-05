package dh.backend.clinicamvc.controller;


import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller /*Funciona con la logica de thymeleaf */
@RequestMapping("/clinica/buscar/")
public class VistaController {

    private IPacienteService pacienteServices;
    private IOdontologoService odontologoServices;

    /*Constructor*/
    /*Inyecciond e dependencias*/



    public VistaController(IPacienteService pacienteServices, IOdontologoService odontologoServices) {
        this.pacienteServices = pacienteServices;
        this.odontologoServices = odontologoServices;
    }

    /*Recibir variables por parametros
     * JS = [Request param]
     * JAVA = [Request Param ]
     * /buscar?id=1
     * /bucar?param1=valor&param2=valor2
     * */

    /*
     * http://localhost:8080/clinica/buscar/paciente?id=1
     * */
    @GetMapping("/paciente")
    public String buscarPaciente(Model model, @RequestParam Integer id){
        Paciente p = pacienteServices.buscarPacientesPorId(id);
        model.addAttribute("especialidad", "Paciente");
        model.addAttribute("nombre",p.getNombre());
        model.addAttribute("apellido",p.getApellido());
        return "index";
    }

    /*
     * http://localhost:8080/clinica/buscar/odontologo?id=1
     * */
    @GetMapping("/odontologo")
    public String buscarOdocntologo(Model model, @RequestParam Integer id){
        Odontologo o = odontologoServices.buscarOdontologoPorId(id);
        model.addAttribute("especialidad", "Odontologo");
        model.addAttribute("nombre",o.getNombre());
        model.addAttribute("apellido",o.getApellido());
        return "index";
    }
}
