package cl.nutrivida.contacto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.nutrivida.contacto.model.Contacto;
import cl.nutrivida.contacto.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
    name = "Contacto",
    description = "API REST para los mensajes de contacto de Clínica NutriVida"
)
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @Operation(summary = "Listar mensajes de contacto")
    @GetMapping
    public List<Contacto> listarMensajes() {
        return contactoService.listarMensajes();
    }

    @Operation(summary = "Buscar mensaje por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> buscarPorId(
            @PathVariable Long id) {

        return contactoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar mensajes por correo")
    @GetMapping("/correo/{correo}")
    public List<Contacto> buscarPorCorreo(
            @PathVariable String correo) {

        return contactoService.buscarPorCorreo(correo);
    }

    @Operation(summary = "Crear mensaje de contacto")
    @PostMapping
    public ResponseEntity<Contacto> crearMensaje(
            @Valid @RequestBody Contacto contacto) {

        Contacto nuevo =
                contactoService.crearMensaje(contacto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @Operation(summary = "Actualizar mensaje de contacto")
    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizarMensaje(
            @PathVariable Long id,
            @Valid @RequestBody Contacto contacto) {

        Contacto actualizado =
                contactoService.actualizarMensaje(id, contacto);

        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar mensaje de contacto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(
            @PathVariable Long id) {

        contactoService.eliminarMensaje(id);

        return ResponseEntity.noContent().build();
    }
}
