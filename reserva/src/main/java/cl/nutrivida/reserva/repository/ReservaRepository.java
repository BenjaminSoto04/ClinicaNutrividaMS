package cl.nutrivida.reserva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nutrivida.reserva.model.Reserva;

public interface ReservaRepository 
        extends JpaRepository<Reserva, Long>{

    List<Reserva> findByRut(String rut);

    List<Reserva> findByProfesionalId(Long profesionalId);

    boolean existsByProfesionalIdAndFechaAndHoraAndEstado(
            Long profesionalId,
            String fecha,
            String hora,
            String estado
    );
}
