package cl.nutrivida.usuarios.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String nombre;
    private String correo;
    private String rut;
    private String password;
    private String region;
    private String comuna;
    private String direccion;
}
