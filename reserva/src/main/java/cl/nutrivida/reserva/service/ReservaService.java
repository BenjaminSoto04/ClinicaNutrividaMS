package cl.nutrivida.reserva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.nutrivida.reserva.model.Reserva;
import cl.nutrivida.reserva.repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public List<Reserva> buscarPorRut(String rut) {
        return reservaRepository.findByRut(rut);
    }

    public List<Reserva> buscarPorProfesional(Long profesionalId) {
        return reservaRepository.findByProfesionalId(profesionalId);
    }

    public Reserva crearReserva(Reserva reserva) {

        boolean horarioOcupado =
                reservaRepository
                        .existsByProfesionalIdAndFechaAndHoraAndEstado(
                                reserva.getProfesionalId(),
                                reserva.getFecha(),
                                reserva.getHora(),
                                "Confirmada"
                        );

        if (horarioOcupado) {
            throw new RuntimeException(
                    "El profesional ya tiene una reserva en ese horario"
            );
        }

        reserva.setEstado("Confirmada");

        return reservaRepository.save(reserva);
    }

    public Reserva actualizarReserva(Long id, Reserva datos) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Reserva no encontrada"));

        reserva.setRut(datos.getRut());
        reserva.setServicioId(datos.getServicioId());
        reserva.setProfesionalId(datos.getProfesionalId());
        reserva.setFecha(datos.getFecha());
        reserva.setHora(datos.getHora());
        reserva.setEstado(datos.getEstado());

        return reservaRepository.save(reserva);
    }

    public Reserva cancelarReserva(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Reserva no encontrada"));

        reserva.setEstado("Cancelada");

        return reservaRepository.save(reserva);
    }

    public Reserva completarReserva(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Reserva no encontrada"));

        if (!"Confirmada".equals(reserva.getEstado())) {
            throw new RuntimeException(
                    "Solo se pueden completar reservas confirmadas"
            );
        }

        reserva.setEstado("Atendido");

        return reservaRepository.save(reserva);
    }

    public void eliminarReserva(Long id) {

        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada");
        }

        reservaRepository.deleteById(id);
    }
}
