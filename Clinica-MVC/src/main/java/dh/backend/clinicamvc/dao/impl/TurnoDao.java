package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDAO;
import dh.backend.clinicamvc.model.Turno;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import dh.backend.clinicamvc.service.impl.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class TurnoDao implements IDAO<Turno> {

    private static Logger LOGGER = LoggerFactory.getLogger(TurnoDao.class);
    /*Inyecciond e dependencias con el constructor */

    private List<Turno> listaTurnos;
//     private PacienteService ps = new PacienteService( new PacienteDaoH2());
//     private OdontologoService os = new OdontologoService( new OdontologoDaoH2());



    public TurnoDao(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    @Override
    public Turno registrar(Turno turno) {
        Integer id = this.listaTurnos.size()+1;
        turno.setId(id);
//        Integer idPaciente = turno.getPaciente().getId();
//        Integer idOdontologo = turno.getOdontologo().getId();
//        LOGGER.info("Id de Paciente " + idPaciente);
//        turno.setPaciente( ps.buscarPacientesPorId(idPaciente));
//        turno.setOdontologo( os.buscarOdontologoPorId( idOdontologo) );
        listaTurnos.add(turno);
        LOGGER.info("Turno creado" + turno);
        return turno;
    }

    @Override
    public Turno buscarPorId(Integer id) {
        for(Turno t : listaTurnos){
            if(t.getId().equals((id))){
                LOGGER.info("turno encontrado: " + t);
                return t;
            }
        }
        LOGGER.info("turno no encontrado: " );
        return null;
    }

    @Override
    public List<Turno> buscarTodos() {
        return this.listaTurnos;
    }

    @Override
    public void actualizar(Turno turno) {
        /*Solo actualziar la fecha, ya que es turno en memoria y este cuenta con solo un dato que actualziar */
//        Integer idPaciente = turno.getPaciente().getId();
//        Integer idOdontologo = turno.getOdontologo().getId();
        for( Turno t : listaTurnos){
            if(t.getId().equals(turno.getId())){
//                LOGGER.info("Paciente : "+  ps.buscarPacientesPorId(idPaciente));
//                LOGGER.info("Turno : "+  os.buscarOdontologoPorId(idOdontologo) );
//                t.setPaciente(  ps.buscarPacientesPorId(idPaciente) );
//                t.setOdontologo( os.buscarOdontologoPorId(idOdontologo) );
                t.setFecha(turno.getFecha());
                LOGGER.info("turno actualizado " + t);
                break;
            }
        }
        LOGGER.info("turno no modificado " );

    }

    @Override
    public void eliminar(Integer id) {
        Turno turnoAEliminar = null;
        for(Turno t : listaTurnos){
            if(t.getId().equals((id))){
                turnoAEliminar = t;
                LOGGER.info("turno encontrado se procedera a eliminar: " + t);
                break;
            }
        }
        listaTurnos.remove(turnoAEliminar);
        LOGGER.info("turno eliminado: " );
    }
}
