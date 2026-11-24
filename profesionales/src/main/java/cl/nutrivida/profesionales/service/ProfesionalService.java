package cl.nutrivida.profesionales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.nutrivida.profesionales.model.Profesional;
import cl.nutrivida.profesionales.repository.ProfesionalRepository;

@Service
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;

    public ProfesionalService(
            ProfesionalRepository profesionalRepository) {

        this.profesionalRepository = profesionalRepository;
    }

    public List<Profesional> listarProfesionales() {
        return profesionalRepository.findAll();
    }

    public Optional<Profesional> buscarPorId(Long id) {
        return profesionalRepository.findById(id);
    }

    public Profesional crearProfesional(
            Profesional profesional) {

        if (profesionalRepository
                .existsByCorreo(profesional.getCorreo())) {

            throw new RuntimeException(
                    "Ya existe un profesional con ese correo"
            );
        }

        return profesionalRepository.save(profesional);
    }

    public Profesional actualizarProfesional(
            Long id,
            Profesional datos) {

        Profesional profesional =
                profesionalRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Profesional no encontrado"
                                )
                        );

        profesional.setNombre(datos.getNombre());
        profesional.setEspecialidad(datos.getEspecialidad());
        profesional.setCorreo(datos.getCorreo());
        profesional.setTelefono(datos.getTelefono());
        profesional.setDescripcion(datos.getDescripcion());
        profesional.setImagen(datos.getImagen());
        profesional.setActivo(datos.getActivo());

        return profesionalRepository.save(profesional);
    }

    public void eliminarProfesional(Long id) {

        if (!profesionalRepository.existsById(id)) {
            throw new RuntimeException(
                    "Profesional no encontrado"
            );
        }

        profesionalRepository.deleteById(id);
    }
}
