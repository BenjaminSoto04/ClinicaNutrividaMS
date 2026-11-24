package cl.nutrivida.usuarios.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.nutrivida.usuarios.model.Usuarios;
import cl.nutrivida.usuarios.repository.UsuariosRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuariosRepository usuariosRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuariosRepository usuariosRepository
    ) {
        this.jwtService = jwtService;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization =
                request.getHeader("Authorization");

        if (
            authorization == null ||
            !authorization.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authorization.substring(7);

        if (!jwtService.tokenValido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String correo =
                jwtService.obtenerCorreo(token);

        if (
            correo != null &&
            SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null
        ) {

            Usuarios usuario =
                    usuariosRepository
                            .findByCorreo(correo)
                            .orElse(null);

            if (
                usuario != null &&
                Boolean.TRUE.equals(usuario.getActivo())
            ) {

                SimpleGrantedAuthority autoridad =
                        new SimpleGrantedAuthority(
                                "ROLE_" +
                                usuario.getRol().toUpperCase()
                        );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario.getCorreo(),
                                null,
                                List.of(autoridad)
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
