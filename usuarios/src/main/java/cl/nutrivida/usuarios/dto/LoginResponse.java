package cl.nutrivida.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long id;
    private String nombre;
    private String correo;
    private String rut;
    private String rol;
    private Long profesionalId;
}
