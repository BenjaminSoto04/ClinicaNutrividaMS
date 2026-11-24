package cl.nutrivida.servicios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.nutrivida.servicios.model.Servicio;
import cl.nutrivida.servicios.repository.ServicioRepository;

@Service 
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio crearServicio(Servicio servicio) {

        if (servicioRepository.existsByCodigo(servicio.getCodigo())) {
            throw new RuntimeException(
                    "Ya existe un servicio con el código " + servicio.getCodigo()
            );
        }

        return servicioRepository.save(servicio);
    }

    public Servicio actualizarServicio(Long id, Servicio datos) {

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Servicio no encontrado"));

        servicio.setCodigo(datos.getCodigo());
        servicio.setTipo(datos.getTipo());
        servicio.setNombre(datos.getNombre());
        servicio.setDuracion(datos.getDuracion());
        servicio.setModalidad(datos.getModalidad());
        servicio.setProfesional(datos.getProfesional());
        servicio.setPrecio(datos.getPrecio());
        servicio.setDescripcion(datos.getDescripcion());
        servicio.setImagen(datos.getImagen());

        return servicioRepository.save(servicio);
    }

    public void eliminarServicio(Long id) {

        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado");
        }

        servicioRepository.deleteById(id);
    }
}
