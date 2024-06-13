package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/*Inteccion de dependencias con Service*/
@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);

    /*Inyeccion de dependencias por constructor*/
    public OdontologoService(IOdontologoRepository odontologoRepository) {

        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo registrarOdontologo(Odontologo odontolo){
        LOGGER.info("Registrando : " + odontologoRepository.save(odontolo) );
        return odontologoRepository.save(odontolo);
    }

    @Override
    public Optional<Odontologo> buscarOdontologoPorId(Integer id){
        // Optional, tipo de dato que guarda un dato si se que lo encuentra y un null su no lo encuentra
        LOGGER.info("Odontologo encontraso : " + odontologoRepository.findById(id));
        return odontologoRepository.findById(id);
    }

    @Override
    public List<Odontologo> buscarTodosLosOdontologos(){
        LOGGER.info("Lista de Odontologos encontraso : " + odontologoRepository.findAll());
        return odontologoRepository.findAll();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        /* Aqui se pisa el objeto actual, por detras JPA lo pisa y lo actualiza completamente */
        LOGGER.info("Actualizando : " + odontologo );
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        LOGGER.info("Borrando Odontologo con id: " + id  );
        odontologoRepository.deleteById(id);
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        LOGGER.info("Buscnado Odontolo por apellido : " + apellido  );
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> bucarPorNombre(String nombre) {
        LOGGER.info("Buscnado Odontolo por nombre : " + nombre  );
        return odontologoRepository.findByNombreLike(nombre);
    }
}
