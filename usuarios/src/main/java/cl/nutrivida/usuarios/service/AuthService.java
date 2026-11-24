package cl.nutrivida.usuarios.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.nutrivida.usuarios.dto.LoginRequest;
import cl.nutrivida.usuarios.dto.LoginResponse;
import cl.nutrivida.usuarios.dto.RegisterRequest;
import cl.nutrivida.usuarios.model.Usuarios;
import cl.nutrivida.usuarios.repository.UsuariosRepository;
import cl.nutrivida.usuarios.security.JwtService;

@Service
public class AuthService {
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuariosRepository usuariosRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Usuarios usuario =
                usuariosRepository
                        .findByCorreo(request.getCorreo())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Correo o contraseña incorrectos"
                                )
                        );

        if (
            usuario.getActivo() != null &&
            !usuario.getActivo()
        ) {
            throw new RuntimeException(
                    "El usuario se encuentra desactivado"
            );
        }

        if (
            !passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
            )
        ) {
            throw new RuntimeException(
                    "Correo o contraseña incorrectos"
            );
        }

        String token =
                jwtService.generarToken(
                        usuario.getCorreo(),
                        usuario.getRol()
                );

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRut(),
                usuario.getRol(),
                usuario.getProfesionalId()
        );
    }

    public Usuarios registrar(RegisterRequest request) {

    if (
        request.getPassword() == null ||
        request.getPassword().trim().length() < 4
    ) {
        throw new RuntimeException(
            "La contraseña debe tener al menos 4 caracteres"
        );
    }

    if (
        usuariosRepository.existsByCorreo(
            request.getCorreo()
        )
    ) {
        throw new RuntimeException(
            "El correo ya se encuentra registrado"
        );
    }

    if (
        usuariosRepository.existsByRut(
            request.getRut()
        )
    ) {
        throw new RuntimeException(
            "El RUT ya se encuentra registrado"
        );
    }

    Usuarios usuario = new Usuarios();

    usuario.setNombre(request.getNombre());
    usuario.setCorreo(
        request.getCorreo().trim().toLowerCase()
    );
    usuario.setRut(request.getRut());

    usuario.setPassword(
        passwordEncoder.encode(
            request.getPassword()
        )
    );

    usuario.setRol("Cliente");
    usuario.setRegion(request.getRegion());
    usuario.setComuna(request.getComuna());
    usuario.setDireccion(request.getDireccion());
    usuario.setActivo(true);

    return usuariosRepository.save(usuario);
}
}
