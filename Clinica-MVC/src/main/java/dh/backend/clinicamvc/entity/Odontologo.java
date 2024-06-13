package dh.backend.clinicamvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
@ToString
@Entity
@Table (name ="odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer numeroMatricula;
    private String nombre;
    private String apellido;

    /**
     * En estricto Sentido
     * Un Odontologo puede tener muchos tuernos asignados <<set>>
     * Donde Turno es la Tabla Padre
     * Donde Paciente es la Tabla hija y hay un atributo de la clase Padre<set> se usa mappedBy
     *  @OneToMany(mappedBy) en Tabla padre existe un atributo del tipo de la tabla Hija
     * @JsonIgnore, EVITA  el ciclado de objetos JSON
     */
    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Turno> turnoSet = new HashSet<>();

}
