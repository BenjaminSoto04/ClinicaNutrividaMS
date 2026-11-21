package cl.nutrivida.profesionales.model;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity
@Table(name = "profesionales")
public class Profesionales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 150)
    private String apellido;

    @Column(name = "especialidad", nullable = false, length = 150)
    private String especialidad;

    @Column(name = "perfil")
    private String perfil;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "horario")
    private String horario;
}
