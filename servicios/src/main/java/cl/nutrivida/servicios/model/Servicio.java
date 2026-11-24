package cl.nutrivida.servicios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Column(nullable = false, length = 100)
    private String codigo;

    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotBlank(message = "La duración es obligatoria")
    @Column(nullable = false, length = 100)
    private String duracion;

    @NotBlank(message = "La modalidad es obligatoria")
    @Column(nullable = false, length = 100)
    private String modalidad;

    @NotBlank(message = "El profesional es obligatorio")
    @Column(nullable = false, length = 150)
    private String profesional;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Integer precio;

    @Column(length = 1000)
    private String descripcion;

    @Column(length = 500)
    private String imagen;
}
