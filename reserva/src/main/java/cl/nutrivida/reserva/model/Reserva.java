package cl.nutrivida.reserva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(nullable = false, length = 12)
    private String rut;

    @NotNull(message = "El servicio es obligatorio")
    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    @NotNull(message = "El profesional es obligatorio")
    @Column(name = "profesional_id", nullable = false)
    private Long profesionalId;

    @NotBlank(message = "La fecha es obligatoria")
    @Column(nullable = false, length = 10)
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    @Column(nullable = false, length = 5)
    private String hora;

    @Column(nullable = false, length = 20)
    private String estado;
}
