package cl.nutrivida.reserva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.nutrivida.reserva.model.Reserva;
import cl.nutrivida.reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
    name = "Reservas",
    description = "API REST para la gestión de reservas de Clínica Nutrivida"
)
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Listar reservas")
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @Operation(summary = "Buscar reserva por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(
            @PathVariable Long id) {

        return reservaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar reservas por RUT")
    @GetMapping("/usuario/{rut}")
    public List<Reserva> buscarPorRut(
            @PathVariable String rut) {

        return reservaService.buscarPorRut(rut);
    }

    @Operation(summary = "Buscar reservas por profesional")
    @GetMapping("/profesional/{id}")
    public List<Reserva> buscarPorProfesional(
            @PathVariable Long id) {

        return reservaService.buscarPorProfesional(id);
    }

    @Operation(summary = "Crear reserva")
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(
            @Valid @RequestBody Reserva reserva) {

        Reserva nueva = reservaService.crearReserva(reserva);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nueva);
    }

    @Operation(summary = "Actualizar reserva")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody Reserva reserva) {

        Reserva actualizada =
                reservaService.actualizarReserva(id, reserva);

        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Cancelar reserva")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.cancelarReserva(id)
        );
    }

    @Operation(summary = "Marcar reserva como atendida")
    @PatchMapping("/{id}/atender")
    public ResponseEntity<Reserva> completarReserva(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.completarReserva(id)
        );
    }

    @Operation(summary = "Eliminar reserva")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(
            @PathVariable Long id) {

        reservaService.eliminarReserva(id);

        return ResponseEntity.noContent().build();
    }
}
