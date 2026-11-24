package cl.nutrivida.contacto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.nutrivida.contacto.model.Contacto;
import cl.nutrivida.contacto.repository.ContactoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public List<Contacto> listarMensajes() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public List<Contacto> buscarPorCorreo(String correo) {
        return contactoRepository.findByCorreo(correo);
    }

    public Contacto crearMensaje(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public Contacto actualizarMensaje(Long id, Contacto datos) {

        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mensaje de contacto no encontrado"));

        contacto.setNombre_completo(datos.getNombre_completo());
        contacto.setCorreo(datos.getCorreo());
        contacto.setMensaje(datos.getMensaje());

        return contactoRepository.save(contacto);
    }

    public void eliminarMensaje(Long id) {

        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException(
                    "Mensaje de contacto no encontrado"
            );
        }

        contactoRepository.deleteById(id);
    }
}
