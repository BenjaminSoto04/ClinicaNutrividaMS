package cl.nutrivida.profesionales.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nutrivida.profesionales.model.Profesional;

public interface ProfesionalRepository 
        extends JpaRepository<Profesional, Long>{

    Optional<Profesional> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
