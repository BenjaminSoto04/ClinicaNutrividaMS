package cl.nutrivida.usuarios.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Column(name = "rol", nullable = false, length = 150)
    private String rol;

    @Column(name = "region", length = 150)
    private String region;

    @Column(name = "comuna", length = 150)
    private String comuna;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "profesional_id")
    private Long profesionalId;

    @Column(nullable = false)
    private Boolean activo = true;
}
