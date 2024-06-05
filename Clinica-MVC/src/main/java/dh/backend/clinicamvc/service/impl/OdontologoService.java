package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.stereotype.Service;


import java.util.List;

/*Inteccion de dependencias con Service*/
@Service
public class OdontologoService implements IOdontologoService {
    private IDAO<Odontologo> odontologoIDao;

    public OdontologoService(IDAO<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo registrarOdontologo(Odontologo o){
        return odontologoIDao.registrar(o);
    }

    public Odontologo buscarOdontologoPorId(Integer id){
        return odontologoIDao.buscarPorId(id);
    }

    public List<Odontologo> buscarTodosLosOdontologos(){
        return odontologoIDao.buscarTodos();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontolo) {
        odontologoIDao.actualizar(odontolo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
            odontologoIDao.eliminar(id);
    }
}
