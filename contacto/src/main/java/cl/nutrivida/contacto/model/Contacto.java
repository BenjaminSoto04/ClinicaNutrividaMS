package cl.nutrivida.contacto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contacto")
public class Contacto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombre_completo;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
}
