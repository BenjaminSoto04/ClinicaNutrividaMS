package cl.nutrivida.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    
    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @Column(name = "rol", nullable = false, length = 150)
    private String rol;

    @Column(name = "region", length = 150)
    private String region;

    @Column(name = "comuna", length = 150)
    private String comuna;

    @Column(name = "direccion", length = 150)
    private String direccion;
}
