package cl.nutrivida.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.nutrivida.usuarios.model.Usuarios;
import cl.nutrivida.usuarios.repository.UsuariosRepository;

@Service 
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(
            UsuariosRepository usuariosRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuarios> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuarios> buscarPorId(Long id) {
        return usuariosRepository.findById(id);
    }

    public Usuarios guardarUsuario(Usuarios usuario) {

        if (
            usuario.getPassword() == null ||
            usuario.getPassword().trim().length() < 4
        ) {
            throw new RuntimeException(
                "La contraseña debe tener al menos 4 caracteres"
            );
        }

        if (usuariosRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException(
                "El correo ya se encuentra registrado"
            );
        }

        if (usuariosRepository.existsByRut(usuario.getRut())) {
            throw new RuntimeException(
                "El RUT ya se encuentra registrado"
            );
        }

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        usuario.setPassword(
            passwordEncoder.encode(usuario.getPassword())
        );

        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(Long id, Usuarios datos) {

        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());
        usuario.setRut(datos.getRut());
        if (
            datos.getPassword() != null &&
            !datos.getPassword().isBlank()
        ) {
            usuario.setPassword(
                passwordEncoder.encode(datos.getPassword())
            );
        }
        usuario.setRol(datos.getRol());
        usuario.setRegion(datos.getRegion());
        usuario.setComuna(datos.getComuna());
        usuario.setDireccion(datos.getDireccion());
        usuario.setProfesionalId(datos.getProfesionalId());
        usuario.setActivo(datos.getActivo());

        return usuariosRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuariosRepository.deleteById(id);
    }
}
