package med.voll.api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.application.dto.medico.ActualizarMedicoDTO;
import med.voll.api.application.dto.medico.DatosMedicosRegistro;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosMedicosRegistro datosMedicosRegistro) {
        this.activo = true;
        this.nombre = datosMedicosRegistro.nombre();
        this.documento = datosMedicosRegistro.documento();
        this.especialidad = datosMedicosRegistro.especialidad();
        this.direccion = new Direccion(datosMedicosRegistro.direccion());
        this.email = datosMedicosRegistro.email();
        this.telefono = datosMedicosRegistro.telefono();
    }


    public void actualizarDatos(ActualizarMedicoDTO datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.documento() != null){
            this.documento= datos.documento();
        }
        if (datos.documento() != null){
            this.direccion= direccion.actualizarDatos(datos.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
