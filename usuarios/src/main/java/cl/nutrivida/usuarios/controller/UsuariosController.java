package cl.nutrivida.usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.nutrivida.usuarios.model.Usuarios;
import cl.nutrivida.usuarios.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "Usuarios",
    description = "API REST para la gestión de usuarios de Clínica Nutrivida"
)
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene todos los usuarios registrados"
    )
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuariosService.listarUsuarios();
    }

    @Operation(
        summary = "Buscar usuario por ID",
        description = "Obtiene un usuario utilizando su identificador"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarUsuario(@PathVariable Long id) {

        return usuariosService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());   
    }

    @Operation(
        summary = "Crear usuario",
        description = "Registra un nuevo usuario en Clínica Nutravida"
    )
    @PostMapping
    public ResponseEntity<Usuarios> crearUsuario(
            @Valid @RequestBody Usuarios usuario) {
        
        Usuarios nuevoUsuario = usuariosService.guardarUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoUsuario);
    }

    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualziarUsuarios(
            @PathVariable Long id,
            @Valid @RequestBody Usuarios usuario) {
        
        Usuarios usuarioActualizado =
                usuariosService.actualizarUsuario(id, usuario);

        return ResponseEntity.ok(usuarioActualizado);
    }

    @Operation(
        summary = "Eliminar Usuario",
        description = "Elimina un usuario según su identificador"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Long id) {

        usuariosService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
