package cl.nutrivida.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nutrivida.usuarios.model.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

    Optional<Usuarios> findByCorreo(String correo);

    Optional<Usuarios> findByRut(String rut);

    boolean existsByCorreo(String correo);

    boolean existsByRut(String rut);
}
