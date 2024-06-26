package dh.backend.clinicamvc.dto.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacienteResponseDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;

}
