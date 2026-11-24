package cl.nutrivida.profesionales.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.nutrivida.profesionales.model.Profesional;
import cl.nutrivida.profesionales.service.ProfesionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profesionales")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
    name = "Profesionales",
    description = "API REST para la gestión de profesionales de Clínica NutriVida"
)
public class ProfesionalController {

    private final ProfesionalService profesionalService;

    public ProfesionalController(
            ProfesionalService profesionalService) {

        this.profesionalService = profesionalService;
    }

    @Operation(summary = "Listar profesionales")
    @GetMapping
    public List<Profesional> listarProfesionales() {
        return profesionalService.listarProfesionales();
    }

    @Operation(summary = "Buscar profesional por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Profesional> buscarPorId(
            @PathVariable Long id) {

        return profesionalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear profesional")
    @PostMapping
    public ResponseEntity<Profesional> crearProfesional(
            @Valid @RequestBody Profesional profesional) {

        Profesional nuevo =
                profesionalService.crearProfesional(profesional);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @Operation(summary = "Actualizar profesional")
    @PutMapping("/{id}")
    public ResponseEntity<Profesional> actualizarProfesional(
            @PathVariable Long id,
            @Valid @RequestBody Profesional profesional) {

        Profesional actualizado =
                profesionalService
                        .actualizarProfesional(id, profesional);

        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar profesional")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesional(
            @PathVariable Long id) {

        profesionalService.eliminarProfesional(id);

        return ResponseEntity.noContent().build();
    }
}
