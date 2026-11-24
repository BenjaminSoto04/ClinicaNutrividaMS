package cl.nutrivida.servicios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.nutrivida.servicios.model.Servicio;
import cl.nutrivida.servicios.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
    name = "Servicios",
    description = "API REST para la gestión de servicios de Clínica NutriVida"
)
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @Operation(summary = "Listar todos los servicios")
    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.listarServicios();
    }

    @Operation(summary = "Buscar un servicio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarPorId(
            @PathVariable Long id) {

        return servicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo servicio")
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(
            @Valid @RequestBody Servicio servicio) {

        Servicio nuevo =
                servicioService.crearServicio(servicio);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @Operation(summary = "Actualizar un servicio")
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(
            @PathVariable Long id,
            @Valid @RequestBody Servicio servicio) {

        Servicio actualizado =
                servicioService.actualizarServicio(id, servicio);

        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un servicio")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(
            @PathVariable Long id) {

        servicioService.eliminarServicio(id);

        return ResponseEntity.noContent().build();
    }
}
