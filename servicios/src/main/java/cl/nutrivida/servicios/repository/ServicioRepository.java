package cl.nutrivida.servicios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nutrivida.servicios.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long>{

    Optional<Servicio> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
