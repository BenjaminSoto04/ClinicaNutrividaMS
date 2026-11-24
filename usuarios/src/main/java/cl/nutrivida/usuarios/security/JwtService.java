package cl.nutrivida.usuarios.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =
            "nutrivida-clave-super-secreta-jwt-2026-segura";

    private static final long EXPIRACION =
            1000 * 60 * 60; // 1 hora

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generarToken(
            String correo,
            String rol
    ) {

        return Jwts.builder()
                .subject(correo)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + EXPIRACION
                        )
                )
                .signWith(obtenerClave())
                .compact();
    }

    public String obtenerCorreo(String token) {

        return obtenerClaims(token)
                .getSubject();
    }

    public String obtenerRol(String token) {

        return obtenerClaims(token)
                .get("rol", String.class);
    }

    public boolean tokenValido(String token) {

        try {

            Claims claims = obtenerClaims(token);

            return claims.getExpiration()
                    .after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    private Claims obtenerClaims(String token) {

        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
